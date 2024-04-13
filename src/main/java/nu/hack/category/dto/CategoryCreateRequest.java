package nu.hack.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {

    @Schema(description = "Category's name", example = "Одежда")
    @NotBlank
    private String name;

    @Schema(description = "Link to category image", example = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flaticon.com%2Ffree-icon%2Fclothing_925072&psig=AOvVaw1YE64ZMfKRIdhXMGtH8qBm&ust=1713088731372000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNiRvvz2voUDFQAAAAAdAAAAABAJ")
    private String image;
}