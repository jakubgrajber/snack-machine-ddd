package com.greybear.snackmachine.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(exclude = "name")
@ToString
public class Snack {

    public static final Snack NONE = new Snack(0, "None");
    public static final Snack CHOCOLATE = new Snack(1, "Chocolate");
    public static final Snack SODA = new Snack(2, "Soda");
    public static final Snack GUM = new Snack(3, "Gum");
    private final long id;
    private final String name;

    public Snack(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
