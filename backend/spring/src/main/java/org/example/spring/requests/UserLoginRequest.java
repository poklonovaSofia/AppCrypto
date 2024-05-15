package org.example.spring.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "user login request")
public class UserLoginRequest {
    @Schema(description = "Email user", example = "user@user.com")
    @Size(min = 1, max = 100)
    private String email;

    @Schema(description = "Пароль администратора", example = "password")
    @Size(min = 8, max = 255)
    private String password;
}