package nu.hack.appUser.service;

import lombok.RequiredArgsConstructor;
import nu.hack.appUser.dto.AppUserCreateRequest;
import nu.hack.appUser.dto.AppUserLoginRequest;
import nu.hack.appUser.dto.AppUserResponse;

import nu.hack.appUser.entity.AppUserEntity;
import nu.hack.appUser.mapper.AppUserMapper;
import nu.hack.appUser.repository.AppUserRepository;
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
            throw new IllegalArgumentException("User Already exists");
        }
        var user = AppUserMapper.INSTANCE.toEntity(request);
        user = userRepository.save(user);
        return AppUserMapper.INSTANCE.toResponse(user);
    }

    @Transactional(readOnly = true)
    public AppUserResponse findById(Integer id) {
        var user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
        return AppUserMapper.INSTANCE.toResponse(user);
    }

    @Transactional(readOnly = true)
    public AppUserResponse login(AppUserLoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("user not found"));
        if (user.getPassword().equals(request.getPassword())) {
            return AppUserMapper.INSTANCE.toResponse(user);
        }
        throw new IllegalArgumentException("invalid password");
    }
}
