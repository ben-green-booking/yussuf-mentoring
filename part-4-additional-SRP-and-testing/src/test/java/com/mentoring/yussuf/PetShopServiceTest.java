package com.mentoring.yussuf;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.repository.PetRepository;
import com.mentoring.yussuf.service.PetShopService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PetShopServiceTest {

    private PetRepository petRepository = new PetRepository(new HashMap<>());
    private PetShopService petShopService = new PetShopService(petRepository);

    @Test
    public void shouldReturnPetsGivenPetsExistInTheList() {

        var existingPet = petShopService.createPet(5, 300, "Fluffy", "Good kitty", "F", "Cat");
        var petId = existingPet.getId();
        Optional<Pet> pet = petShopService.getPetById(petId);
        assertThat(pet).isEqualTo(Optional.of(existingPet));
    }
}
