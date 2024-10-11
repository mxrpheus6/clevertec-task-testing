package by.clevertec.service;

import by.clevertec.domain.User;
import by.clevertec.entity.UserEntity;
import by.clevertec.exception.UserNotFoundException;
import by.clevertec.mapper.UserMapper;
import by.clevertec.repository.UserRepository;
import by.clevertec.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static by.clevertec.common.UserType.ADMIN;
import static by.clevertec.common.UserType.USER;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    private List<UserEntity> userEntities;
    private List<User> userDomains;

    @BeforeEach
    void initRepository() {
        userEntities = Arrays.asList(
                new UserEntity(1L, "mxrpheus", OffsetDateTime.now(), ADMIN),
                new UserEntity(2L, "abc", OffsetDateTime.now(), USER),
                new UserEntity(3L, "trump", OffsetDateTime.now(), ADMIN)
        );
        userDomains = Arrays.asList(
                new User(1L, "mxrpheus", OffsetDateTime.now(), ADMIN),
                new User(2L, "abc", OffsetDateTime.now(), USER),
                new User(3L, "trump", OffsetDateTime.now(), ADMIN)
        );

        lenient().when(userRepository.getUsers()).thenReturn(Optional.of(userEntities));
        lenient().when(userRepository.getUserById(1L)).thenReturn(Optional.of(userEntities.get(0)));
        lenient().when(userRepository.getUserById(2L)).thenReturn(Optional.of(userEntities.get(1)));
        lenient().when(userRepository.getUserById(3L)).thenReturn(Optional.of(userEntities.get(2)));

        lenient().when(userMapper.toDomains(userEntities)).thenReturn(userDomains);
        lenient().when(userMapper.toDomain(userEntities.get(0))).thenReturn(userDomains.get(0));
        lenient().when(userMapper.toDomain(userEntities.get(1))).thenReturn(userDomains.get(1));
        lenient().when(userMapper.toDomain(userEntities.get(2))).thenReturn(userDomains.get(2));
    }

    @Test
    void getUsers() {
        List<User> users = userServiceImpl.getUsers();

        Assertions.assertEquals(3, users.size());
        Assertions.assertEquals(userDomains.get(0), users.get(0));
        Assertions.assertEquals("mxrpheus", users.get(0).getUsername());
        Assertions.assertEquals(userDomains.get(1), users.get(1));
        Assertions.assertEquals("abc", users.get(1).getUsername());
        Assertions.assertEquals(userDomains.get(2), users.get(2));
        Assertions.assertEquals("trump", users.get(2).getUsername());
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void getUserById(Long id) {
        User user = userServiceImpl.getUserById(id);

        Assertions.assertEquals(userDomains.get((int)(id - 1L)), user);
    }

    @Test
    void getUserById_throwsException() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById(4L));
    }

    @Test
    void create() {
        UserEntity newUserEntity = new UserEntity(null, "chelovek", OffsetDateTime.now(), USER);
        User newUser = new User(4L, "chelovek", OffsetDateTime.now(), USER);
        UserEntity createdUserEntity = new UserEntity(4L, "chelovek", OffsetDateTime.now(), USER);
        User createdUser = new User(4L, "chelovek", OffsetDateTime.now(), USER);

        when(userMapper.toEntity(newUser)).thenReturn(newUserEntity);
        when(userRepository.create(newUserEntity)).thenReturn(Optional.of(createdUserEntity));
        when(userMapper.toDomain(createdUserEntity)).thenReturn(createdUser);

        User result = userServiceImpl.create(newUser);
        Assertions.assertEquals(createdUser, result);

        verify(userRepository).create(userEntityCaptor.capture());

        UserEntity capturedEntity = userEntityCaptor.getValue();
        Assertions.assertEquals("chelovek", capturedEntity.getUsername());
        Assertions.assertEquals(USER, capturedEntity.getUserType());
    }

    @Test
    void create_throwsException() {
        User newUser = new User(4L, "chelovek", OffsetDateTime.now(), USER);
        UserEntity newUserEntity = new UserEntity(null, "chelovek", OffsetDateTime.now(), USER);

        when(userMapper.toEntity(newUser)).thenReturn(newUserEntity);
        when(userRepository.create(newUserEntity)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userServiceImpl.create(newUser));
    }

    @Test
    void update() {
        Long id = 3L;
        UserEntity newUserEntity = new UserEntity(null, "chelovek", OffsetDateTime.now(), USER);
        User newUser = new User(id, "chelovek", OffsetDateTime.now(), USER);
        UserEntity updatedUserEntity = new UserEntity(id, "chelovek", OffsetDateTime.now(), USER);
        User updatedUser = new User(id, "chelovek", OffsetDateTime.now(), USER);

        when(userMapper.toEntity(newUser)).thenReturn(newUserEntity);
        when(userRepository.update(id, newUserEntity)).thenReturn(Optional.of(updatedUserEntity));
        when(userMapper.toDomain(updatedUserEntity)).thenReturn(updatedUser);

        User result = userServiceImpl.update(id, newUser);
        Assertions.assertEquals(updatedUser, result);
    }

    @Test
    void update_throwsException() {
        Long id = 4L;
        User newUser = new User(id, "chelovek", OffsetDateTime.now(), USER);
        UserEntity newUserEntity = new UserEntity(null, "chelovek", OffsetDateTime.now(), USER);

        when(userMapper.toEntity(newUser)).thenReturn(newUserEntity);
        when(userRepository.update(id, newUserEntity)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userServiceImpl.update(id, newUser));
    }

    @Test
    void delete() {
        userServiceImpl.delete(1L);

        verify(userRepository, times(1)).delete(1L);
    }
}