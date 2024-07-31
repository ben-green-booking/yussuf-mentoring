package com.mentoring.yussuf.controller;

import com.mentoring.yussuf.dto.*;
import com.mentoring.yussuf.entity.Pet;

import java.util.*;

public class PetShopController {

    ArrayList<Pet> pets = new ArrayList();

    public int createPet(CreatePetDTO createPetDTO) {
        int id = pets.size() + 1;
        pets.add(Pet.builder().name(createPetDTO.name())
                .age(createPetDTO.age())
                .sold(false)
                .price(createPetDTO.price())
                .description(createPetDTO.description())
                .gender(createPetDTO.gender())
                .species(createPetDTO.species())
                .id(id).build()
        );
        if (createPetDTO.species() == null) {
            throw new RuntimeException("Species is a mandatory field for a new pet");
        }

        return id;
    }

    public void updatePet(UpdatePetDTO updatePetDTO) {
        for (Pet pet : pets) {
            if (pet.getId() == updatePetDTO.id()) {
                pet.setAge(updatePetDTO.age() == null ? pet.getAge() : updatePetDTO.age());
                pet.setPrice(updatePetDTO.price() == null ? pet.getPrice() : updatePetDTO.price());
                pet.setSold(updatePetDTO.sold() == null ? pet.isSold() : updatePetDTO.sold());
            }
        }
    }

    public void deletePet(int petId) {

    }

    public List<GetPetDTO> getPetsBy(String species, boolean availableOnly) {
        return List.of();
    }

    public Optional<GetPetDTO> getPetById(int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                GetPetDTO getPetDTO = GetPetDTO.builder().id(pet.getId())
                        .age(pet.getAge())
                        .description(pet.getDescription())
                        .price(pet.getPrice())
                        .name(pet.getName())
                        .species(pet.getSpecies())
                        .sold(pet.isSold())
                        .gender(pet.getGender()).build();
                return Optional.of(getPetDTO);
            }
        }
        return Optional.empty();
    }
}
