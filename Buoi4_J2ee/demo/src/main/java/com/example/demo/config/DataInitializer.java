package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserService userService;

    public DataInitializer(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // Demo accounts (created only if not exists)
        if (!userRepository.existsByUsername("admin")) {
            userService.register("admin", "123456", Role.ADMIN);
        }
        if (!userRepository.existsByUsername("user")) {
            userService.register("user", "123456", Role.USER);
        }
    }
}
