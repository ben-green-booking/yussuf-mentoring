package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Profile(value = "map")
@Repository
public class MapBackedPetRepository implements PetRepository {

    private Map<Integer, Pet> pets;

    public MapBackedPetRepository(Map<Integer, Pet> pets) {
        this.pets = pets;
    }

    @Override // coming from interface
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(pets.size() + 1);
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public Optional<Pet> findById(int id) {
        return Optional.ofNullable(pets.get(id));
    }

    @Override
    public List<Pet> findPetsBy(String species, boolean availableOnly) {
        return pets.values().stream()
                .filter(p -> species == null || p.getSpecies().equals(species))
                .filter(p -> !availableOnly || !p.isSold()).toList();
    }

    @Override
    public void delete(int id) {
        pets.remove(id);
    }

    @Override
    public void deleteAll() {
        pets = new HashMap<>();
    }
}
