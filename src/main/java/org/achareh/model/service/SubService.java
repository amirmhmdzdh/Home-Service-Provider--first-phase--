package org.achareh.model.service;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.achareh.base.entity.BaseEntity;
import org.achareh.model.order.Orders;
import org.achareh.model.user.Specialist;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SubService extends BaseEntity<Long> {

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Invalid name!")
    @Column(unique = true)
    private String name;

    @NotNull(message = "Filling it is mandatory")
    private Long basePrice;


    @Pattern(regexp = "^[a-zA-Z ]{5,}$", message = "Invalid Description!")
    private String description;

    @ManyToOne
    private MainService mainService;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<Specialist> specialistList;


    @OneToMany(mappedBy = "subServices",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Orders> ordersList;
}
