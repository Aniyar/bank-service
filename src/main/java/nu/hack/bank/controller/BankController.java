package nu.hack.bank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nu.hack.bank.dto.BankCreateRequest;
import nu.hack.bank.dto.BankResponse;
import nu.hack.bank.entity.BankEntity;
import nu.hack.bank.service.BankService;
import nu.hack.common.dto.PageResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    // Create a bank
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody BankCreateRequest request) {
        bankService.create(request);
    }

    @GetMapping
    public PageResponse<BankResponse> findAll(@ParameterObject Pageable pageable) {
        return bankService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public BankResponse findById(@PathVariable Integer id) {
        return bankService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Integer id) {
        bankService.deleteById(id);
    }

}
