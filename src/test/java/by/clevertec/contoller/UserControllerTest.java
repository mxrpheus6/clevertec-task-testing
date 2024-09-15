package by.clevertec.contoller;

import by.clevertec.domain.User;
import by.clevertec.exception.UserNotFoundException;
import by.clevertec.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static by.clevertec.common.UserType.ADMIN;
import static by.clevertec.common.UserType.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void getUsers() throws Exception {
        User user1 = new User(1L, "mxrpheus", OffsetDateTime.parse("2024-09-14T09:27:23.0046411+03:00"), USER);
        User user2 = new User(2L, "admin", OffsetDateTime.parse("2004-09-14T09:27:23.0046411+03:00"), ADMIN);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userService.getUsers()).thenReturn(users);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user-api/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("mxrpheus"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("2024-09-14T09:27:23.0046411+03:00"))
                .andExpect(jsonPath("$[0].userType").value("USER"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("admin"))
                .andExpect(jsonPath("$[1].dateOfBirth").value("2004-09-14T09:27:23.0046411+03:00"))
                .andExpect(jsonPath("$[1].userType").value("ADMIN"))
                .andDo(print());
    }

    @Test
    void getUserById() throws Exception {
        User user = new User(1L, "mxrpheus", OffsetDateTime.parse("2024-09-14T09:27:23.0046411+03:00"), ADMIN);

        when(userService.getUserById(1L)).thenReturn(user);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user-api/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("mxrpheus"))
                .andExpect(jsonPath("$.dateOfBirth").value("2024-09-14T09:27:23.0046411+03:00"))
                .andExpect(jsonPath("$.userType").value("ADMIN"))
                .andDo(print());
    }

    @Test
    void getUserById_notFound() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(new UserNotFoundException("User not found"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user-api/get/99"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User not found"))
                .andDo(print());
    }

    @Test
    void create() throws Exception {
        User user = new User(1L, "mxrpheus", OffsetDateTime.parse("2024-09-14T09:27:23.0046411+03:00"), ADMIN);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String userJson = objectMapper.writeValueAsString(user);

        when(userService.create(any(User.class))).thenReturn(user);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user-api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("mxrpheus"))
                .andExpect(jsonPath("$.dateOfBirth").value("2024-09-14T09:27:23.0046411+03:00"))
                .andExpect(jsonPath("$.userType").value("ADMIN"))
                .andDo(print());
    }

    @Test
    void create_throwsException() throws Exception {
        User user = new User(1L, "mxrpheus", OffsetDateTime.parse("2024-09-14T09:27:23.0046411+03:00"), ADMIN);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String userJson = objectMapper.writeValueAsString(user);

        when(userService.create(any(User.class))).thenThrow(new UserNotFoundException("User creation failed"));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user-api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User creation failed"))
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        User updatedUser = new User(1L, "mxrpheus", OffsetDateTime.parse("2024-09-14T09:27:23.0046411+03:00"), ADMIN);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String userJson = objectMapper.writeValueAsString(updatedUser);

        when(userService.update(anyLong(), any(User.class))).thenReturn(updatedUser);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/user-api/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("mxrpheus"))
                .andExpect(jsonPath("$.dateOfBirth").value("2024-09-14T09:27:23.0046411+03:00"))
                .andExpect(jsonPath("$.userType").value("ADMIN"))
                .andDo(print());
    }

    @Test
    void update_throwsException() throws Exception {
        User updatedUser = new User(1L, "mxrpheus", OffsetDateTime.parse("2024-09-14T09:27:23.0046411+03:00"), ADMIN);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String userJson = objectMapper.writeValueAsString(updatedUser);

        when(userService.update(anyLong(), any(User.class))).thenThrow(new UserNotFoundException("User update failed"));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/user-api/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User update failed"))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        Long userId = 1L;

        doNothing().when(userService).delete(userId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/user-api/delete/{id}", userId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}