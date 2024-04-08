package es.price.rest.api.domain.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.price.rest.api.domain.repository.model.Prices;

@Repository
public interface PricesRepository extends JpaRepository<Prices, Long> {
  @Query("SELECT p FROM Prices p WHERE p.productId = ?1 AND p.brandId = ?2 AND p.startDate <= ?3 AND p.endDate >= ?3 ORDER BY p.priority desc")
  List<Prices> findPricesByProductIdAndBrandIdAndDate(String productId, String brandId,
      OffsetDateTime date);

  default Optional<Prices> findPriceByProductIdAndBrandIdAndDateByPriority(String productId,
      String brandId, OffsetDateTime date) {
    final List<Prices> prices = findPricesByProductIdAndBrandIdAndDate(productId, brandId, date);
    return prices.isEmpty() ? Optional.empty() : Optional.of(prices.getFirst());
  }
}
