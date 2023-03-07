package com.greybear.snackmachine.domain;

import lombok.Getter;

@Getter
public class Snack extends AggregateRoot {

    private final String name;

    public Snack(String name) {
        this.name = name;
    }
}
