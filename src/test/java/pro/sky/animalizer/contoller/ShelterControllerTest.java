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
import pro.sky.animalizer.controller.ShelterController;
import pro.sky.animalizer.model.Shelter;
import pro.sky.animalizer.service.ShelterService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ShelterControllerTest {
    @Mock
    private ShelterService shelterService;
    @InjectMocks
    private ShelterController shelterController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(shelterController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateShelter() throws Exception {
        Shelter shelterTest = new Shelter();
        String userJson = objectMapper.writeValueAsString(shelterTest);
        mockMvc.perform(post("/shelter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    void testGetShelterById() throws Exception {
        mockMvc.perform(get("/shelter/{id}", 1L))
                .andExpect(status().isOk());

    }

    @Test
    void testDeleteShelterById() throws Exception {
        mockMvc.perform(get("/shelter/{id}", 1L))
                .andExpect(status().isOk());

    }

    @Test
    void testGetAllShelters() throws Exception {
        Collection<Shelter> listTest = new ArrayList<>();
        when(shelterService.getAllShelters()).thenReturn((List<Shelter>) listTest);
        mockMvc.perform(get("/shelter"))
                .andExpect(status().isOk());
    }

    @Test
    void testEditShelter() throws Exception {
        Shelter shelterTest = new Shelter();
        Shelter shelterActual = new Shelter();
        when(shelterService.editShelter(2L, shelterTest)).thenReturn(shelterActual);
        mockMvc.perform(put("/shelter/{id}", 2L));
        Assertions.assertEquals(shelterService.editShelter(2L, shelterTest), shelterActual);
    }
}