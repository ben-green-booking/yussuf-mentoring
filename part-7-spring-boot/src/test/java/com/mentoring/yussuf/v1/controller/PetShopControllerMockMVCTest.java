package com.mentoring.yussuf.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.service.PetShopService;
import com.mentoring.yussuf.v1.dto.UpdatePetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PetShopController.class)
@AutoConfigureMockMvc
public class PetShopControllerMockMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetShopService petShopService;


    @Test
    void shouldReturnNewlyGeneratedPet() throws Exception {

        var pet = Pet.builder().name("Fluffy")
                .id(1)
                .age(10)
                .sold(false)
                .price(10)
                .description("nice kitty")
                .gender("Male")
                .species("Cat")
                .build();

        when(petShopService.createPet(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(pet);

        /* This is important for setting up the behaviour of the mock service.
        It ensures that when the createPet method is called, it
        returns a predictable value, so I can validate how the controller processes that response.
        */

        /*
        Testing that the controller calls the service method with
        the correct types of arguments, not necessarily the exact values.
        */

        mockMvc.perform(post("/v1/pets")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(pet)))
                .andExpect(status().isOk());

        when(petShopService.getPetById(1)).thenReturn(Optional.of(pet));

        mockMvc.perform(get("/v1/pets/1"))
                .andDo(print()) // print the details of an HTTP request and response.
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Fluffy")))
                .andExpect(content().string(containsString("10")))
                .andExpect(content().string(containsString("Cat")))
                .andExpect(content().string(containsString("nice kitty")));
    }

    @Test
    void shouldErrorMessageIfPetCreatedWithoutRequiredSpeciesField() throws Exception {
        var pet = Pet.builder()
                .id(1)
                .name("Fluffy")
                .age(10)
                .sold(false)
                .price(10)
                .description("nice kitty")
                .gender("Male")
                .build();

        mockMvc.perform(post("/v1/pets")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(pet))) // Pet without species
                .andExpect(status().isBadRequest()) // Expect a bad request status (400)
                .andExpect(content().string(containsString("Species is a mandatory field for a new pet"))); // Check for error message
        // handleRuntimeException method is dealing with error ensuring it sends the correct message each time
    }


    @Test
    void shouldReturnUpdatedPetDetails() throws Exception {

        int petId = 1;
        int newAge = 11;
        int newPrice = 15;
        boolean newSoldStatus = true;

        // Mock the service method
        doNothing().when(petShopService).updatePet(eq(petId), eq(newAge), eq(newPrice), eq(newSoldStatus));

        // Create the update DTO
        UpdatePetDTO updatePetDTO = new UpdatePetDTO(petId, newAge, newPrice, newSoldStatus);

        mockMvc.perform(patch("/v1/pets/{id}", petId)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updatePetDTO)))
                .andDo(print())
                .andExpect(status().isOk());

        // Verify that the service method was called with the correct parameters
        verify(petShopService).updatePet(petId, newAge, newPrice, newSoldStatus);
    }

    @Test
    void shouldEnsureControllerCallsCorrectServiceMethod() throws Exception {
        var pet = Pet.builder().name("Fluffy")
                .id(1)
                .age(10)
                .sold(false)
                .price(10)
                .description("nice kitty")
                .gender("Male")
                .species("Cat")
                .build();

        mockMvc.perform(post("/v1/pets")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(pet)));
//                .andExpect(status().isOk());

        when(petShopService.createPet(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(pet);

        verify(petShopService, times(1)).createPet(10, 10, "Fluffy", "nice kitty", "Male", "Cat");
    }

    @Test
    void shouldDeletePetWhenExists() throws Exception {
        int petId = 1;
        doNothing().when(petShopService).deletePet(petId); // When petShopService.deletePet(petId) is called, do nothing.

        mockMvc.perform(delete("/v1/pets/{id}", petId))
                .andExpect(status().isOk());

        verify(petShopService).deletePet(petId);
    }
}


