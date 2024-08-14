package com.mentoring.yussuf.service;

import com.mentoring.yussuf.entity.Pet;

import java.util.*;

public class PetShopService {

    private final Map<Integer, Pet> pets;

    public PetShopService(Map<Integer, Pet> pets) {
        this.pets = pets;
    }

    public Pet createPet(int age, int price, String name, String description, String gender, String species) {
        int id = pets.size() + 1;
        var pet = Pet.builder().name(name)
                .age(age)
                .sold(false)
                .price(price)
                .description(description)
                .gender(gender)
                .species(species)
                .id(id).build();
        pets.put(id, pet);
        return pet;
    }

    public void updatePet(int id, Integer age, Integer price, Boolean sold) {
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
        return pets.values().stream()
                .filter(p -> species == null || p.getSpecies().equals(species))
                .filter(p -> !availableOnly || !p.isSold()).toList();
    }

    public void deletePet(int id) {
        pets.remove(id);
    }

    public Optional<Pet> getPetById(int id) {
        return Optional.ofNullable(pets.get(id));
    }
}

