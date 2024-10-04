package com.mentoring.yussuf;

import com.mentoring.yussuf.repository.PetRepository;
import com.mentoring.yussuf.service.PetShopService;
import com.mentoring.yussuf.v1.controller.PetShopController;
import com.mentoring.yussuf.v2.controller.V2PetShopController;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class PetShopConfiguration {

    private final PetRepository petRepository;

    // class to define spring beans
    @Bean
    public V2PetShopController v2PetShopController() {
        return new V2PetShopController(petShopService());
    }

    @Bean
    public PetShopController petShopController() {
        return new PetShopController(petShopService());
    }

    @Bean
//    @Scope(value = "prototype") - prototype creates new instance of repo and map
    public PetShopService petShopService() {
        return new PetShopService(petRepository);
    }
}
