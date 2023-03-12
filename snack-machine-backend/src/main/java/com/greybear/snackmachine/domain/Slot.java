package com.greybear.snackmachine.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Embedded;

import static com.greybear.snackmachine.domain.SnackPile.EMPTY;


@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString
public class Slot {

    @EqualsAndHashCode.Include
    @Id
    private long id;

    @Setter
    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY)
    private SnackPile snackPile;

    @ToString.Exclude
    @Transient
    private SnackMachine snackMachine;
    private int position;

    public Slot(SnackMachine snackMachine, int position) {
        this.snackMachine = snackMachine;
        this.position = position;
        snackPile = EMPTY;
    }

    @PersistenceCreator
    public Slot(long id, SnackPile snackPile, int position){
        this.id = id;
        this.snackPile = snackPile;
        this.position = position;
    }
}
