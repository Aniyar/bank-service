package nu.hack.bankcard.repository;


import nu.hack.bankcard.entity.BankCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BankCardRepository extends JpaRepository<BankCardEntity, Integer>, JpaSpecificationExecutor<BankCardEntity> {
}
