package nu.hack.bank.service;

import lombok.RequiredArgsConstructor;
import nu.hack.bank.dto.BankCreateRequest;
import nu.hack.bank.entity.BankEntity;
import nu.hack.bank.mapper.BankMapper;
import nu.hack.bank.repository.BankRepository;
import nu.hack.common.dto.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nu.hack.bank.entity.BankEntity_.NAME;
import static nu.hack.common.specification.CommonSpecification.attributeEquals;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    @Transactional
    public void create(BankCreateRequest request) {
        var entity = BankMapper.INSTANCE.toEntity(request);
        Specification<BankEntity> where = attributeEquals(NAME, request.getName());
        if (bankRepository.exists(where)) {
            throw new IllegalArgumentException("Bank already exists");
        }
        bankRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public PageResponse<BankEntity> findAll(Pageable pageable) {
        var banks = bankRepository.findAll(pageable);
        return PageResponse.fromPage(banks);
    }

    @Transactional(readOnly = true)
    public BankEntity findById(Integer id) {
        return bankRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bank with this id not found"));
    }

    @Transactional
    public void deleteById(Integer id) {
        bankRepository.deleteById(id);
    }
}
