package com.mentoring.yussuf.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.service.PetShopService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}


