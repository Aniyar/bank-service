package nu.hack.bankcard.service;

import lombok.RequiredArgsConstructor;
import nu.hack.bank.service.BankService;
import nu.hack.bankcard.dto.BankCardCreateRequest;
import nu.hack.bankcard.dto.BankCardResponse;
import nu.hack.bankcard.entity.BankCardEntity;
import nu.hack.bankcard.mapper.BankCardMapper;
import nu.hack.bankcard.repository.BankCardRepository;
import nu.hack.common.dto.PageResponse;
import nu.hack.common.exception.CommonException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nu.hack.bankcard.entity.BankCardEntity_.BANK;
import static nu.hack.bankcard.entity.BankCardEntity_.NAME;
import static nu.hack.common.entity.AuditEntity_.ID;
import static nu.hack.common.specification.CommonSpecification.*;

@Service
@RequiredArgsConstructor
public class BankCardService {

    private final BankCardRepository cardRepository;
    private final BankService bankService;

    @Transactional
    public void create(BankCardCreateRequest request) {
        Specification<BankCardEntity> where = attributeEquals(NAME, request.getName());
        where = where.and(attributeEquals(BANK, ID, request.getBankId()));
        if (cardRepository.exists(where)) {
            throw new CommonException(400, "BankCardCard already exists");
        }

        var entity = BankCardMapper.INSTANCE.toEntity(request);
        var bank = bankService.getEntityById(request.getBankId());
        entity.setBank(bank);
        cardRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public PageResponse<BankCardResponse> findAll(Integer bankId, Pageable pageable) {
        Specification<BankCardEntity> where = nullSafeEquals(BANK, ID, bankId);
        var bankCards = cardRepository.findAll(where, pageable);
        return PageResponse.fromPage(bankCards.map(BankCardMapper.INSTANCE::toResponse));
    }

    @Transactional(readOnly = true)
    public BankCardResponse findById(Integer id) {
        var card = getById(id);
        return BankCardMapper.INSTANCE.toResponse(card);
    }

    @Transactional(readOnly = true)
    public BankCardEntity getById(Integer id) {
        return cardRepository.findById(id).orElseThrow(() -> new CommonException(404, "BankCard with this id not found"));
    }

    @Transactional
    public void deleteById(Integer id) {
        cardRepository.deleteById(id);
    }
}
