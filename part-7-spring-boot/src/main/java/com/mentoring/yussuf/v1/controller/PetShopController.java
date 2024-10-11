package com.mentoring.yussuf.v1.controller;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.service.PetShopService;
import com.mentoring.yussuf.v1.dto.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PetShopController {

    private final PetShopService petShopService;

    public PetShopController(PetShopService petShopService) {
        this.petShopService = petShopService;
    }

    public int createPet(CreatePetDTO createPetDTO) {
        if (createPetDTO.species() == null) {
            throw new RuntimeException("Species is a mandatory field for a new pet");
        }
        return petShopService.createPet(createPetDTO.age(),
                createPetDTO.price(),
                createPetDTO.name(),
                createPetDTO.description(),
                createPetDTO.gender(),
                createPetDTO.species()).getId();
    }

    public void updatePet(UpdatePetDTO updatePetDTO) {
        petShopService.updatePet(updatePetDTO.id(), updatePetDTO.age(), updatePetDTO.price(), updatePetDTO.sold());
    }

    public void deletePet(int petId) {
        petShopService.deletePet(petId);
    }

    public List<GetPetDTO> getPetsBy(String species, boolean availableOnly) {
        return petShopService.getPetsBy(species, availableOnly).stream().map(this::toGetPetDTO).toList();
    }

    public Optional<GetPetDTO> getPetById(int id) {
        return petShopService.getPetById(id).map(this::toGetPetDTO);
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
