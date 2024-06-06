package org.achareh.model.address;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.achareh.base.entity.BaseEntity;
import org.achareh.model.user.Customer;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Address extends BaseEntity<Long> {

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Invalid Province!")
    @Column(unique = true)
    private String province;


    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Invalid!")
    @Column(unique = true)
    private String city;


    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Invalid Avenue")
    private String avenue;


    @Pattern(regexp = "^\\d{1,4}$", message = "Invalid House Number")
    private String houseNumber;


    @ManyToOne
    private Customer customer;

}
