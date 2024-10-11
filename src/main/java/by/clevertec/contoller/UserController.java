package by.clevertec.contoller;

import by.clevertec.domain.User;
import by.clevertec.service.UserService;
import by.clevertec.service.impl.UserServiceImpl;
import by.clevertec.service.impl.proxy.UserServiceProxy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user-api")
public class UserController {

    private final UserService userServiceImpl;
    private final UserServiceProxy userServiceProxy;

    public UserController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.userServiceProxy = new UserServiceProxy(userServiceImpl);
    }

    @GetMapping("/get/all")
    public List<User> getUsers() {
        return userServiceProxy.getUsers();
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable Long id) {
        return userServiceProxy.getUserById(id);
    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userServiceProxy.create(user);
    }

    @PutMapping("/update/{id}")
    public User update(@PathVariable Long id, @RequestBody User updatedUser) {
        return userServiceProxy.update(id, updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userServiceProxy.delete(id);
    }
}
