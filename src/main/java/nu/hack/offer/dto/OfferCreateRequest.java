package nu.hack.offer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferCreateRequest {

    @Schema(description = "User id. (For mobile)")
    private Integer userId;

    @Schema(description = "Bank Card id", example = "1")
    @NotNull
    private Integer bankCardId;

    @Schema(description = "Category id", example = "1")
    private Integer categoryId;

    @Schema(description = "percentage", example = "2.5")
    @NotNull
    private BigDecimal percentage;

    @Schema(description = "Conditions", example = "Только через QR")
    private String conditions;

    @Schema(description = "С какого времени началась акция", example = "2025-03-10")
    @NotNull
    private LocalDate dateFrom;

    @Schema(description = "Когда закончится акция", example = "2025-03-10")
    @NotNull
    private LocalDate dateTo;
}