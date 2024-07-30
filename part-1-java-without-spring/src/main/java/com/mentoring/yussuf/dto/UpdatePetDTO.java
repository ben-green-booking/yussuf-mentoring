package com.mentoring.yussuf.dto;

import lombok.Builder;

@Builder
public record UpdatePetDTO(int id, int age, int price, boolean sold) {
}
