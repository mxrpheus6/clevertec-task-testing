package by.clevertec.service.impl.proxy;

import by.clevertec.domain.User;
import by.clevertec.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceProxy implements UserService {
    private final UserService userService;
    private Map<Long, User> cache = new ConcurrentHashMap<>();

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Override
    public User getUserById(Long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        User user = userService.getUserById(id);
        cache.put(id, user);
        return user;
    }

    @Override
    public User create(User user) {
        User createdUser = userService.create(user);
        cache.put(createdUser.getId(), createdUser);
        return createdUser;
    }

    @Override
    public User update(Long id, User updatedUser) {
        User updatedUserResult = userService.update(id, updatedUser);
        cache.put(id, updatedUserResult);
        return updatedUserResult;
    }

    @Override
    public void delete(Long id) {
        cache.remove(id);
        userService.delete(id);
    }
}
