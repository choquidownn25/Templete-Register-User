package org.example.adapters;

import org.example.entity.ERole;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.mappers.UserMapper;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.exemple.data.UserDTO;
import org.exemple.data.response.MessageResponse;
import org.exemple.ports.spi.UserPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

public class UserJpaAdapter implements UserPersistencePort {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalStateException ("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalStateException("Error: Email is already in use!");
        }
        // Create new user's account
        User user = new User(userDTO.getUsername(),
                userDTO.getEmail(),
                encoder.encode(userDTO.getPassword()));

        Set<String> strRoles = userDTO.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return  UserMapper.INSTANCE.userDTOToUser(user);
    }
}
