package io.github.WesleyPatrick.stock_api.infra.seed;

import io.github.WesleyPatrick.stock_api.domain.model.User;
import io.github.WesleyPatrick.stock_api.domain.model.enums.Role;
import io.github.WesleyPatrick.stock_api.infra.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeed implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AdminSeed(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAdmin();
    }

    private void seedAdmin() {
        var email = "admin@email.com";

        if (userRepository.existsByEmail(email)) return;

        var user = new User();
        user.setName("ADMIN");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }
}
