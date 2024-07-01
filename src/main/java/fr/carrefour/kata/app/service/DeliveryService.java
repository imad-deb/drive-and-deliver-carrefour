package fr.carrefour.kata.app.service;

import fr.carrefour.kata.app.data.entity.Delivery;
import fr.carrefour.kata.app.data.repository.DeliveryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public List<Delivery> getAll() {
        return deliveryRepository.findAll();
    }

    public Delivery getById(int id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public void delete(int id) {
        deliveryRepository.deleteById(id);
    }
}
