package com.mentoring.yussuf.service;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
        var pet = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet with id " + id + " does not exist"));
        if (age != null) pet.setAge(age);
        if (price != null) pet.setPrice(price);
        if (sold != null) pet.setSold(sold);
        petRepository.save(pet);
    }

    public List<Pet> getPetsBy(String species, boolean availableOnly) {
        return petRepository.findPetsBy(species, availableOnly);
    }

    public void deletePet(int id) {
        petRepository.delete(id);
    }

    public Optional<Pet> getPetById(int id) {
        return petRepository.findById(id);
    }

    public void updateSpecies(Integer id, String species) {
        var pet = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet with id " + id + " does not exist"));
        if (species != null) pet.setSpecies(species);
        petRepository.save(pet);
    }
}

