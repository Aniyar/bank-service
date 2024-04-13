package nu.hack.offer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nu.hack.offer.dto.OfferCreateRequest;
import nu.hack.offer.dto.OfferResponse;
import nu.hack.offer.service.OfferService;
import nu.hack.common.dto.PageResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    // Create a offer
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody OfferCreateRequest request) {
        offerService.create(request);
    }

    @GetMapping
    public PageResponse<OfferResponse> findAll(@ParameterObject Pageable pageable) {
        return offerService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public OfferResponse findById(@PathVariable Integer id) {
        return offerService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Integer id) {
        offerService.deleteById(id);
    }

}
