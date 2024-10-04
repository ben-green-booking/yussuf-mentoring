package com.mentoring.yussuf;

import com.mentoring.yussuf.repository.ListBackedPetRepository;
import com.mentoring.yussuf.repository.PetRepository;
import org.springframework.context.annotation.*;

import java.util.ArrayList;

@Profile(value = "list")
@Configuration
public class ListRepositoryConfig {

    @Bean
    public PetRepository listRepository() {
        return new ListBackedPetRepository(new ArrayList<>());
    }
}
