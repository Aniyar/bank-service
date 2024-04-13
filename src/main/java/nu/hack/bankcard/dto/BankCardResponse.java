package nu.hack.bankcard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nu.hack.bank.dto.BankResponse;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankCardResponse {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "Bank")
    private BankResponse bank;

    @Schema(description = "Bank card's name", example = "Kaspi Gold")
    private String name;

    @Schema(description = "Link to bank card image", example = "Hhttps://www.google.com/url?sa=i&url=https%3A%2F%2Fcommons.wikimedia.org%2Fwiki%2FFile%3A%25D0%259A%25D0%25B0%25D1%2580%25D1%2582%25D0%25B0_Kaspi_Gold.png&psig=AOvVaw01uSZCEKuTK6Xr2NWFJjC9&ust=1713090733148000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPiJ2Lb-voUDFQAAAAAdAAAAABAJ")
    private String image;

    @Schema(description = "Comment", example = "Some comment")
    private String comment;
}