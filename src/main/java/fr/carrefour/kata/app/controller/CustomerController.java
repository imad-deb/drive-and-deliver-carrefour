package fr.carrefour.kata.app.controller;

import fr.carrefour.kata.app.data.dto.CustomerDto;
import fr.carrefour.kata.app.data.dto.DeliveryDto;
import fr.carrefour.kata.app.data.entity.Customer;
import fr.carrefour.kata.app.data.entity.Delivery;
import fr.carrefour.kata.app.service.CustomerService;
import fr.carrefour.kata.app.service.DeliveryService;
import fr.carrefour.kata.app.validator.CustomerValidator;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.CUSTOMER_ENDPOINT)
@Validated
@AllArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        List<CustomerDto> clientDTOs = customerService.getAll()
                .stream()
                .map(CustomerDto::fromEntity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable Integer id) {
        CustomerDto clientDTO = CustomerDto.fromEntity(customerService.getById(id));
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> save(@RequestBody Customer customer) {

        CustomerDto customerDTO = CustomerDto.fromEntity(customer);
        List<String> errors = CustomerValidator.validate(customerDTO);
        if (!errors.isEmpty()) {
            log.error("Can not save the customer: {}", customerDTO);
            throw new EntityNotValidException("Customer not valid", ErrorCode.CUSTOMER_NOT_VALID, errors);
        }

        customerService.save(customer);
        log.info("Customer saved successfully.");
        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customerService.delete(id);
        log.info("Customer deleted successfully.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addDelivery")
    public ResponseEntity<DeliveryDto> addDelivery(@RequestBody DeliveryDto deliveryDto) {
        
        List<String> errors = DeliveryValidator.validate(deliveryDto);
        if (!errors.isEmpty()) {
            log.error("Can not save the delivery: {}", deliveryDto);
            throw new EntityNotValidException("Delivery not valid", ErrorCode.DELIVERY_NOT_VALID, errors);
        }

        Customer existingCustomer = customerService.getById(deliveryDto.getCustomerId());

        Delivery delivery = DeliveryDto.toEntity(deliveryDto);
        delivery.setCustomer(existingCustomer);

        deliveryService.save(delivery);
        log.info("Delivery added successfully.");
        return new ResponseEntity<>(deliveryDto, HttpStatus.CREATED);
    }

}
