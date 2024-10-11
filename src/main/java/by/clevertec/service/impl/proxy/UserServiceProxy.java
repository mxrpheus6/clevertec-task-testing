package by.clevertec.service.impl.proxy;

import by.clevertec.domain.User;
import by.clevertec.service.UserService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceProxy implements UserService {
    private final UserService userService;
    private final Map<Long, User> cache = new ConcurrentHashMap<>();
    private final List<Long> accessOrder = new LinkedList<>();
    private final Integer MAX_SIZE = 100;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    private void addToCache(Long id, User user) {
        if (cache.size() >= MAX_SIZE) {
            Long oldestId = accessOrder.removeFirst();
            cache.remove(oldestId);
        }

        cache.put(id, user);
        accessOrder.add(id);
    }

    @Override
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Override
    public User getUserById(Long id) {
        if (cache.containsKey(id)) {
            accessOrder.remove(id);
            accessOrder.add(id);
            return cache.get(id);
        }

        User user = userService.getUserById(id);
        addToCache(id, user);
        return user;
    }

    @Override
    public User create(User user) {
        User createdUser = userService.create(user);
        addToCache(createdUser.getId(), createdUser);
        return createdUser;
    }

    @Override
    public User update(Long id, User updatedUser) {
        User updatedUserResult = userService.update(id, updatedUser);
        addToCache(id, updatedUserResult);
        return updatedUserResult;
    }

    @Override
    public void delete(Long id) {
        cache.remove(id);
        accessOrder.remove(id);
        userService.delete(id);
    }
}