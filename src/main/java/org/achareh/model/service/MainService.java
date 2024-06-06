package org.achareh.model.service;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.achareh.base.entity.BaseEntity;

import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class MainService extends BaseEntity<Long> {

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Invalid name!")
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "mainService", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<SubService> subServicesList;
}
