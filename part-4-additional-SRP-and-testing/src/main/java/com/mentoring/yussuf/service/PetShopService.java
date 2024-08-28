package com.mentoring.yussuf.service;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.repository.PetRepository;

import java.util.List;
import java.util.Optional;

public class PetShopService {

    private final PetRepository petRepository;

    public PetShopService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet createPet(int age, int price, String name, String description, String gender, String species) {
        var pet = Pet.builder().name(name)
                .age(age)
                .sold(false)
                .price(price)
                .description(description)
                .gender(gender)
                .species(species)
                .build();
        return petRepository.save(pet);
    }

    public void updatePet(int id, Integer age, Integer price, Boolean sold) {
        //TODO Refactor - Consider what happens here. We retrieve a pet, update it, and then save it again. Which repository methods do you think we should use for that
        petRepository.update(id, age, price, sold);
    }

    public List<Pet> getPetsBy(String species, boolean availableOnly) {
        //TODO Refactor - All of this is repository interaction. There's basically no service layer logic here. Can we move all of this to a method in the repository?
        return petRepository.findPetsBy(species, availableOnly);
    }

    public void deletePet(int id) {
        //TODO Refactor - All of this is repository interaction. There's basically no service layer logic here. Can we move all of this to a method in the repository?
        petRepository.delete(id);
    }

    public Optional<Pet> getPetById(int id) {
        //TODO Refactor - All of this is repository interaction. There's basically no service layer logic here. Can we move all of this to a method in the repository?
        return petRepository.findById(id);
    }

    public void updateSpecies(Integer id, String species) {
        petRepository.updateSpecies(id, species);
    }
}

