package nu.hack.offer.service;

import lombok.RequiredArgsConstructor;
import nu.hack.appUser.service.AppUserService;
import nu.hack.bankcard.service.BankCardService;
import nu.hack.category.service.CategoryService;
import nu.hack.offer.dto.OfferCreateRequest;
import nu.hack.offer.dto.OfferGetRequest;
import nu.hack.offer.dto.OfferResponse;
import nu.hack.offer.entity.OfferEntity;
import nu.hack.offer.mapper.OfferMapper;
import nu.hack.offer.repository.OfferRepository;
import nu.hack.common.dto.PageResponse;
import nu.hack.common.exception.CommonException;
import nu.hack.usercard.service.UserCardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static nu.hack.common.entity.AuditEntity_.ID;
import static nu.hack.common.specification.CommonSpecification.*;
import static nu.hack.offer.entity.OfferEntity_.*;


@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final BankCardService bankCardService;
    private final CategoryService categoryService;
    private final UserCardService userCardService;
    private final AppUserService appUserService;

    @Transactional
    public void create(OfferCreateRequest request) {
        var entity = OfferMapper.INSTANCE.toEntity(request);

        var bankCard = bankCardService.getById(request.getBankCardId());
        entity.setBankCard(bankCard);

        if (request.getCategoryId() != null) {
            var category = categoryService.getById(request.getCategoryId());
            entity.setCategory(category);
        }
        if (request.getUserId() != null) {
            var user = appUserService.getById(request.getUserId());
            entity.setUser(user);
        }
        offerRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public PageResponse<OfferResponse> findAll(OfferGetRequest request, Pageable pageable) {
        Specification<OfferEntity> where = alwaysTrue();

        where = where.and(nullSafeEquals(BANK_CARD, ID, request.getBankCardId()));

        if (request.getCategoryId() != null) {
            Specification<OfferEntity> categorySpec = attributeEquals(CATEGORY, ID, request.getCategoryId());
            categorySpec = categorySpec.or(isNull(CATEGORY, ID));
            where = where.and(categorySpec);
        }

        if (request.getUserId() == null) {
            where = where.and(isNull(USER, ID));
        } else {
            Specification<OfferEntity> userSpec = attributeEquals(USER, ID, request.getUserId());
            userSpec = userSpec.or(isNull(USER, ID));
            where = where.and(userSpec);
        }

        if (request.getActive() != null && request.getActive()) {
            where = where.and(between(DATE_FROM, DATE_TO, LocalDate.now()));
        }
        Pageable pageable1 = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), OfferEntity.DEFAULT_SORT);
        var offers = offerRepository.findAll(where, pageable1);
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

    @Transactional(readOnly = true)
    public OfferResponse findBest(Integer userId, Integer categoryId) {
        var bankCardIds = userCardService.getAll(userId)
                .stream()
                .map(userCard -> userCard.getBankCard().getId())
                .toList();

        Optional<OfferEntity> offer = offerRepository.findBest(bankCardIds, LocalDate.now(), categoryId);
        return offer.map(OfferMapper.INSTANCE::toResponse).orElse(null);
    }
}
