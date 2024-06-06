package org.achareh.model.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.achareh.model.offer.Offer;
import org.achareh.model.service.SubService;
import org.achareh.model.user.enums.SpecialistStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Specialist extends Users {

    @Lob
    private byte[] image;

    private Double star;

    @NotNull(message = "Filling it is mandatory")
    private Long credit;

    @Enumerated(value = EnumType.STRING)
    private SpecialistStatus status;

    @ManyToMany(mappedBy = "specialistList", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @ToString.Exclude
    private List<SubService> subServicesList;


    @OneToMany(mappedBy = "specialist",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<Offer> offerList;

    public void addSubServices(SubService subServices) {
        this.subServicesList.add(subServices);
        subServices.getSpecialistList().add(this);
    }
}
