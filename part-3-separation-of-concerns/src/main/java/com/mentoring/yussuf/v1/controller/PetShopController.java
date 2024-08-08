package com.mentoring.yussuf.v1.controller;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.v1.dto.*;

import java.util.*;

public class PetShopController {

    private final Map<Integer, Pet> pets;

    public PetShopController(Map<Integer, Pet> pets) {
        this.pets = pets;
    }

    public int createPet(CreatePetDTO createPetDTO) {
        if (createPetDTO.species() == null) {
            throw new RuntimeException("Species is a mandatory field for a new pet");
        }
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

        return id;
    }

    public void updatePet(UpdatePetDTO updatePetDTO) {
        var pet = pets.stream()
                .filter(p -> p.getId() == updatePetDTO.id())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pet with id " + updatePetDTO.id() + " does not exist"));

        pet.setAge(updatePetDTO.age() == null ? pet.getAge() : updatePetDTO.age());
        pet.setPrice(updatePetDTO.price() == null ? pet.getPrice() : updatePetDTO.price());
        pet.setSold(updatePetDTO.sold() == null ? pet.isSold() : updatePetDTO.sold());
    }

    public void deletePet(int petId) {
        pets.stream().filter(pet -> pet.getId() == petId).findFirst().ifPresent(pets::remove);
    }

    public List<GetPetDTO> getPetsBy(String species, boolean availableOnly) {
        return pets.stream().filter(p -> species == null || p.getSpecies().equals(species))
                .filter(p -> !availableOnly || !p.isSold()).map(this::toGetPetDTO).toList();
    }

    public Optional<GetPetDTO> getPetById(int id) {
        return pets.stream().filter(p -> p.getId() == id).findFirst().map(this::toGetPetDTO);
    }

    private GetPetDTO toGetPetDTO(Pet pet) {
        GetPetDTO getPetDTO = GetPetDTO.builder().id(pet.getId())
                .age(pet.getAge())
                .description(pet.getDescription())
                .price(pet.getPrice())
                .name(pet.getName())
                .species(pet.getSpecies())
                .sold(pet.isSold())
                .gender(pet.getGender()).build();
        return getPetDTO;
    }
}
