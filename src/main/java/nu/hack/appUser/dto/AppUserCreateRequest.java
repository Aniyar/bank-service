package nu.hack.appUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserCreateRequest {

    @Schema(description = "User's email", example = "aniyar@example.com")
    @NotBlank
    private String email;

    @Schema(description = "Password", example = "hha!22")
    @NotBlank
    private String password;

    @Schema(description = "User's name", example = "Aniyar")
    @NotBlank
    private String name;
}
