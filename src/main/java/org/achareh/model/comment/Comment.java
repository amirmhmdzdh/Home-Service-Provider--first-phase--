package org.achareh.model.comment;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.achareh.base.entity.BaseEntity;
import org.achareh.model.order.Orders;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Comment extends BaseEntity<Long> {

    @NotNull(message = "Filling it is mandatory")
    private Double star;

    private String textComment;

    @OneToOne
    private Orders orders;

}
