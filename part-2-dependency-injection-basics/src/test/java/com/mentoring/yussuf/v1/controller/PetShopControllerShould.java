package com.mentoring.yussuf.v1.controller;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.v1.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PetShopControllerShould {

    private PetShopController subject;

    @BeforeEach
    public void setUp() {
        subject = new PetShopController(new ArrayList<Pet>());
    }

    @Test
    public void returnTheIdOfACreatedPet() {
        assertThat(subject.createPet(CreatePetDTO.builder()
                .age(2)
                .description("Nice cat")
                .name("Socks")
                .gender("F")
                .species("Cat")
                .price(20)
                .build())).isNotZero();
    }

    @Test
    public void failValidationIfSpeciesIsNotProvidedWhenCreatingAPet() {
        assertThatThrownBy(() -> subject.createPet(CreatePetDTO.builder()
                .age(2)
                .description("Nice cat")
                .name("Socks")
                .gender("F")
                .price(20)
                .build())).hasMessage("Species is a mandatory field for a new pet");
        assertThat(subject.getPetsBy(null, false)).isEmpty();
    }

    @Test
    public void returnACreatedPetIfTheCorrectIdIsPassedIn() {
        var petId = subject.createPet(CreatePetDTO.builder()
                .age(5)
                .description("Nice cat")
                .name("Max")
                .gender("M")
                .species("Cat")
                .price(20)
                .build());

        assertThat(subject.getPetById(petId)).hasValue(GetPetDTO.builder()
                .id(petId)
                .age(5)
                .description("Nice cat")
                .name("Max")
                .gender("M")
                .species("Cat")
                .price(20)
                .sold(false)
                .build());
    }

    @Test
    public void updateAnExistingPetCorrectly() {
        var petId = subject.createPet(CreatePetDTO.builder()
                .age(5)
                .description("Nice cat")
                .name("Max")
                .gender("M")
                .species("Cat")
                .price(20)
                .build());

        subject.updatePet(UpdatePetDTO.builder().id(petId).sold(true).age(34).price(300).build());

        assertThat(subject.getPetById(petId)).hasValue(GetPetDTO.builder()
                .id(petId)
                .age(34)
                .description("Nice cat")
                .name("Max")
                .gender("M")
                .species("Cat")
                .price(300)
                .sold(true)
                .build());
    }

    @Test
    public void returnAnErrorIfNoPetExistsWithTheGivenId() {
        assertThatThrownBy(() -> subject.updatePet(UpdatePetDTO.builder().id(1).sold(true).age(34).price(300).build()))
                .hasMessage("Pet with id 1 does not exist");
    }

    @Test
    public void returnAllPetsOfACertainSpeciesWhenFiltered() {
        var maxId = subject.createPet(CreatePetDTO.builder()
                .age(5)
                .description("Nice cat")
                .name("Max")
                .gender("M")
                .species("Cat")
                .price(20)
                .build());

        var steveId = subject.createPet(CreatePetDTO.builder()
                .age(4)
                .description("Slightly less nice cat")
                .name("Steve")
                .gender("M")
                .species("Cat")
                .price(5)
                .build());

        subject.createPet(CreatePetDTO.builder()
                .age(2)
                .description("It's an elephant")
                .name("Jumbo")
                .gender("F")
                .species("Elephant")
                .price(3000)
                .build());

        assertThat(subject.getPetsBy("Cat", false)).containsExactlyInAnyOrder(GetPetDTO.builder()
                        .id(maxId)
                        .age(5)
                        .description("Nice cat")
                        .name("Max")
                        .gender("M")
                        .species("Cat")
                        .price(20)
                        .sold(false)
                        .build(),
                GetPetDTO.builder()
                        .id(steveId)
                        .age(4)
                        .description("Slightly less nice cat")
                        .name("Steve")
                        .gender("M")
                        .species("Cat")
                        .price(5)
                        .sold(false)
                        .build()
        );
    }

    @Test
    public void returnAllPetsWhenNotFiltered() {
        var maxId = subject.createPet(CreatePetDTO.builder()
                .age(5)
                .description("Nice cat")
                .name("Max")
                .gender("M")
                .species("Cat")
                .price(20)
                .build());

        var jumboId = subject.createPet(CreatePetDTO.builder()
                .age(2)
                .description("It's an elephant")
                .name("Jumbo")
                .gender("F")
                .species("Elephant")
                .price(3000)
                .build());

        assertThat(subject.getPetsBy(null, false)).containsExactlyInAnyOrder(GetPetDTO.builder()
                        .id(maxId)
                        .age(5)
                        .description("Nice cat")
                        .name("Max")
                        .gender("M")
                        .species("Cat")
                        .price(20)
                        .sold(false)
                        .build(),
                GetPetDTO.builder()
                        .id(jumboId)
                        .age(2)
                        .description("It's an elephant")
                        .name("Jumbo")
                        .gender("F")
                        .species("Elephant")
                        .price(3000)
                        .sold(false)
                        .build()
        );
    }

    @Test
    public void returnOnlyNotSoldAnimalsWhenFiltered() {
        var maxId = subject.createPet(CreatePetDTO.builder()
                .age(5)
                .description("Nice cat")
                .name("Max")
                .gender("M")
                .species("Cat")
                .price(20)
                .build());

        var catToUpdate = subject.createPet(CreatePetDTO.builder()
                .age(4)
                .description("Slightly less nice cat")
                .name("Steve")
                .gender("M")
                .species("Cat")
                .price(5)
                .build());

        subject.createPet(CreatePetDTO.builder()
                .age(2)
                .description("It's an elephant")
                .name("Jumbo")
                .gender("F")
                .species("Elephant")
                .price(3000)
                .build());

        subject.updatePet(UpdatePetDTO.builder().id(catToUpdate).sold(true).age(34).price(300).build());

        assertThat(subject.getPetsBy("Cat", true)).containsOnly(GetPetDTO.builder()
                .id(maxId)
                .age(5)
                .description("Nice cat")
                .name("Max")
                .gender("M")
                .species("Cat")
                .price(20)
                .sold(false)
                .build()
        );
    }

    @Test
    void deleteAnExistingPetWithTheGivenId() {
        var petId = subject.createPet(CreatePetDTO.builder()
                .age(5)
                .description("Nice cat")
                .name("Max")
                .gender("M")
                .species("Cat")
                .price(20)
                .build());

        subject.deletePet(petId);

        assertThat(subject.getPetById(petId)).isEmpty();
    }
}
