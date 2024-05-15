package org.example.spring.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "add savings request")
public class AddSavingsRequest {
    @Schema(description = "Id user")
    private Long user_id;
    @Schema(description = "Id coin")
    private Long coin_id;
    @Schema(description = "Duration")
    private int duration;
    @Schema(description = "TempBalance")
    private BigDecimal tempBalance;


}
