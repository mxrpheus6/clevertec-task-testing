package by.clevertec.entity;

import by.clevertec.common.UserType;
import by.clevertec.strategy.UserStrategy;
import by.clevertec.strategy.impl.AdminUserStrategy;
import by.clevertec.strategy.impl.RegularUserStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;

    private String username;

    private OffsetDateTime dateOfBirth;

    private UserType userType;

    private UserStrategy userStrategy;

    public UserEntity(Long id, String username, OffsetDateTime dateOfBirth, UserType type) {
        this.id = id;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.userStrategy = getStrategyByType(type);
    }

    private UserStrategy getStrategyByType(UserType role) {
        switch (role) {
            case ADMIN:
                return new AdminUserStrategy();
            case USER:
                return new RegularUserStrategy();
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

    public void performRoleSpecificAction() {
        userStrategy.performRoleSpecificAction();
    }
}
