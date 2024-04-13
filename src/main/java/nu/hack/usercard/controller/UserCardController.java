package nu.hack.usercard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nu.hack.common.dto.PageResponse;
import nu.hack.usercard.dto.UserCardCreateRequest;
import nu.hack.usercard.dto.UserCardResponse;
import nu.hack.usercard.service.UserCardService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-cards")
@RequiredArgsConstructor
public class UserCardController {

    private final UserCardService userCardService;

    // Create a bank
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserCardCreateRequest request) {
        userCardService.create(request);
    }

    @GetMapping
    public PageResponse<UserCardResponse> findAll(@RequestParam(required = false) Integer userId,
                                                  @ParameterObject Pageable pageable) {
        return userCardService.findAll(userId, pageable);
    }

    @GetMapping("/{id}")
    public UserCardResponse findById(@PathVariable Integer id) {
        return userCardService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Integer id) {
        userCardService.deleteById(id);
    }

}
