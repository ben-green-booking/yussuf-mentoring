package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;

import java.util.*;

public class SetBackedPetRepository implements PetRepository {

    private final Set<Pet> pets;

    public SetBackedPetRepository(Set<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(pets.size() + 1);
        } else {
            delete(pet.getId());
        }
        pets.add(pet);
        return pet;
    }

    @Override
    public Optional<Pet> findById(int id) {
        return pets.stream().filter(pet -> pet.getId() == id).findFirst();
    }

    @Override
    public List<Pet> findPetsBy(String species, boolean availableOnly) {
        return pets.stream().filter(p -> species == null || p.getSpecies().equals(species))
                .filter(p -> !availableOnly || !p.isSold()).toList();
    }

    @Override
    public void delete(int id) {
        pets.removeIf(pet -> pet.getId() == id);
    }

    public Set<Pet> getAllPets() {
        System.out.printf(pets.toString());
        return pets;
    }
    // helper method not in interface?
}
