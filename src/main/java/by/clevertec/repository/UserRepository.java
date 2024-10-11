package by.clevertec.repository;

import by.clevertec.common.UserType;
import by.clevertec.common.builder.UserEntityBuilder;
import by.clevertec.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private static List<UserEntity> db = new ArrayList<>();

    static {
        db.add(new UserEntityBuilder()
                .setId(1L)
                .setUsername("user1")
                .setDateOfBirth(OffsetDateTime.now())
                .setUserType(UserType.USER)
                .build());
        db.add(new UserEntity(2L, "admin1", OffsetDateTime.now(), UserType.ADMIN));
        db.add(new UserEntity(3L, "user2", OffsetDateTime.now(), UserType.USER));
    }

    public Optional<List<UserEntity>> getUsers() {
        if (db == null) {
            return Optional.empty();
        }

        return Optional.of(db);
    }

    public Optional<UserEntity> getUserById(Long id) {
        for (UserEntity userEntity : db) {
            if (userEntity.getId() == id) {
                return Optional.of(userEntity);
            }
        }

        return Optional.empty();
    }

    public Optional<UserEntity> create(UserEntity userEntity) {
        if (userEntity == null) {
            return Optional.empty();
        }

        db.add(userEntity);
        return Optional.of(userEntity);
    }

    public Optional<UserEntity> update(Long id, UserEntity newUserEntity) {
        if (id == null || newUserEntity == null) {
            return Optional.empty();
        }

        newUserEntity.setId(id);
        Optional<UserEntity> targetUser = getUserById(id);
        if (targetUser.isPresent()) {
            targetUser.map(userEntity ->
                    userEntity.setId(id)
                            .setUsername(newUserEntity.getUsername())
                            .setUserType(newUserEntity.getUserType())
                            .setDateOfBirth(newUserEntity.getDateOfBirth())
            );
        } else {
            targetUser = Optional.empty();
        }

        return targetUser;
    }

    public void delete(Long id) {
        for (int i = 0; i < db.size(); i++) {
            if (db.get(i).getId() == id){
                db.remove(i);
                return;
            }
        }
    }
}
