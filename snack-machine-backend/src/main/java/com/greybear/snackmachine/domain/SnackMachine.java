package com.greybear.snackmachine.domain;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

import static com.greybear.snackmachine.domain.Money.*;

@Getter
public class SnackMachine extends AggregateRoot {

    private static final List<Money> COINS_AND_NOTES = List.of(CENT, TEN_CENT, QUARTER, DOLLAR, FIVE_DOLLAR, TWENTY_DOLLAR);
    private Money moneyInside;
    private Money moneyInTransaction;
    protected List<Slot> slots;

    public SnackMachine() {
        moneyInside = NONE;
        moneyInTransaction = NONE;
        slots = new LinkedList<>(List.of(
           new Slot(this, 1),
           new Slot(this, 2),
           new Slot(this, 3)
        ));
    }

    public void insertMoney(Money money) {

        if (!COINS_AND_NOTES.contains(money))
            throw new IllegalArgumentException("Only one coin or note can be inserted at a time.");

        moneyInTransaction = moneyInTransaction.add(money);
    }

    public void returnMoney() {
        moneyInTransaction = NONE;
    }

    public void buySnack(int position) {
        Slot slot = getSlot(position);
        slot.setSnackPile(slot.getSnackPile().subtractOne());
        moneyInside = moneyInside.add(moneyInTransaction);
        moneyInTransaction = NONE;
    }

    public void loadSnacks(int position, SnackPile snackPile) {
        getSlot(position).setSnackPile(snackPile);
    }

    public SnackPile getSnackPile(int position) {
        return getSlot(position).getSnackPile();
    }

    private Slot getSlot(int position) {
        return slots.stream().filter(slot -> slot.getPosition() == position)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Wrong slot position number."));
    }
}
