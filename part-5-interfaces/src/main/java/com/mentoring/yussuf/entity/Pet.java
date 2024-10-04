package com.mentoring.yussuf.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Builder
@Data
public class Pet {

    Integer id;
    String name;
    String description;
    String species;
    String gender;
    int age;
    int price;
    boolean sold;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return age == pet.age && price == pet.price && sold == pet.sold && Objects.equals(name, pet.name) && Objects.equals(description, pet.description) && Objects.equals(species, pet.species) && Objects.equals(gender, pet.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, species, gender, age, price, sold);
    }
}
