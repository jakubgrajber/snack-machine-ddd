package com.greybear.snackmachine.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = "name")
public class Snack {

    private long id;
    private final String name;

    public Snack(String name) {
        this.name = name;
    }
}
