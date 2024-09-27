package com.mentoring.yussuf.v2.controller;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.service.PetShopService;
import com.mentoring.yussuf.v2.dto.*;

import java.util.List;
import java.util.Optional;

public class V2PetShopController {

    private final PetShopService petShopService;

    public V2PetShopController(PetShopService petShopService) {
        this.petShopService = petShopService;
    }

    public int createPet(CreatePetDTO createPetDTO) {
        String[] petDetails = createPetDTO.petInformation().split("\\|");
        if (petDetails[0].isEmpty()) {
            throw new RuntimeException("Species is a mandatory field for a new pet");
        }
        return petShopService.createPet(Integer.parseInt(petDetails[3]),
                Integer.parseInt(petDetails[4]),
                petDetails[1],
                petDetails[5],
                petDetails[2],
                petDetails[0]
        ).getId();
    }

    public void updatePet(UpdatePetDTO updatePetDTO) {
        petShopService.updateSpecies(updatePetDTO.id(), updatePetDTO.species());
    }

    public void deletePet(int petId) {
        petShopService.deletePet(petId);
    }

    public List<GetPetDTO> getPetsBy(String species, boolean availableOnly) {
        return petShopService.getPetsBy(species, availableOnly).stream().map(this::toGetPetDTO).toList();
    }

    public Optional<GetPetDTO> getPetById(int id) {
        return petShopService.getPetById(id).map(this::toGetPetDTO);
        // ofNullable = optional empty if not pet present or id is present
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