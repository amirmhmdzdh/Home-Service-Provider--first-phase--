package org.achareh.base.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseEntity<ID extends Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @Pattern(regexp = "yyyy-MM-dd")
    private LocalDateTime registrationTime = LocalDateTime.now();

}
