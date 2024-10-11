package by.clevertec.service;

import by.clevertec.domain.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(Long id);
    User create(User user);
    User update(Long id, User updatedUser);
    void delete(Long id);
}
