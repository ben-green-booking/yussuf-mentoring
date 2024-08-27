package com.mentoring.yussuf.v2.dto;

import lombok.Builder;

/**
 * Example: Cat|Max|M|5|300|A very good boy
 *
 */
@Builder
public record CreatePetDTO(String petInformation) {
}

