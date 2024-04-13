package nu.hack.bank.service;

import lombok.RequiredArgsConstructor;
import nu.hack.bank.dto.BankCreateRequest;
import nu.hack.bank.dto.BankResponse;
import nu.hack.bank.entity.BankEntity;
import nu.hack.bank.mapper.BankMapper;
import nu.hack.bank.repository.BankRepository;
import nu.hack.common.dto.PageResponse;
import nu.hack.common.exception.CommonException;
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
            throw new CommonException(400, "Bank already exists");
        }
        bankRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public PageResponse<BankResponse> findAll(Pageable pageable) {
        var banks = bankRepository.findAll(pageable);
        return PageResponse.fromPage(banks.map(BankMapper.INSTANCE::toResponse));
    }

    @Transactional(readOnly = true)
    public BankResponse findById(Integer id) {
        var bank = bankRepository.findById(id).orElseThrow(() -> new CommonException(404, "Bank with this id not found"));
        return BankMapper.INSTANCE.toResponse(bank);
    }

    @Transactional(readOnly = true)
    public BankEntity getEntityById(Integer id) {
        return bankRepository.findById(id).orElseThrow(() -> new CommonException(404, "Bank with this id not found"));
    }



    @Transactional
    public void deleteById(Integer id) {
        bankRepository.deleteById(id);
    }

    @Transactional
    public void update(Integer id, BankCreateRequest request) {
        var entity = getEntityById(id);
        entity = BankMapper.INSTANCE.toEntity(request, entity);
        bankRepository.save(entity);
    }
}
