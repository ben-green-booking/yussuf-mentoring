package com.mentoring.yussuf.dto;

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
