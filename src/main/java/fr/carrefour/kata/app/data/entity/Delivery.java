package fr.carrefour.kata.app.data.entity;

import fr.carrefour.kata.app.data.dto.DeliveryModeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId")
    private Integer deliveryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "deliveryMode", nullable = false)
    private DeliveryModeEnum deliveryMode;

    @Column(name = "deliveryDate", nullable = false)
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "Customer_customerId", referencedColumnName = "customerId")
    private Customer customer;
}
