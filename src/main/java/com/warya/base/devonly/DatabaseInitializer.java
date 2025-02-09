package com.warya.base.devonly;

import com.warya.base.security.dao.User;
import com.warya.base.security.dao.UserRepository;
import com.warya.base.security.enums.Role;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class DatabaseInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void run(String... args) {
        log.info("Initializing Database");

        if (userRepository.count() > 0) {
            log.info("Database already initialized");
            return;
        }

        log.info("Initializing Database with sample data");

        createUser("admin", "admin", "admin@base.com", Role.ROLE_ADMIN);


        log.info("Database initialization completed");
    }

    private void createUser(String username, String password, String email, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole(role);
        user.setActive(true);
        userRepository.save(user);
    }

    @Transactional
    public void cleanDatabase() {
        log.info("Cleaning database...");
        userRepository.deleteAll();
        log.info("Database cleaned successfully");
    }

    @PostConstruct
    public void logEncoderType() {
        log.info("Using password encoder: {}", passwordEncoder.getClass().getName());
    }
}