package com.greybear.snackmachine.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnackMachine extends Entity{

    private Money moneyInside;
    private Money moneyInTransaction;

    public void insertMoney(Money money) {
        moneyInTransaction = moneyInTransaction.add(money);
    }

    public void returnMoney() {

//        moneyInTransaction = 0;
    }

    public void buySnack() {
        moneyInside = moneyInside.add(moneyInTransaction);

//        moneyInTransaction = 0;
    }
}
