package com.greybear.snackmachine.domain;

import lombok.Getter;
import lombok.Setter;



@Getter
public class Slot extends Entity{

    @Setter
    private SnackPile snackPile;
    private SnackMachine snackMachine;
    private int position;

    public Slot(SnackMachine snackMachine, int position) {
        this.snackMachine = snackMachine;
        this.position = position;
        snackPile = new SnackPile(null, 0, null);
    }
}
