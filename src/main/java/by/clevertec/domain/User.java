package by.clevertec.domain;

import by.clevertec.common.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;

    private String username;

    private OffsetDateTime dateOfBirth;

    private UserType userType;

}
