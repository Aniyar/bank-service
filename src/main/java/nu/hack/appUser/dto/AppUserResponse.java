package nu.hack.appUser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponse {

    @Schema(description = "User's id", example = "1")
    private Integer id;

    @Schema(description = "User's email", example = "aniyar@example.com")
    private String email;

    @Schema(description = "User's name", example = "Aniyar")
    private String name;
}
