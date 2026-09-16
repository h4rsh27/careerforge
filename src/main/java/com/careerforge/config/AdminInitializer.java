package com.careerforge.config;

import com.careerforge.entity.User;
import com.careerforge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Value("${admin.email:}")
    private String adminEmail;

    @Value("${admin.password:}")
    private String adminPassword;

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (adminEmail == null
                    || adminEmail.isBlank()
                    || adminPassword == null
                    || adminPassword.isBlank()) {

                return;
            }

            if (userRepository.existsByEmail(adminEmail)) {
                return;
            }

            User admin = new User();

            admin.setName("CareerForge Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(
                    passwordEncoder.encode(adminPassword)
            );
            admin.setRole("ADMIN");

            userRepository.save(admin);
        };
    }
}