package nu.hack.bank.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankCreateRequest {

    @Schema(description = "Bank's name", example = "Kaspi")
    @NotBlank
    private String name;

    @Schema(description = "Link to bank image", example = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fru.wikipedia.org%2Fwiki%2F%25D0%25A4%25D0%25B0%25D0%25B9%25D0%25BB%3ALogo_of_Kaspi_bank.png&psig=AOvVaw00zybeQVBGlwtJwEHVACf9&ust=1713083100552000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCKC1jv_hvoUDFQAAAAAdAAAAABAE")
    private String image;
}