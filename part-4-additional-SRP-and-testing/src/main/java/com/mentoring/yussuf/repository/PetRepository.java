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
        return Optional.ofNullable(pets.get(id));
    }

    public List<Pet> findPetsBy(String species, boolean availableOnly) {
        return pets.values().stream()
                .filter(p -> species == null || p.getSpecies().equals(species))
                .filter(p -> !availableOnly || !p.isSold()).toList();
    }

    public void delete(int id) {
        pets.remove(id);
    }
}
