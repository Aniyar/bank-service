package nu.hack.bank.repository;


import nu.hack.bank.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCardRepository extends JpaRepository<BankEntity, Integer> {
}
