package com.mentoring.yussuf.v2.dto;

import lombok.Builder;

/**
 * This pet supplier is only interested in updating the species of a pet!
 */
@Builder
public record UpdatePetDTO(Integer id, String species) {

}
