package fr.carrefour.kata.app.validator;

import fr.carrefour.kata.app.data.dto.DeliveryDto;
import fr.carrefour.kata.app.data.dto.DeliveryModeEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeliveryValidator {

    public static List<String> validate(DeliveryDto deliveryDto) {
        List<String> errors = new ArrayList<>();

        if (deliveryDto == null) {
            errors.add("Please fill in the delivery information");
            return errors;
        }
        if (deliveryDto.getCustomerId() == null) {
            errors.add("Please fill in the customer information");
        }
        if (deliveryDto.getDeliveryDate() == null) {
          errors.add("Delivery date is not valid");
        }
        if (Arrays.stream(DeliveryModeEnum.values())
                .filter(v -> v.equals(deliveryDto.getDeliveryMode()))
                .findFirst()
                .isEmpty()) {
            errors.add("Delivery mode is not valid");
        }

        return errors;
    }
}
