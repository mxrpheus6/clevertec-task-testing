package by.clevertec.service;

import by.clevertec.domain.User;
import by.clevertec.entity.UserEntity;
import by.clevertec.mapper.UserMapper;
import by.clevertec.mapper.UserMapperImpl;
import by.clevertec.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapper = new UserMapperImpl();

    public List<User> getUsers() {
        List<UserEntity> users = userRepository.getUsers();

        return userMapper.toDomains(users);
    }

    public User getUserById(UUID id) {
        UserEntity user = userRepository.getUserById(UUID.randomUUID());

        return userMapper.toDomain(user);
    }

    public User create(User user) {
        UserEntity userEntity = userMapper.toEntity(user);

        return userMapper.toDomain(userRepository.create(userEntity));
    }

    public User update(UUID id, User updatedUser) {
        UserEntity userEntity = userMapper.toEntity(updatedUser);

        UserEntity user = userRepository.update(id, userEntity);

        return userMapper.toDomain(user);
    }

    public void delete(UUID id) {
        userRepository.delete(id);
    }
}
