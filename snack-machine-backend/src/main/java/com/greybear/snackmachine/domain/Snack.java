package com.greybear.snackmachine.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(exclude = "name")
@ToString
public class Snack {

    private long id;
    private final String name;

    public Snack(String name) {
        this.name = name;
    }
    public Snack(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
