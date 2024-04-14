package nu.hack.common.scheduled;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParsedOfferRepository extends JpaRepository<ParsedOfferEntity, Integer> {
    boolean existsByLink(String link);
}
