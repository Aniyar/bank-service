package nu.hack.usercard.repository;


import nu.hack.usercard.entity.UserCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserCardRepository extends JpaRepository<UserCardEntity, Integer>, JpaSpecificationExecutor<UserCardEntity> {
}
