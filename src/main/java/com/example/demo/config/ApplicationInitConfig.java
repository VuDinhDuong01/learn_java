package com.example.demo.config;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.User;
import com.example.demo.enums.Role;
import com.example.demo.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


// dùng sonarqube để check các tiềm  ẩn lỗi .

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    // @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    // khi bật app thì nếu k có thì sẽ tạo 1 user admin
    // có rồi thì thôi.
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                HashSet<String> rolesSet = new HashSet<>();
                rolesSet.add(Role.ADMIN.name());

                User user = (User) User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        // .roles(rolesSet)
                        .build();
                userRepository.save(user);
                log.warn("đã tạo user admin khi application run");
            }
        };
    }
}
