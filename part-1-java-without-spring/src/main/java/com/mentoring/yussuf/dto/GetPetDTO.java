package com.mentoring.yussuf.dto;

import lombok.Builder;

@Builder
public record GetPetDTO(
        String name,
        String description,
        String species,
        String gender,
        int age,
        int price,
        boolean sold) {}
