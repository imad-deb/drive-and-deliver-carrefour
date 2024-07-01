package fr.carrefour.kata.app.validator;

import fr.carrefour.kata.app.data.dto.CustomerDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerValidator {

    public static List<String> validate(CustomerDto customerDto) {
        List<String> errors = new ArrayList<>();

        if (customerDto == null) {
            errors.add("Please fill in the customer information");
            return errors;
        }
        if (!StringUtils.hasText(customerDto.getLastname())) {
            errors.add("Lastname is not valid");
        }
        if (!StringUtils.hasText(customerDto.getFirstname())) {
            errors.add("Firstname is not valid");
        }
        if (!StringUtils.hasText(customerDto.getEmail())) {
            errors.add("Email address is not valid");
        }
        return errors;
    }
}
