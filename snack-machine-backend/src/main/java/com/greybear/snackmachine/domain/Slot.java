package com.greybear.snackmachine.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
        snackPile = new SnackPile(null, 0, null);
    }
}
