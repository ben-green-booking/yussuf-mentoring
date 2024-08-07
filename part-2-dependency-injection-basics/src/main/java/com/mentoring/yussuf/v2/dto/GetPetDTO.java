package com.mentoring.yussuf.v2.dto;

import lombok.Builder;

/**
 * Example: 1|Cat|Max|M|5|300|A very good boy
 */
@Builder
public record GetPetDTO(
        String petInfo) {}
