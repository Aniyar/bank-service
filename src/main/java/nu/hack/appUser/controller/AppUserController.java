package nu.hack.appUser.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nu.hack.appUser.dto.AppUserCreateRequest;
import nu.hack.appUser.dto.AppUserLoginRequest;
import nu.hack.appUser.dto.AppUserResponse;
import nu.hack.appUser.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app-users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserResponse create(@Valid @RequestBody AppUserCreateRequest request) {
        return service.create(request);
    }

    @PostMapping("/login")
    public AppUserResponse login(@Valid @RequestBody AppUserLoginRequest request) {
        return service.login(request);
    }


    @GetMapping("/{id}")
    public AppUserResponse findById(@PathVariable Integer id) {
        return service.findById(id);
    }
}