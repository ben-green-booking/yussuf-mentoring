package com.mentoring.yussuf.v2.controller;

import com.mentoring.yussuf.v1.controller.PetShopController;
import com.mentoring.yussuf.v2.dto.CreatePetDTO;
import com.mentoring.yussuf.v2.dto.GetPetDTO;
import com.mentoring.yussuf.v2.dto.UpdatePetDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class V2PetShopControllerShould {

    private V2PetShopController subject;

    @BeforeEach
    public void setUp() {
        subject = new V2PetShopController();
    }

    @Test
    public void returnTheIdOfACreatedPet() {
        assertThat(subject.createPet(CreatePetDTO.builder()
                .petInformation("Cat|Max|M|5|300|A very good boy")
                .build())).isNotZero();
    }

    @Test
    public void failValidationIfSpeciesIsNotProvidedWhenCreatingAPet() {
        assertThatThrownBy(() -> subject.createPet(CreatePetDTO.builder()
                .petInformation("|Max|M|5|300|A very good boy")
                .build())).hasMessage("Species is a mandatory field for a new pet");
        assertThat(subject.getPetsBy(null, false)).isEmpty();
    }

    @Test
    public void returnACreatedPetIfTheCorrectIdIsPassedIn() {
        var petId = subject.createPet(CreatePetDTO.builder()
                .petInformation("Cat|Max|M|5|300|A very good boy")
                .build());

        assertThat(subject.getPetById(petId)).hasValue(GetPetDTO.builder()
                .petInfo(petId + "|Cat|Max|M|5|300|A very good boy")
                .build());
    }

    @Test
    public void updateAnExistingPetCorrectly() {
        var petId = subject.createPet(CreatePetDTO.builder()
                .petInformation("Cat|Max|M|5|300|A very good boy")
                .build());

        subject.updatePet(UpdatePetDTO.builder().id(petId).species("Dog").build());

        assertThat(subject.getPetById(petId)).hasValue(GetPetDTO.builder()
                .petInfo(petId + "|Dog|Max|M|5|300|A very good boy")
                .build());
    }

    @Test
    public void returnAnErrorIfNoPetExistsWithTheGivenId() {
        assertThatThrownBy(() -> subject.updatePet(UpdatePetDTO.builder().id(1).species("Dog").build()))
                .hasMessage("Pet with id 1 does not exist");
    }

    @Test
    public void returnAllPetsOfACertainSpeciesWhenFiltered() {
        var maxId = subject.createPet(CreatePetDTO.builder()
                .petInformation("Cat|Max|M|5|300|A very good boy")
                .build());

        var steveId = subject.createPet(CreatePetDTO.builder()
                .petInformation("Cat|Steve|M|4|5|Slightly less nice cat")
                .build());

        subject.createPet(CreatePetDTO.builder()
                .petInformation("Elephant|Jumbo|F|2|3000|Very big")
                .build());

        assertThat(subject.getPetsBy("Cat", false)).containsExactlyInAnyOrder(GetPetDTO.builder()
                        .petInfo(maxId + "|Cat|Max|M|5|300|A very good boy")
                        .build(),
                GetPetDTO.builder()
                        .petInfo(steveId + "|Cat|Steve|M|4|5|Slightly less nice cat")
                        .build()
        );
    }

    @Test
    public void returnAllPetsWhenNotFiltered() {
        var maxId = subject.createPet(CreatePetDTO.builder()
                .petInformation("Cat|Max|M|5|300|A very good boy")
                .build());

        var jumboId = subject.createPet(CreatePetDTO.builder()
                .petInformation("Elephant|Jumbo|F|2|3000|Very big")
                .build());

        assertThat(subject.getPetsBy("Cat", false)).containsExactlyInAnyOrder(GetPetDTO.builder()
                        .petInfo(maxId + "|Cat|Max|M|5|300|A very good boy")
                        .build(),
                GetPetDTO.builder()
                        .petInfo(jumboId + "|Elephant|Jumbo|F|2|3000|Very big")
                        .build()
        );
    }

    @Test
    public void returnOnlyNotSoldAnimalsWhenFiltered() {
        var maxId = subject.createPet(CreatePetDTO.builder()
                .petInformation("Cat|Max|M|5|300|A very good boy")
                .build());

        var steveId = subject.createPet(CreatePetDTO.builder()
                .petInformation("Cat|Steve|M|4|5|Slightly less nice cat")
                .build());

        PetShopController petShopController = new PetShopController();


        petShopController.updatePet(com.mentoring.yussuf.v1.dto.UpdatePetDTO.builder().id(steveId).sold(true).age(34).price(300).build());

        assertThat(subject.getPetsBy("Cat", true)).containsOnly(GetPetDTO.builder()
                .petInfo(maxId + "|Cat|Max|M|5|300|A very good boy")
                .build()
        );
    }

    @Test
    void deleteAnExistingPetWithTheGivenId() {
        var petId = subject.createPet(CreatePetDTO.builder()
                .petInformation("Cat|Max|M|5|300|A very good boy")
                .build());

        subject.deletePet(petId);

        assertThat(subject.getPetById(petId)).isEmpty();
    }
}
