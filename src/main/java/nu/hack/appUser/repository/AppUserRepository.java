package nu.hack.appUser.repository;

import nu.hack.appUser.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Integer>, JpaSpecificationExecutor<AppUserEntity> {
    Optional<AppUserEntity> findByEmail(String email);
}
