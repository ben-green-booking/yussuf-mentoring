package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;

import java.util.List;

public class ListBackedPetRepository extends AbstractCollectionBackedRepository {

    public ListBackedPetRepository(List<Pet> pets) {
        super(pets);
    }
}
