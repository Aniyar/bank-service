package nu.hack.offer.repository;


import nu.hack.offer.entity.OfferEntity;
import nu.hack.usercard.entity.UserCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfferRepository extends JpaRepository<OfferEntity, Integer>, JpaSpecificationExecutor<OfferEntity> {
}
