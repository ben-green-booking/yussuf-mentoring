package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;

import java.util.List;
import java.util.Optional;

public class ListBackedPetRepository extends AbstractCollectionBackedRepository<List<Pet>> {

    public ListBackedPetRepository(List<Pet> pets) {
        super(pets);
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

    @Override
    public void deleteAll() {
        pets.removeIf(pet -> true);
    }
}
