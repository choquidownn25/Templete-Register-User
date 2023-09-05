package org.example.adapters;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.ERole;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.exemple.data.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @Mock // Create a mock UserRepository
    UserRepository userRepository;
    @Mock // Create a mock UserRepository
    RoleRepository roleRepository;
    private static ObjectMapper mapper = new ObjectMapper();
    @BeforeEach
    void setUp() {

    }

    @Test
    void registerUser() throws JsonProcessingException {
        Set<Role> roles = new HashSet<>();
        User user = new User( "prueba",
                "prueba@yahoo.com.mx",
                "12345678"
        );
        Role userRole = new Role(ERole.ROLE_USER);
        userRole.setId(1);
        roles.add(userRole);
        Role modRole = new Role(ERole.ROLE_MODERATOR);
        modRole.setId(2);
        roles.add(modRole);
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        adminRole.setId(3);
        roles.add(adminRole);
        user.setRoles(roles);
        user.setResetPasswordToken("Esto es prueba");
        //user.setId(2L);
        when(userRepository.save(any(User.class))).thenReturn(user);
        // Act: Call the method you want to test
        // Assert: Check the result or verify interactions with the mock
        // Act: Call the method you want to test
        User result = userRepository.save(user);
        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void findUserByEmail() {
        Boolean existsByEmail = userRepository.existsByEmail("gloriamamahermosa@yahoo.com");
        assertFalse("Email test es : ", existsByEmail);
    }

    @Test
    void findUserByResetToken() {
    }

    @Test
    void updateResetPasswordToken() {
    }
}