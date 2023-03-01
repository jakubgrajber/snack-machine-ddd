package com.greybear.snackmachine.domain;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

import static com.greybear.snackmachine.domain.Money.*;

@Accessors(fluent = true)
@Getter
public class SnackMachine extends Entity {

    private static final List<Money> COINS_AND_NOTES = List.of(CENT, TEN_CENT, QUARTER, DOLLAR, FIVE_DOLLAR, TWENTY_DOLLAR);
    private Money moneyInside = NONE;
    private Money moneyInTransaction = NONE;


    public void insertMoney(Money money) {

        if (!COINS_AND_NOTES.contains(money))
            throw new IllegalArgumentException("Only one coin or note can be inserted at a time.");

        moneyInTransaction = moneyInTransaction.add(money);
    }

    public void returnMoney() {
        moneyInTransaction = NONE;
    }

    public void buySnack() {
        moneyInside = moneyInside.add(moneyInTransaction);
        moneyInTransaction = NONE;
    }

}
