package com.mentoring.yussuf;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.repository.ListBackedPetRepository;
import com.mentoring.yussuf.repository.PetRepository;
import com.mentoring.yussuf.service.PetShopService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PetShopServiceTest {

    private final PetRepository petRepository = new ListBackedPetRepository(new ArrayList<Pet>());
    private final PetShopService petShopService = new PetShopService(petRepository);

    @Test
    public void shouldReturnPetsGivenPetsExistInTheList() {

        var existingPet = petShopService.createPet(5, 300, "Fluffy", "Good kitty", "F", "Cat");
        var petId = existingPet.getId();
        Optional<Pet> pet = petShopService.getPetById(petId);
        assertThat(pet).isEqualTo(Optional.of(existingPet));
    }

    @Test
    public void shouldUpdatePetGivenChangeInValues() {

        var existingPet = petShopService.createPet(5, 300, "Fluffy", "Good kitty", "F", "Cat");
        petShopService.updatePet(existingPet.getId(), 10, 150, true);

        var updatedPet = petShopService.getPetById(existingPet.getId()).get();

        assertThat(updatedPet.getAge()).isEqualTo(10);
        assertThat(updatedPet.getPrice()).isEqualTo(150);
        assertThat(updatedPet.isSold()).isTrue();
    }

    @Test
    public void shouldReturnAnErrorIfNoPetExistsWithTheGivenId() {
        assertThatThrownBy(() -> petShopService.updatePet(10, 10, 10, false))
                .hasMessage("Pet with id 10 does not exist");
    }

    @Test
    public void shouldReturnCorrectPetsMatchingSpeciesFilter() {

        var fluffyCat = petShopService.createPet(5, 300, "Fluffy", "Good kitty", "F", "Cat");
        var mittensCat = petShopService.createPet(5, 300, "Mittens", "Cute kitty", "M", "Cat");
        var jakDog = petShopService.createPet(1, 300, "Jak", "Little puppy", "M", "Dog");

        var catsFilter = petShopService.getPetsBy("Cat", true);
        assertThat(catsFilter).contains(fluffyCat, mittensCat);
        assertThat(catsFilter).doesNotContain(jakDog);
    }

    @Test
    public void shouldReturnAllPetsIfNoSpeciesIsSpecified() {
        var fluffyCat = petShopService.createPet(5, 300, "Fluffy", "Good kitty", "F", "Cat");
        var mittensCat = petShopService.createPet(5, 300, "Mittens", "Cute kitty", "M", "Cat");
        var jakDog = petShopService.createPet(1, 300, "Jak", "Little puppy", "M", "Dog");
        var nullFilter = petShopService.getPetsBy(null, false);

        assertThat(nullFilter).contains(fluffyCat, mittensCat, jakDog);
    }

    @Test
    public void shouldDeleteCorrectPetGivenPetid() {

        var fluffyCat = petShopService.createPet(5, 300, "Fluffy", "Good kitty", "F", "Cat");
        petShopService.deletePet(fluffyCat.getId());
        assertThat(petRepository.findById(fluffyCat.getId())).isEmpty();
    }

    @Test
    public void shouldReturnCorrectPetGivenPetId() {
        var fluffyCat = petShopService.createPet(5, 300, "Fluffy", "Good kitty", "F", "Cat");
        var mittensPet = petShopService.createPet(5, 300, "Mittens", "Cute kitty", "M", "Cat");

        var mittensFilter = petShopService.getPetById(mittensPet.getId());
        assertThat(mittensFilter).contains(mittensPet);
        var fluffyFilter = petShopService.getPetById(fluffyCat.getId());
        assertThat(fluffyFilter).contains(fluffyCat);
    }


    @Test
    public void shouldUpdatePetGivenChangeInSpecies() {

        var existingPet = petShopService.createPet(5, 300, "Fluffy", "Good kitty", "F", "Cat");
        petShopService.updateSpecies(existingPet.getId(), "Lion");

        var updatedPet = petShopService.getPetById(existingPet.getId()).get();

        assertThat(updatedPet.getSpecies()).isEqualTo("Lion");
    }
}
