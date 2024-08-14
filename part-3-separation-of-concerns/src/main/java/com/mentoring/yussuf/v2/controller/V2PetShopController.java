package com.mentoring.yussuf.v2.controller;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.v2.dto.*;

import java.util.*;

public class V2PetShopController {

    private final Map<Integer, Pet> pets;

    public V2PetShopController(Map<Integer, Pet> pets) {
        this.pets = pets;
    }

    public int createPet(CreatePetDTO createPetDTO) {

        String[] petDetails = createPetDTO.petInformation().split("\\|");

        if (petDetails[0].isEmpty()) {
            throw new RuntimeException("Species is a mandatory field for a new pet");
        }
        int id = pets.size() + 1;
        pets.put(id, Pet.builder().name(petDetails[1])
                .age(Integer.parseInt(petDetails[3]))
                .sold(false)
                .price(Integer.parseInt(petDetails[4]))
                .description(petDetails[5])
                .gender(petDetails[2])
                .species(petDetails[0]).id(id).build());
        return id;
    }

    public void updatePet(UpdatePetDTO updatePetDTO) {
        pets.entrySet().stream()
                .filter(p -> Objects.equals(p.getKey(), updatePetDTO.id())).
                findFirst().ifPresentOrElse(p -> p.getValue().setSpecies(updatePetDTO.species()), () -> {
                    throw new RuntimeException("Pet with id " + updatePetDTO.id() + " does not exist");
                });
    }

    public void deletePet(int petId) {
        pets.remove(petId);
    }

    public List<GetPetDTO> getPetsBy(String species, boolean availableOnly) {
        return pets.values().stream().filter(p -> species == null || p.getSpecies().equals(species))
                .filter(p -> !availableOnly || !p.isSold()).map(this::toGetPetDTO).toList();
    }

    public Optional<GetPetDTO> getPetById(int id) {
        return pets.values().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(this::toGetPetDTO);
    }

    private GetPetDTO toGetPetDTO(Pet pet) {
        GetPetDTO getPetDTO = GetPetDTO.builder()
                .petInfo(pet.getId()
                        + "|" + pet.getSpecies()
                        + "|" + pet.getName()
                        + "|" + pet.getGender()
                        + "|" + pet.getAge()
                        + "|" + pet.getPrice()
                        + "|" + pet.getDescription()).build();
        return getPetDTO;
    }
}
