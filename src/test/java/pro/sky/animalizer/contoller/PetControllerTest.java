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
import pro.sky.animalizer.controller.PetController;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.service.PetService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {
    @Mock
    private PetService petService;
    @InjectMocks
    private PetController petController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreatePet() throws Exception {
        Pet petTest = new Pet();
        String petJson = objectMapper.writeValueAsString(petTest);
        mockMvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPetById() throws Exception {
        mockMvc.perform(get("/pet/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPetByUserId() throws Exception {
        mockMvc.perform(get("/pet/by{userId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePetById() throws Exception {
        mockMvc.perform(delete("/pet/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllPets() throws Exception {
        Collection<Pet> petListTest = new ArrayList<>();
        when(petService.getAllPets()).thenReturn((List<Pet>) petListTest);
        mockMvc.perform(get("/pet"))
                .andExpect(status().isOk());
    }

    @Test
    void testEditPetById() throws Exception {
        Pet petTest = new Pet(2L, "Tobik");
        Pet petActual = new Pet(2L, "Sharik");
        when(petService.editPet(2L, petTest)).thenReturn(petActual);
        mockMvc.perform(put("/pet/{id}", 2L));
        Assertions.assertEquals(petService.editPet(2L, petTest), petActual);
    }
}