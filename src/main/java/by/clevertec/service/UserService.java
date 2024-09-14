package by.clevertec.service;

import by.clevertec.domain.User;
import by.clevertec.entity.UserEntity;
import by.clevertec.exception.UserNotFoundException;
import by.clevertec.mapper.UserMapper;
import by.clevertec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getUsers() {
        Optional<List<UserEntity>> users = userRepository.getUsers();

        if (users.isPresent()) {
            return userMapper.toDomains(users.get());
        }

        return List.of();
    }

    public User getUserById(Long id) {
        Optional<UserEntity> user = userRepository.getUserById(id);

        if (user.isPresent()) {
            return userMapper.toDomain(user.get());
        }

        throw new UserNotFoundException("User id=" + id + " not found");
    }

    public User create(User user) {
        UserEntity userEntity = userMapper.toEntity(user);

        Optional<UserEntity> createdUser = userRepository.create(userEntity);

        if (createdUser.isPresent()) {
            return userMapper.toDomain(createdUser.get());
        }

        throw new UserNotFoundException("User creation failed");
    }

    public User update(Long id, User updatedUser) {
        UserEntity userEntity = userMapper.toEntity(updatedUser);

        Optional<UserEntity> user = userRepository.update(id, userEntity);

        if (user.isPresent()) {
            return userMapper.toDomain(user.get());
        }

        throw new UserNotFoundException("Update failed");
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }
}
