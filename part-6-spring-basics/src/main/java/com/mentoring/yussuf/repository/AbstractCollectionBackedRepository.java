package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;

import java.util.Collection;

public abstract class AbstractCollectionBackedRepository<T extends Collection<Pet>> implements PetRepository {

    protected final T pets;

    public AbstractCollectionBackedRepository(T pets) {
        this.pets = pets;
    }
}
