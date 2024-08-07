package com.mentoring.yussuf.v1.dto;

import lombok.Builder;

@Builder
public record GetPetDTO(
        int id,
        String name,
        String description,
        String species,
        String gender,
        int age,
        int price,
        boolean sold) {}
