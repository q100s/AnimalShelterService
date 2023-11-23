package pro.sky.animalizer.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pro.sky.animalizer.controller.UserController;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.model.UserType;
import pro.sky.animalizer.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateUser() throws Exception {
        User userTest = new User(1L, "TelegramNick");
        String userJson = objectMapper.writeValueAsString(userTest);
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/user/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUserById() throws Exception {
        mockMvc.perform(delete("/user/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserByIdOne() throws Exception {
        User userTest = new User(1L, "TelegramNick");
        when(userService.findUserById(1L)).thenReturn(userTest);
        mockMvc.perform(get("/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.telegramNick").value("TelegramNick"));
    }

    @Test
    void testEditUserById() throws Exception {
        User userTest = new User(1L, "Nikolay");
        User userActual = new User(1L, "Tanya");
        when(userService.editUser(1L, userTest)).thenReturn(userActual);
        mockMvc.perform(put("/user/{id}", 2L));
        Assertions.assertEquals(userService.editUser(1L, userTest), userActual);
    }

    @Test
    void testConnectUserAndPet() throws Exception {
        User userTest = new User(1L, "Nikolay");
        Pet petTest = new Pet(2L, "Tobik");
        User userActual = new User(1L, "NIkolay", "NIk", "79160000000");
        when(userService.connectUserAndPet(1L, 2L)).thenReturn(userActual);
        mockMvc.perform(put("/user/setPet?userId=1&petId=2}"));
        Assertions.assertEquals(userService.connectUserAndPet(1L, 2L), userActual);
    }

    @Test
    void testGetAllUsers() throws Exception {
        Collection<User> listTest = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn((List<User>) listTest);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUserApprove() throws Exception {
        mockMvc.perform(delete("/user/approve?id=1"))
                .andExpect(status().isOk());
    }
}