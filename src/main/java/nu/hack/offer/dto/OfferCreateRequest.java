package nu.hack.offer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nu.hack.bankcard.entity.BankCardEntity;
import nu.hack.category.entity.CategoryEntity;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferCreateRequest {

    @Schema(description = "Bank Card id", example = "1")
    @NotNull
    private Integer bankCardId;

    @Schema(description = "Category id", example = "1")
    @NotNull
    private Integer categoryId;

    @Schema(description = "percentage", example = "2.5")
    @NotNull
    private BigDecimal percentage;

    @Schema(description = "Conditions", example = "Только через QR")
    private String conditions;

    @Schema(description = "С какого времени началась акция", example = "2025-03-10")
    private LocalDate dateFrom;

    @Schema(description = "Когда закончится акция", example = "2025-03-10")
    private LocalDate dateTo;
}