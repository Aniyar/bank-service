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
public class OfferGetRequest {

    @Schema(description = "User id", example = "1")
    private Integer userId;

    @Schema(description = "Bank Card id", example = "1")
    private Integer bankCardId;

    @Schema(description = "Category id", example = "1")
    private Integer categoryId;

    @Schema(description = "Флаг обозначающий активные офферы", example = "true")
    private Boolean active;
}