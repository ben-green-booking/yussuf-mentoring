package com.mentoring.yussuf.v1.dto;

import lombok.Builder;

@Builder
public record UpdatePetDTO(Integer id, Integer age, Integer price, Boolean sold) {

}
