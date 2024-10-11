package by.clevertec.common.builder;

import by.clevertec.common.UserType;
import by.clevertec.entity.UserEntity;

import java.time.OffsetDateTime;

public class UserEntityBuilder {
    private Long id;

    private String username;

    private OffsetDateTime dateOfBirth;

    private UserType userType;

    public UserEntity build() {
        return new UserEntity(id, username, dateOfBirth, userType);
    }

    public UserEntityBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserEntityBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserEntityBuilder setDateOfBirth(OffsetDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserEntityBuilder setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }
}
