package es.price.rest.api.infrastructure.storage.price.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.price.rest.api.infrastructure.storage.price.model.PriceEntity;

@Repository
public interface PricesRepository extends JpaRepository<PriceEntity, Long> {
}
