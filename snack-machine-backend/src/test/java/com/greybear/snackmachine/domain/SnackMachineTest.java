package com.greybear.snackmachine.domain;

import org.junit.jupiter.api.Test;

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
        assertThat(snackMachine.getMoneyInTransaction().getAmount()).isZero();
    }

    @Test
    void givenSnackMachine_whenInsertsMoney_thenMoneyGoesToTransactionMoney() {

        // GIVEN
        SnackMachine snackMachine = new SnackMachine();

        // WHEN
        snackMachine.insertMoney(CENT);
        snackMachine.insertMoney(DOLLAR);

        // THEN
        assertThat(snackMachine.getMoneyInTransaction()).isEqualTo(DOLLAR.add(CENT));
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
        assertThat(snackMachine.getMoneyInTransaction()).isEqualTo(NONE);
        assertThat(snackMachine.getMoneyInside()).isEqualTo(DOLLAR.multiply(2));
    }
}