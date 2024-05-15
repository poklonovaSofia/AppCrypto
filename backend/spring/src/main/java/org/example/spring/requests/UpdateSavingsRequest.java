package org.example.spring.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Schema(description = "update savings request")
public class UpdateSavingsRequest {
    @Schema(description = "Id")
    private Long id;
    @Schema(description = "TempBalance")
    private BigDecimal tempBalance;
}
