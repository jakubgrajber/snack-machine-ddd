package com.greybear.snackmachine.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.greybear.snackmachine.domain.Money.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class SnackMachineTest {

    @Test
    void givenSnackMachineWithMoneyInTransaction_when_then() {

        // GIVEN
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.insertMoney(DOLLAR);

        // WHEN
        snackMachine.returnMoney();

        // THEN
        assertThat(snackMachine.moneyInTransaction().amount()).isZero();
    }

    @Test
    void givenSnackMachine_whenInsertsMoney_thenMoneyGoesToTransactionMoney() {

        // GIVEN
        SnackMachine snackMachine = new SnackMachine();

        // WHEN
        snackMachine.insertMoney(CENT);
        snackMachine.insertMoney(DOLLAR);

        // THEN
        assertThat(snackMachine.moneyInTransaction().amount()).isEqualTo(BigDecimal.valueOf(1.01));
    }

    @Test
    void givenSnackMachine_whenInsertsMoreThanOneCoinOrNoteAtATime_thenThrowIllegalArgumentException() {

        // GIVEN
        SnackMachine snackMachine = new SnackMachine();
        Money twoCent = CENT.add(CENT);

        // WHEN - THEN
        assertThatThrownBy(() -> snackMachine.insertMoney(twoCent)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenSnackMachineWithMoneyInTransaction_whenPurchases_thenMoneyInTransactionGoesToMoneyInside() {

        // GIVEN
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.insertMoney(DOLLAR);
        snackMachine.insertMoney(DOLLAR);

        // WHEN
        snackMachine.buySnack();

        // THEN
        assertThat(snackMachine.moneyInTransaction().amount().doubleValue()).isEqualTo(0);
        assertThat(snackMachine.moneyInside().amount().doubleValue()).isEqualTo(2.0);
    }
}