package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public abstract class AbstractCollectionBackedRepository implements PetRepository {

    protected final Collection<Pet> pets;

    public AbstractCollectionBackedRepository(Collection<Pet> pets) {
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
        log.info("Loading pet with id from {}", getRepositoryType());
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

    @Override
    public void deleteAll() {
        pets.removeIf(pet -> true);
    }

    protected abstract String getRepositoryType();
}
