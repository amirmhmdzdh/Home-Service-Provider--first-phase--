package org.achareh.model.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.achareh.model.address.Address;
import org.achareh.model.order.Orders;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class Customer extends Users {

    @NotNull(message = "Recharging the wallet is required!")
    private Long credit;

    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
            , fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Address> addressList;

    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Orders> ordersList;

    public void addAddress(Address address) {
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        address.setCustomer(this);
        addressList.add(address);
    }
}


