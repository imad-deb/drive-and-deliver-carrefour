package fr.carrefour.kata.app.controller;

import fr.carrefour.kata.app.data.dto.DeliveryDto;
import fr.carrefour.kata.app.data.entity.Delivery;
import fr.carrefour.kata.app.service.DeliveryService;
import fr.carrefour.kata.app.validator.DeliveryValidator;
import fr.carrefour.kata.core.exception.EntityNotValidException;
import fr.carrefour.kata.core.exception.ErrorCode;
import fr.carrefour.kata.core.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.DELIVERY_ENDPOINT)
@Validated
@AllArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<Delivery>> getAll() {
        List<Delivery> livraisons = deliveryService.getAll();
        return new ResponseEntity<>(livraisons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getById(@PathVariable int id) {
        Delivery livraison = deliveryService.getById(id);
        return new ResponseEntity<>(livraison, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Delivery> save(@RequestBody Delivery delivery) {
        DeliveryDto deliveryDto = DeliveryDto.fromEntity(delivery);
        List<String> errors = DeliveryValidator.validate(deliveryDto);
        if (!errors.isEmpty()) {
            log.error("Can not save the delivery: {}", deliveryDto);
            throw new EntityNotValidException("Delivery not valid", ErrorCode.DELIVERY_NOT_VALID, errors);
        }

        Delivery savedLivraison = deliveryService.save(delivery);
        log.info("Delivery saved successfully.");
        return new ResponseEntity<>(savedLivraison, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deliveryService.delete(id);
        log.info("Delivery deleted successfully.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
