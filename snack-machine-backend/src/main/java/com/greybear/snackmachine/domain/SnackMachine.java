package com.greybear.snackmachine.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static com.greybear.snackmachine.domain.Money.*;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SnackMachine {

    private static final List<Money> COINS_AND_NOTES = List.of(CENT, TEN_CENT, QUARTER, DOLLAR, FIVE_DOLLAR, TWENTY_DOLLAR);
    @EqualsAndHashCode.Include
    private long id;
    private Money moneyInside;
    private BigDecimal moneyInTransaction;
    protected List<Slot> slots;

    public SnackMachine() {
        moneyInside = NONE;
        moneyInTransaction = BigDecimal.ZERO;
        slots = new LinkedList<>(List.of(
           new Slot(this, 1),
           new Slot(this, 2),
           new Slot(this, 3)
        ));
    }

    public void insertMoney(Money money) {

        if (!COINS_AND_NOTES.contains(money))
            throw new IllegalArgumentException("Only one coin or note can be inserted at a time.");

        moneyInTransaction = moneyInTransaction.add(money.getAmount());
        moneyInside = moneyInside.add(money);
    }

    public void returnMoney() {
        Money moneyToReturn = moneyInside.allocate(moneyInTransaction);
        moneyInside = moneyInside.subtract(moneyToReturn);
        moneyInTransaction = BigDecimal.ZERO;
    }

    public void buySnack(int position) {
        Slot slot = getSlot(position);

        if (slot.getSnackPile().quantity() <= 0)
            throw new IllegalStateException("Insufficient quantity of goods.");

        if (slot.getSnackPile().price().compareTo(moneyInTransaction) > 0)
            throw new IllegalStateException("Not enough money.");

        slot.setSnackPile(slot.getSnackPile().subtractOne());

        Money change = moneyInside.allocate(moneyInTransaction.subtract(slot.getSnackPile().price()));

        if (change.getAmount().compareTo(moneyInTransaction.subtract(slot.getSnackPile().price())) < 0)
            throw new IllegalStateException("Not enough money for a change.");

        moneyInside = moneyInside.subtract(change);

        moneyInTransaction = BigDecimal.ZERO;
    }

    public void loadSnacks(int position, SnackPile snackPile) {
        getSlot(position).setSnackPile(snackPile);
    }
    public void loadMoney(Money money) {
        moneyInside = moneyInside.add(money);
    }

    public SnackPile getSnackPile(int position) {
        return getSlot(position).getSnackPile();
    }

    private Slot getSlot(int position) {
        return slots.stream().filter(slot -> slot.getPosition() == position)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Wrong slot position number."));
    }

}
