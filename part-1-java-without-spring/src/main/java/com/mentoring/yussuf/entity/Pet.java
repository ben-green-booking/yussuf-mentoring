package com.mentoring.yussuf.entity;

import lombok.Builder;

@Builder
public record Pet(
        int id,
        String name,
        String description,
        String species,
        String gender,
        int age,
        int price,
        boolean sold) {}
