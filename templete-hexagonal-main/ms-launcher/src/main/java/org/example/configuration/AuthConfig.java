package org.example.configuration;

import org.example.adapters.UserJpaAdapter;
import org.exemple.ports.api.UserServicePort;
import org.exemple.ports.spi.UserPersistencePort;
import org.exemple.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {
    @Bean
    public UserPersistencePort userPersistencePort(){ return new UserJpaAdapter();
    }
    @Bean
    public UserServicePort userServicePort(){return new UserServiceImpl(userPersistencePort());}
}
