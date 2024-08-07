package com.mentoring.yussuf.v1.dto;

import lombok.Builder;

@Builder
public record CreatePetDTO(
        String name,
        String species,
        String description,
        String gender,
        int age,
        int price) {
}
