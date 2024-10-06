package com.dirtmuncher.configuration;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;

@Configuration
@Data
public class AuthenticationConfiguration {

    @Bean
    public AuthenticationManager noOperationAuthenticationManager() {
        //authManager variable is redundant but I feel it makes it more readable
        AuthenticationManager authManager = authentication -> {
            throw new AuthenticationServiceException("No user authentication required currently");
        };

        return authManager;
    }
}
