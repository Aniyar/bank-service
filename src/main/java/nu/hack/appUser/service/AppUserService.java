package nu.hack.appUser.service;

import lombok.RequiredArgsConstructor;
import nu.hack.appUser.dto.AppUserCreateRequest;
import nu.hack.appUser.dto.AppUserLoginRequest;
import nu.hack.appUser.dto.AppUserResponse;

import nu.hack.appUser.entity.AppUserEntity;
import nu.hack.appUser.mapper.AppUserMapper;
import nu.hack.appUser.repository.AppUserRepository;
import nu.hack.common.exception.CommonException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nu.hack.appUser.entity.AppUserEntity_.EMAIL;
import static nu.hack.common.specification.CommonSpecification.attributeEquals;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepository;

    @Transactional
    public AppUserResponse create(AppUserCreateRequest request) {
        Specification<AppUserEntity> where = attributeEquals(EMAIL, request.getEmail());
        if (userRepository.exists(where)) {
            throw new CommonException(400, "User Already exists");
        }
        var user = AppUserMapper.INSTANCE.toEntity(request);
        user = userRepository.save(user);
        return AppUserMapper.INSTANCE.toResponse(user);
    }

    @Transactional(readOnly = true)
    public AppUserResponse findById(Integer id) {
        var user = getById(id);
        return AppUserMapper.INSTANCE.toResponse(user);
    }

    @Transactional(readOnly = true)
    public AppUserEntity getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new CommonException(404, "user not found"));
    }

    @Transactional(readOnly = true)
    public AppUserResponse login(AppUserLoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CommonException(404, "user not found"));
        if (user.getPassword().equals(request.getPassword())) {
            return AppUserMapper.INSTANCE.toResponse(user);
        }
        throw new CommonException(400, "invalid password");
    }
}
