package nu.hack.bankcard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nu.hack.bank.dto.BankCreateRequest;
import nu.hack.bankcard.dto.BankCardCreateRequest;
import nu.hack.bankcard.dto.BankCardResponse;
import nu.hack.bankcard.service.BankCardService;
import nu.hack.common.dto.PageResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bank-cards")
@RequiredArgsConstructor
public class BankCardController {

    private final BankCardService bankCardService;

    // Create a bank
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody BankCardCreateRequest request) {
        bankCardService.create(request);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Valid @RequestBody BankCardCreateRequest request) {
        bankCardService.update(id, request);
    }
    @GetMapping
    public PageResponse<BankCardResponse> findAll(@RequestParam(required = false) Integer bankId,
                                                  @ParameterObject Pageable pageable) {
        return bankCardService.findAll(bankId, pageable);
    }

    @GetMapping("/{id}")
    public BankCardResponse findById(@PathVariable Integer id) {
        return bankCardService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Integer id) {
        bankCardService.deleteById(id);
    }

}
