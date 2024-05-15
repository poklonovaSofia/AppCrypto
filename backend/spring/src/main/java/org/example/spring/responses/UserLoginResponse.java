package org.example.spring.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "User`s key")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    @Schema(description = "ID user")
    private Long id;

    @Schema(description = "Email user")
    private String email;

    @Schema(description = "JWT token access")
    private String accessToken;
}