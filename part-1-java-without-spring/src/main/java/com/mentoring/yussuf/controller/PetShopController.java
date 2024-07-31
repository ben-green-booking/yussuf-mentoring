package com.mentoring.yussuf.controller;

import com.mentoring.yussuf.dto.*;
import com.mentoring.yussuf.entity.Pet;

import java.util.*;

public class PetShopController {

    ArrayList<Pet> pets = new ArrayList();

    public int createPet(CreatePetDTO createPetDTO) {
        int id = pets.size() + 1;
        pets.add(Pet.builder().name(createPetDTO.name()).age(createPetDTO.age()).sold(false).price(createPetDTO.price()).description(createPetDTO.description()).gender(createPetDTO.gender()).species(createPetDTO.species()).id(id).build()
        );
        return id;
    }

    public void updatePet(UpdatePetDTO updatePetDTO) {

    }

    public void deletePet(int petId) {

    }

    public List<GetPetDTO> getPetsBy(String species, boolean availableOnly) {
        return null;
    }

    public Optional<GetPetDTO> getPetById(int id) {
        return Optional.empty();
    }
}
