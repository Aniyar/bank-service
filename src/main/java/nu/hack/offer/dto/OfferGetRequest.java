package nu.hack.offer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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