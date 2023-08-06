package org.example.controller;


import net.bytebuddy.utility.RandomString;
import org.example.security.jwt.JwtTokenUtil;
import org.example.security.jwt.JwtUtils;
import org.example.security.services.UserDetailsImpl;
import org.exemple.data.BancoOrigenDTO;
import org.exemple.data.CertificadoBeneficios;
import org.exemple.data.Mail;
import org.exemple.data.request.*;
import org.exemple.data.response.BancoOrigenDTOResponse;
import org.exemple.data.response.JwtResponse;
import org.exemple.data.response.Message;
import org.exemple.ports.api.UserServicePort;
import org.exemple.service.EmailServiceImpl;
import org.exemple.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserServicePort serverPort;

    @Autowired
    private  MessageSource messageSource;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailServiceImpl emailService;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(serverPort.registerUser(signUpRequest));
    }
    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model, @Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            serverPort.updateResetPasswordToken(token, passwordResetRequest.getEmail());
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;

            sendEmail(passwordResetRequest.getEmail(), resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (Exception e) {
            model.addAttribute("error", "Error while sending email : " + e.getMessage());
        }

        return "forgot_password_form" + model.asMap().values();
    }
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        Mail mail = new Mail();
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        String to = recipientEmail;
        String subject = "Here's the link to reset your password";

        mail.setMailTo(to);
        mail.setMailSubject(subject);
        mail.setMailContent(content);
        mail.setMailFrom("choquidownn2255@outlook.com");
        emailService.sendEmail(mail);

    }


    @GetMapping("/listEmailsIMCP/{nameBanco}")
    public BancoOrigenDTOResponse receiveEmailsIMCP(@PathVariable String nameBanco) throws MessagingException, IOException {
        //return emailService.receiveEmailsIMCP();
        //return emailService.receiveEmailsHTML();
        BancoOrigenDTOResponse bancoOrigenDTOResponse = new BancoOrigenDTOResponse();
        List<BancoOrigenDTO> bancoOrigenDTO = emailService.receiveEmailsHTMLBanco(nameBanco);
        if(bancoOrigenDTO.size()>0){

            Message message = new Message();
            message.setCode(200);
            message.setEcho("Success connection ");
            bancoOrigenDTOResponse.setListBancoOrigenDTOs(bancoOrigenDTO);
            bancoOrigenDTOResponse.setMessage(message);
            return bancoOrigenDTOResponse;
        }

        else{
            Message message = new Message();
            message.setCode(10);
            message.setEcho("Error Internet connection ");
            bancoOrigenDTOResponse.setMessage(message);
            return bancoOrigenDTOResponse;
        }

    }
    @GetMapping("/get_certificado_beneficios")
    public CertificadoBeneficios get_certificado_beneficios() throws Exception {
        return emailService.mainCertificadoBeneficios();
    }
}
