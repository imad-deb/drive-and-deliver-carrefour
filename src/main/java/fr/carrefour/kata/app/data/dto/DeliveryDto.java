package fr.carrefour.kata.app.data.dto;

import fr.carrefour.kata.app.data.entity.Customer;
import fr.carrefour.kata.app.data.entity.Delivery;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class DeliveryDto {

    private Integer customerId;
    private Date deliveryDate;
    private DeliveryModeEnum deliveryMode;

    public static DeliveryDto fromEntity(Delivery delivery) {
        if (delivery == null) return null;
        return DeliveryDto.builder()
            .deliveryMode(delivery.getDeliveryMode())
            .deliveryDate(delivery.getDeliveryDate())
            .customerId(delivery.getCustomer().getCustomerId())
            .build();
      }
    
      public static Delivery toEntity(DeliveryDto deliveryDto) {
        if (deliveryDto == null) return null;
        return Delivery.builder()
                .deliveryMode(deliveryDto.getDeliveryMode())
                .deliveryDate(deliveryDto.getDeliveryDate())
                .customer(Customer.builder().customerId(deliveryDto.getCustomerId()).build())
                .build();
      }
      
}
