package nu.hack.usercard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nu.hack.appUser.dto.AppUserResponse;
import nu.hack.bankcard.dto.BankCardResponse;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCardResponse {

    @Schema(description = "User")
    private AppUserResponse user;

    @Schema(description = "Bank Card")
    private BankCardResponse bankCard;

    @Schema(description = "Card alias", example = "типа никнейм карточки")
    private String name;

    @Schema(description = "Card number", example = "5555 5555 5555 5555")
    private String cardNumber;

    @Schema(description = "valid until. Передавайте первый день месяца", example = "2025-05-01")
    private LocalDate validUntil;
}