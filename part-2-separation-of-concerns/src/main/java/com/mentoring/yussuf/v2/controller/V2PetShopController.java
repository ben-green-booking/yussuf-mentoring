package com.mentoring.yussuf.v2.controller;

import com.mentoring.yussuf.entity.Pet;
import com.mentoring.yussuf.v2.dto.*;

import java.util.*;

public class V2PetShopController {

    ArrayList<Pet> pets = new ArrayList();

    public int createPet(CreatePetDTO createPetDTO) {
        String[] petDetails = createPetDTO.petInformation().split("\\|");
        // Cat|Max|M|5|300|A very good boy
        if (petDetails[0].isEmpty()) {
            throw new RuntimeException("Species is a mandatory field for a new pet");
        }
        int id = pets.size() + 1;
        pets.add(Pet.builder().name(petDetails[1])
                .age(Integer.parseInt(petDetails[3]))
                .sold(false)
                .price(Integer.parseInt(petDetails[4]))
                .description(petDetails[5])
                .gender(petDetails[2])
                .species(petDetails[0])
                .id(id).build());
        return id;
    }

    public void updatePet(UpdatePetDTO updatePetDTO) {
        var pet = pets.stream()
                .filter(p -> p.getId() == updatePetDTO.id())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pet with id " + updatePetDTO.id() + " does not exist"));
        pet.setSpecies(updatePetDTO.species());
    }

    public void deletePet(int petId) {
        pets.removeIf(pet -> pet.getId() == petId);
    }

    public List<GetPetDTO> getPetsBy(String species, boolean availableOnly) {
        return pets.stream().filter(p -> species == null || p.getSpecies().equals(species))
                .filter(p -> !availableOnly || !p.isSold()).map(this::toGetPetDTO).toList();
    }

    public Optional<GetPetDTO> getPetById(int id) {
        return pets.stream().filter(p -> p.getId() == id).findFirst().map(this::toGetPetDTO);
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
