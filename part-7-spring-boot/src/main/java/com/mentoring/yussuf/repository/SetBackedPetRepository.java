package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Profile(value = "set")
@Repository
public class SetBackedPetRepository extends AbstractCollectionBackedRepository {

    public SetBackedPetRepository(Set<Pet> pets) {
        super(pets);
    }

    @Override
    protected String getRepositoryType() {
        return "set";
    }
    // Method 'save()' is identical to its super method, remove redundant method
    // Super allows referencing the parent class or superclass of a subclass in Java.
}
