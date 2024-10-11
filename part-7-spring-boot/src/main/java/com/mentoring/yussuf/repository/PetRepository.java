package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    Pet save(Pet pet);

    Optional<Pet> findById(int id);

    List<Pet> findPetsBy(String species, boolean availableOnly);

    void delete(int id);

    void deleteAll();
}
