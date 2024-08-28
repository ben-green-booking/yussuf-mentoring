package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;

import java.util.*;

public class PetRepository {

    private final Map<Integer, Pet> pets;

    public PetRepository(Map<Integer, Pet> pets) {
        this.pets = pets;
    }

    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(pets.size() + 1);
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    public Optional<Pet> findById(int id) {
        //TODO Populate
        return Optional.ofNullable(pets.get(id));
    }

    public List<Pet> findPetsBy(String species, boolean availableOnly) {
        //TODO Populate
        return pets.values().stream()
                .filter(p -> species == null || p.getSpecies().equals(species))
                .filter(p -> !availableOnly || !p.isSold()).toList();
    }

    public void delete(int id) {
        //TODO Populate
        pets.remove(id);
    }

    public void update(int id, Integer age, Integer price, Boolean sold) {
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

    public void updateSpecies(Integer id, String species) {
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
