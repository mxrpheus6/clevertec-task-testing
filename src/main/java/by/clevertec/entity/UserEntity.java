package by.clevertec.entity;

import by.clevertec.common.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private UUID id;

    private String username;

    private OffsetDateTime dateOfBirth;

    private UserType userType;

}
