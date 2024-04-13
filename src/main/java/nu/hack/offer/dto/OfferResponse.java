package nu.hack.offer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nu.hack.appUser.dto.AppUserResponse;
import nu.hack.bankcard.dto.BankCardResponse;
import nu.hack.category.dto.CategoryResponse;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponse {

    @Schema(description = "Bank Card")
    private BankCardResponse bankCard;

    @Schema(description = "Bank Card")
    private CategoryResponse category;

    @Schema(description = "percentage", example = "2.5")
    private BigDecimal percentage;

    @Schema(description = "Conditions", example = "Только через QR")
    private String conditions;

    @Schema(description = "С какого времени началась акция", example = "2025-03-10")
    private LocalDate dateFrom;

    @Schema(description = "Когда закончится акция", example = "2025-03-10")
    private LocalDate dateTo;
}