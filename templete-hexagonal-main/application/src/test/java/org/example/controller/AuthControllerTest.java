package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.security.jwt.JwtTokenUtil;
import org.example.security.jwt.JwtUtils;
import org.exemple.data.request.LoginRequest;
import org.exemple.data.request.SignupRequest;
import org.exemple.ports.api.UserServicePort;
import org.exemple.service.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes=AuthController.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    JwtUtils jwtUtils;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private UserServicePort serverPort;
    @MockBean
    private MessageSource messageSource;
    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    private EmailServiceImpl emailService;

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();
    private LoginRequest loginRequest;
    private SignupRequest signupRequest;
    private Set<String> role;
    @BeforeEach
    public void setup() {
        loginRequest = new LoginRequest("gloriamamahermosados", "12345678");
    }

    @Test
    void authenticateUser() throws Exception {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        String json = mapper.writeValueAsString(loginRequest);
        mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print());


    }

    @Test
    void registerUser() throws Exception {
        signupRequest = new SignupRequest( "prueba",
                "prueba@yahoo.com.mx",
                Collections.singleton("mod"),
                "12345678"
        );
        mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(String.valueOf(signupRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());

    }
}