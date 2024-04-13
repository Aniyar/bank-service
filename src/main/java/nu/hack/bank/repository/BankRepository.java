package nu.hack.bank.repository;


import nu.hack.bank.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BankRepository extends JpaRepository<BankEntity, Integer>, JpaSpecificationExecutor<BankEntity> {
}
