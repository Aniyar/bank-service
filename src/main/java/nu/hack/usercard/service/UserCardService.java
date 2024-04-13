package nu.hack.usercard.service;

import lombok.RequiredArgsConstructor;
import nu.hack.appUser.service.AppUserService;
import nu.hack.bankcard.service.BankCardService;
import nu.hack.common.dto.PageResponse;
import nu.hack.common.exception.CommonException;
import nu.hack.usercard.dto.UserCardCreateRequest;
import nu.hack.usercard.dto.UserCardResponse;
import nu.hack.usercard.entity.UserCardEntity;
import nu.hack.usercard.mapper.UserCardMapper;
import nu.hack.usercard.repository.UserCardRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static nu.hack.common.specification.CommonSpecification.alwaysTrue;
import static nu.hack.common.specification.CommonSpecification.attributeEquals;
import static nu.hack.usercard.entity.UserCardEntity_.USER;
import static nu.hack.common.entity.AuditEntity_.ID;



@Service
@RequiredArgsConstructor
public class UserCardService {

    private final UserCardRepository userCardRepository;
    private final BankCardService bankCardService;
    private final AppUserService userService;

    @Transactional
    public void create(UserCardCreateRequest request) {
        var entity = UserCardMapper.INSTANCE.toEntity(request);

        var bankCard = bankCardService.getById(request.getBankCardId());
        entity.setBankCard(bankCard);

        var user = userService.getById(request.getUserId());
        entity.setUser(user);

        userCardRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public PageResponse<UserCardResponse> findAll(Integer userId, Pageable pageable) {
        Specification<UserCardEntity> where = alwaysTrue();
        if (userId != null) {
            where = where.and(attributeEquals(USER, ID, userId));
        }
        var bankCards = userCardRepository.findAll(where, pageable);
        return PageResponse.fromPage(bankCards.map(UserCardMapper.INSTANCE::toResponse));
    }

    @Transactional(readOnly = true)
    public List<UserCardEntity> getAll(Integer userId) {
        Specification<UserCardEntity> where = alwaysTrue();
        if (userId != null) {
            where = where.and(attributeEquals(USER, ID, userId));
        }
        return userCardRepository.findAll(where);
    }

    @Transactional(readOnly = true)
    public UserCardResponse findById(Integer id) {
        var card = getById(id);
        return UserCardMapper.INSTANCE.toResponse(card);
    }

    @Transactional(readOnly = true)
    public UserCardEntity getById(Integer id) {
        return userCardRepository.findById(id).orElseThrow(() -> new CommonException(404, "BankCard with this id not found"));
    }

    @Transactional
    public void deleteById(Integer id) {
        userCardRepository.deleteById(id);
    }
}
