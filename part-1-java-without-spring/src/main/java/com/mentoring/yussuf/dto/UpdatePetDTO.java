package com.mentoring.yussuf.dto;

import lombok.Builder;

@Builder
public record UpdatePetDTO(Integer id, Integer age, Integer price, Boolean sold) {

}
