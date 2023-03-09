package com.greybear.snackmachine.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.greybear.snackmachine.domain.SnackPile.*;


@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Slot {

    @EqualsAndHashCode.Include
    private long id;
    @Setter
    private SnackPile snackPile;
    private final SnackMachine snackMachine;
    private final int position;

    public Slot(SnackMachine snackMachine, int position) {
        this.snackMachine = snackMachine;
        this.position = position;
        snackPile = EMPTY;
    }

    public Slot(long id, SnackPile snackPile, SnackMachine snackMachine, int position) {
        this.id = id;
        this.snackPile = snackPile;
        this.snackMachine = snackMachine;
        this.position = position;
    }
}
