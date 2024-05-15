package org.example.spring.models.auth;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.regex.Pattern;

@MappedSuperclass
@Data
public class BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Уникальность почты
    private String email;

    private String password;

    @Column(unique = true)
    private String username;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;



    // Дополнительная проверка на корректность формата email
    @PrePersist
    @PreUpdate
    private void validateEmail() {
        if (email != null && !email.matches("^[\\w-_.+]*[\\w-_.]@[\\w]+[.]([\\w]+[.])*[\\w]{2,}$")) {
            throw new IllegalArgumentException("Email");
        }
        validateUsername();
        hashPassword();
    }

    private void validateUsername() {
        if(!username.matches("^[a-zA-Z0-9]{5,}$"))
        {
            throw new IllegalArgumentException("Username");
        }
    }
    // Хеширование пароля

    private void hashPassword() {
        // Проверка на длину пароля и наличие символов
        if (password == null || password.length() < 6 ||
                !password.matches(".*[A-Z].*") || // Проверка наличия большой буквы
                !password.matches(".*[a-z].*") || // Проверка наличия маленькой буквы
                !password.matches(".*\\d.*")) {  // Проверка наличия цифры
            //throw new IllegalArgumentException("Пароль должен быть не менее 6 символов и содержать хотя бы одну большую букву, одну маленькую букву и одну цифру");
            throw new IllegalArgumentException("Password");

        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(this.password);
    }


}