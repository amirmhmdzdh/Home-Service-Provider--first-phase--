package org.achareh.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.achareh.base.entity.BaseEntity;
import org.achareh.model.user.enums.Role;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
@MappedSuperclass
@SuperBuilder
public class Users extends BaseEntity<Long> {

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Invalid name format!")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z ]{3,}$", message = "Invalid last name format!")
    private String lastName;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email!")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password is not strong!")
    private String password;


    @Enumerated(value = EnumType.STRING)
    private Role role;

    private LocalDateTime registrationTime;

}
