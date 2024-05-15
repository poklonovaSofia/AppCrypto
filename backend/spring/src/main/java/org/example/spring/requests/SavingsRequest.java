package org.example.spring.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "get savings request")
public class SavingsRequest {
    @Schema(description = "Id user")
    private Long user_id;
    @Schema(description = "Id coin")
    private Long coin_id;
}
