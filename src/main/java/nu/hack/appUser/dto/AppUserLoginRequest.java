package nu.hack.appUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserLoginRequest {

    @Schema(description = "User's email", example = "aniyar@example.com")
    @NotBlank
    private String email;

    @Schema(description = "Password", example = "hha!22")
    @NotBlank
    private String password;
}
