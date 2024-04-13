package nu.hack.offer.repository;


import nu.hack.offer.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferEntity, Integer>, JpaSpecificationExecutor<OfferEntity> {

    @Query("SELECT o FROM OfferEntity o " +
            "WHERE o.bankCard.id IN :bankCardIds " +
            "AND o.dateFrom <= :date " +
            "AND o.dateTo >= :date " +
            "AND ( o.category.id = null OR o.category.id = :categoryId )" +
            "ORDER BY o.percentage DESC")
    Optional<OfferEntity> findBest(
            @Param("bankCardIds") List<Integer> bankCardIds,
            @Param("date") LocalDate date,
            @Param("categoryId") Integer categoryId);
}
