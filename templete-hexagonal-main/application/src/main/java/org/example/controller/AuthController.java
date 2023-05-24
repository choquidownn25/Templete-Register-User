package org.example.controller;


import net.bytebuddy.utility.RandomString;
import org.example.security.jwt.JwtTokenUtil;
import org.example.security.jwt.JwtUtils;
import org.example.security.services.UserDetailsImpl;
import org.exemple.data.Mail;
import org.exemple.data.UserDTO;
import org.exemple.data.request.*;
import org.exemple.data.response.EmailDTOResponse;
import org.exemple.data.response.JwtResponse;
import org.exemple.data.response.PasswordResetTokenResponse;
import org.exemple.ports.api.UserServicePort;
import org.exemple.service.EmailService;
import org.exemple.service.EmailServiceImpl;
import org.exemple.utils.CustomerNotFoundException;
import org.exemple.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    @GetMapping("/emails")
    public void getEmails() throws MessagingException, IOException {
        //emailService.receiveEmailsOutlook();
        //emailService.receiveEmailsOutlook(); receiveEmails
        emailService.receiveEmails();
    }
    @GetMapping("/listEmails")
    public List<String> listEmails() throws MessagingException, IOException {
        //emailService.receiveEmailsOutlook();
        //emailService.receiveEmailsOutlook(); receiveEmails
        Stream<String> s = Stream.of(String.valueOf(emailService.listReceiveEmails().stream().collect(Collectors.toList())));
        //return emailService.listReceiveEmails().stream().collect(Collectors.toList());
        List<String> myList = s.collect(Collectors.toList());
        return myList;
    }
    @GetMapping("/listEmailsIMCP")
    public List<EmailDTOResponse> receiveEmailsIMCP() throws MessagingException, IOException {
        return emailService.receiveEmailsIMCP();
    }
}
