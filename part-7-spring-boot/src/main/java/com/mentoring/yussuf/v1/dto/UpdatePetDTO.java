package com.mentoring.yussuf.v1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record UpdatePetDTO(Integer id, @Min(0) @Max(50) Integer age, @Min(1) Integer price, Boolean sold) {

}
