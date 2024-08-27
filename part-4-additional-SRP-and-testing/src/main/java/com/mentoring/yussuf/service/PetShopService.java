package com.mentoring.yussuf.service;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.repository.PetRepository;

import java.util.*;

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
        pets.entrySet().stream()
                .filter(petEntry -> Objects.equals(petEntry.getKey(), id))
                .findFirst()
                .ifPresentOrElse(entry -> {
                    Pet pet = entry.getValue();
                    if (age != null) pet.setAge(age);
                    if (price != null) pet.setPrice(price);
                    if (sold != null) pet.setSold(sold);
                }, () -> {
                    throw new RuntimeException("Pet with id " + id + " does not exist");
                });
    }


    public List<Pet> getPetsBy(String species, boolean availableOnly) {
        //TODO Refactor - All of this is repository interaction. There's basically no service layer logic here. Can we move all of this to a method in the repository?
        return pets.values().stream()
                .filter(p -> species == null || p.getSpecies().equals(species))
                .filter(p -> !availableOnly || !p.isSold()).toList();
    }

    public void deletePet(int id) {
        //TODO Refactor - All of this is repository interaction. There's basically no service layer logic here. Can we move all of this to a method in the repository?
        pets.remove(id);
    }

    public Optional<Pet> getPetById(int id) {
        //TODO Refactor - All of this is repository interaction. There's basically no service layer logic here. Can we move all of this to a method in the repository?
        return Optional.ofNullable(pets.get(id));
    }

    public void updateSpecies(Integer id, String species) {
        //TODO Refactor - Consider what happens here. We retrieve a pet, update it, and then save it again. Which repository methods do you think we should use for that
        pets.entrySet().stream()
                .filter(petEntry -> Objects.equals(petEntry.getKey(), id))
                .findFirst()
                .ifPresentOrElse(entry -> {
                    Pet pet = entry.getValue();
                    if (species != null) pet.setSpecies(species);
                }, () -> {
                    throw new RuntimeException("Pet with id " + id + " does not exist");
                });
    }
}

