package fr.carrefour.kata.app.data.dto;

import fr.carrefour.kata.app.data.entity.Customer;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDto {

    private String lastname;
    private String firstname;
    private String email;

    public static CustomerDto fromEntity(Customer customer) {
        if (customer == null) return null;
        return CustomerDto.builder()
            .lastname(customer.getLastname())
            .firstname(customer.getFirstname())
            .email(customer.getEmail())
            .build();
      }
    
      public static Customer toEntity(CustomerDto customerDto) {
        if (customerDto == null) return null;
        return Customer.builder()
                .lastname(customerDto.getLastname())
                .firstname(customerDto.getFirstname())
                .email(customerDto.getEmail())
                .build();
      }

}
