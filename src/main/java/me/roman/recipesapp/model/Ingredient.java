package me.roman.recipesapp.model;

import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String name;
    private int count;
    private String size;

    @Override
    public String toString() {
        return name + " - " + count + size;
    }

}
