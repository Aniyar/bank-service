package nu.hack.offer.service;

import lombok.RequiredArgsConstructor;
import nu.hack.offer.dto.OfferCreateRequest;
import nu.hack.offer.dto.OfferResponse;
import nu.hack.offer.mapper.OfferMapper;
import nu.hack.offer.repository.OfferRepository;
import nu.hack.common.dto.PageResponse;
import nu.hack.common.exception.CommonException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    @Transactional
    public void create(OfferCreateRequest request) {
        var entity = OfferMapper.INSTANCE.toEntity(request);
        offerRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public PageResponse<OfferResponse> findAll(Pageable pageable) {
        var offers = offerRepository.findAll(pageable);
        return PageResponse.fromPage(offers.map(OfferMapper.INSTANCE::toResponse));
    }

    @Transactional(readOnly = true)
    public OfferResponse findById(Integer id) {
        var offer = offerRepository.findById(id).orElseThrow(() -> new CommonException(404, "Offer with this id not found"));
        return OfferMapper.INSTANCE.toResponse(offer);
    }

    @Transactional
    public void deleteById(Integer id) {
        offerRepository.deleteById(id);
    }
}
