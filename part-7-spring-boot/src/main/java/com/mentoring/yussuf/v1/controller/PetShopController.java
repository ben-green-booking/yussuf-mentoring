package com.mentoring.yussuf.v1.controller;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.service.PetShopService;
import com.mentoring.yussuf.v1.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/pets")
public class PetShopController {

    private final PetShopService petShopService;

    public PetShopController(PetShopService petShopService) {
        this.petShopService = petShopService;
    }

    @PostMapping
    public int createPet(@RequestBody CreatePetDTO createPetDTO) {
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

    @PatchMapping("/{id}")
    public void updatePet(@RequestBody UpdatePetDTO updatePetDTO) {
        petShopService.updatePet(updatePetDTO.id(), updatePetDTO.age(), updatePetDTO.price(), updatePetDTO.sold());
        // Have to manually put id in on postman instead of taking from URL, URL or Request Body?
    }

    @DeleteMapping
    public void deletePet(@PathVariable int petId) {
        petShopService.deletePet(petId);
    }

    @GetMapping
    public List<GetPetDTO> getPetsBy(@RequestParam(required = false) String species, @RequestParam boolean availableOnly) {
        return petShopService.getPetsBy(species, availableOnly).stream().map(this::toGetPetDTO).toList();
    }

    @RequestMapping("/{id}")
    public Optional<GetPetDTO> getPetById(@PathVariable int id) {
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
