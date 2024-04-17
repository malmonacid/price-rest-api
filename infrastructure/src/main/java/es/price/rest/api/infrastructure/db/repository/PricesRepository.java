package es.price.rest.api.infrastructure.db.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.price.rest.api.infrastructure.db.repository.model.Prices;

@Repository
public interface PricesRepository extends JpaRepository<Prices, Long> {

  @Query("SELECT p FROM Prices p WHERE p.productId = ?1 AND p.brandId = ?2 AND p.startDate <= ?3 AND p.endDate >= ?3 ORDER BY p.priority desc LIMIT 1")
  Optional<Prices> findPricesByProductIdAndBrandIdAndDate(String productId, String brandId,
      OffsetDateTime date);

}
