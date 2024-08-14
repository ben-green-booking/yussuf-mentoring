package com.mentoring.yussuf.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Pet {

    int id;
    String name;
    String description;
    String species;
    String gender;
    int age;
    int price;
    boolean sold;
}