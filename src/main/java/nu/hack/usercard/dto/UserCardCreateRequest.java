package nu.hack.usercard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCardCreateRequest {

    @Schema(description = "User id", example = "1")
    @NotNull
    private Integer userId;

    @Schema(description = "Bank Card id", example = "1")
    @NotNull
    private Integer bankCardId;

    @Schema(description = "Card alias", example = "типа никнейм карточки")
    private String name;


    @Schema(description = "Card number", example = "5555 5555 5555 5555")
    private String cardNumber;

    @Schema(description = "valid until. Передавайте первый день месяца", example = "2025-05-01")
    private LocalDate validUntil;
}