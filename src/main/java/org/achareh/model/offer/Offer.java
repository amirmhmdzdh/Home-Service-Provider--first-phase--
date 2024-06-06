package org.achareh.model.offer;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.achareh.base.entity.BaseEntity;
import org.achareh.model.offer.enums.OfferStatus;
import org.achareh.model.order.Orders;
import org.achareh.model.user.Specialist;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Offer extends BaseEntity<Long> {

    private LocalDateTime executionTime;

    private LocalDateTime endTime;

    private LocalDateTime sendTime;


    @NotNull(message = "Filling it is mandatory")
    private Long proposedPrice;


    @Enumerated(value = EnumType.STRING)
    private OfferStatus offerStatus;


    @ManyToOne (fetch = FetchType.EAGER)
    private Orders orders;


    @ManyToOne (fetch = FetchType.EAGER)
    private Specialist specialist;
}
