package com.mentoring.yussuf.repository;

import com.mentoring.yussuf.entity.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile(value = "list")
@Repository
public class ListBackedPetRepository extends AbstractCollectionBackedRepository {

    public ListBackedPetRepository(List<Pet> pets) {
        super(pets);
    }

    @Override
    protected String getRepositoryType() {
        return "list";
    }
}
