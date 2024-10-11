package by.clevertec.domain.builder;

import by.clevertec.common.UserType;
import by.clevertec.domain.User;

import java.time.OffsetDateTime;

public class UserBuilder {

    private Long id;

    private String username;

    private OffsetDateTime dateOfBirth;

    private UserType userType;

    public User build() {
        return new User(id, username, dateOfBirth, userType);
    }

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setDateOfBirth(OffsetDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserBuilder setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }
}
