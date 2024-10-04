package com.mentoring.yussuf;

import com.mentoring.yussuf.repository.MapBackedPetRepository;
import com.mentoring.yussuf.repository.PetRepository;
import org.springframework.context.annotation.*;

import java.util.HashMap;

@Profile(value = "map")
@Configuration
public class MapRepositoryConfig {

    @Bean
    public PetRepository mapRepository() {
        return new MapBackedPetRepository(new HashMap<>());
    }
}
