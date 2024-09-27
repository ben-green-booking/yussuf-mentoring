package com.mentoring.yussuf;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.repository.PetRepository;
import com.mentoring.yussuf.service.PetShopService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class PetShopServiceTest {

    private final PetRepository petRepository = mock(PetRepository.class);
    private final PetShopService petShopService = new PetShopService(petRepository);

    @Test
    public void shouldReturnPetsGivenPetsExistInTheList() {
        Pet existingPet = mock(Pet.class); // mock of existing pet
        when(petRepository.findById(1)).thenReturn(Optional.of(existingPet)); // repo called should return existingPet
        Optional<Pet> pet = petShopService.getPetById(1);
        assertThat(pet).isEqualTo(Optional.of(existingPet));
    }

    @Test
    public void shouldUpdatePetGivenChangeInValues() {
        Pet existingPet = Pet.builder().id(1).age(5).price(300).name("Fluffy").description("Good kitty").gender("F").species("Cat").build();
        when(petRepository.findById(1)).thenReturn(Optional.of(existingPet));
        petShopService.updatePet(existingPet.getId(), 10, 150, true);

        assertThat(existingPet.getAge()).isEqualTo(10);
        assertThat(existingPet.getPrice()).isEqualTo(150);
        assertThat(existingPet.isSold()).isTrue();
        verify(petRepository, times(1)).save(existingPet);
    }

    @Test
    public void shouldReturnAnErrorIfNoPetExistsWithTheGivenId() {
        assertThatThrownBy(() -> petShopService.updatePet(10, 10, 10, false))
                .hasMessage("Pet with id 10 does not exist");
    }

    @Test
    public void shouldReturnCorrectPetsMatchingSpeciesFilter() {

        var fluffyCat = mock(Pet.class);
        var mittensCat = mock(Pet.class);
        when(petRepository.findPetsBy("Cat", true)).thenReturn(List.of(fluffyCat, mittensCat));
        var catsFilter = petShopService.getPetsBy("Cat", true);
        assertThat(catsFilter).contains(fluffyCat, mittensCat);
    }

    @Test
    public void shouldDeleteCorrectPetGivenPetId() {
        petShopService.deletePet(1);
        verify(petRepository, times(1)).delete(1);
    }

    @Test
    public void shouldUpdatePetGivenChangeInSpecies() {
        Pet existingPet = Pet.builder().id(1).age(5).price(300).name("Fluffy").description("Good kitty").gender("F").species("Cat").build();
        when(petRepository.findById(1)).thenReturn(Optional.of(existingPet));
        petShopService.updateSpecies(existingPet.getId(), "Lion");

        assertThat(existingPet.getSpecies()).isEqualTo("Lion");
    }
}
