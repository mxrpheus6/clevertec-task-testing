package by.clevertec.domain;

import by.clevertec.common.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private OffsetDateTime dateOfBirth;

    private UserType userType;
}
