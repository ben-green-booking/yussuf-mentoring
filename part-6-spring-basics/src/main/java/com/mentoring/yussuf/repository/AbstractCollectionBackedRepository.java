package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;

import java.util.Collection;

public abstract class AbstractCollectionBackedRepository implements PetRepository {

    protected final Collection<Pet> pets;

    public AbstractCollectionBackedRepository(Collection<Pet> pets) {
        this.pets = pets;
    }
}
