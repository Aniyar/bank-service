package nu.hack.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nu.hack.category.dto.CategoryCreateRequest;
import nu.hack.category.dto.CategoryResponse;
import nu.hack.category.service.CategoryService;
import nu.hack.common.dto.PageResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Create a category
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CategoryCreateRequest request) {
        categoryService.create(request);
    }

    @GetMapping
    public PageResponse<CategoryResponse> findAll(@ParameterObject Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Integer id) {
        categoryService.deleteById(id);
    }

}
