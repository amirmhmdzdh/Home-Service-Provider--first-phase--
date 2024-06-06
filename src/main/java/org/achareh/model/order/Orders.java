package org.achareh.model.order;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.achareh.base.entity.BaseEntity;
import org.achareh.model.address.Address;
import org.achareh.model.comment.Comment;
import org.achareh.model.offer.Offer;
import org.achareh.model.order.enums.OrderStatus;
import org.achareh.model.service.SubService;
import org.achareh.model.user.Customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Order_Table")
@SuperBuilder
public class Orders extends BaseEntity<Long> {

    private LocalDateTime executionTime;


    private LocalDateTime endTime;


    @NotNull(message = "Filling it is mandatory")
    private Long proposedPrice;


    @Pattern(regexp = "^[a-zA-Z ]{5,}$", message = "Invalid Description!")
    private String description;


    @Enumerated(value = STRING)
    private OrderStatus orderStatus;


    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;


    @ManyToOne(fetch = FetchType.EAGER)
    private SubService subServices;


    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER
            , cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<Offer> offerList;


    @OneToOne
    private Comment comment;


    @OneToOne
    private Address address;
}
