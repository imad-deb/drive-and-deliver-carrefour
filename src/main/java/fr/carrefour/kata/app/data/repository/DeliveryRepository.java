package fr.carrefour.kata.app.data.repository;

import fr.carrefour.kata.app.data.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}
