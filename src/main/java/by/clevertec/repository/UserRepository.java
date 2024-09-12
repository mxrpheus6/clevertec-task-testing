package by.clevertec.repository;

import by.clevertec.common.UserType;
import by.clevertec.entity.UserEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    private static final List<UserEntity> db = List.of(
            new UserEntity(UUID.randomUUID(), "user1", OffsetDateTime.now(), UserType.USER),
            new UserEntity(UUID.randomUUID(), "admin1", OffsetDateTime.now(), UserType.ADMIN),
            new UserEntity(UUID.randomUUID(), "user2", OffsetDateTime.now(), UserType.USER)
    );

    public List<UserEntity> getUsers() {
        return db;
    }

    public UserEntity getUserById(UUID id) {
        return db.get(0);
    }

    public UserEntity create(UserEntity userEntity) {
        return userEntity;
    }

    public UserEntity update(UUID id, UserEntity newUserEntity) {
        return newUserEntity.setId(id);
    }

    public void delete(UUID id) {
        //without body
    }
}
