package com.greybear.snackmachine.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static com.greybear.snackmachine.domain.Money.*;
import static com.greybear.snackmachine.domain.Snack.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class SnackMachineTest {

    private SnackMachine snackMachine;

    @BeforeEach
    void setUp() {
        snackMachine = new SnackMachine();
    }

    @Test
    void givenSnackMachineWithMoneyInTransaction_when_then() {

        // GIVEN
        snackMachine.insertMoney(DOLLAR);

        // WHEN
        snackMachine.returnMoney();

        // THEN
        assertThat(snackMachine.getMoneyInTransaction()).isZero();
    }

    @Test
    void givenSnackMachine_whenInsertsMoney_thenMoneyGoesToTransactionMoney() {

        // GIVEN
        BigDecimal insertedMoney = new BigDecimal("1.01");

        // WHEN
        snackMachine.insertMoney(CENT);
        snackMachine.insertMoney(DOLLAR);

        // THEN
        assertThat(snackMachine.getMoneyInTransaction()).isEqualTo(insertedMoney);
    }

    @Test
    void givenSnackMachine_whenInsertsMoreThanOneCoinOrNoteAtATime_thenThrowIllegalArgumentException() {

        // GIVEN
        Money twoCent = CENT.add(CENT);

        // WHEN - THEN
        assertThatThrownBy(() -> snackMachine.insertMoney(twoCent)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenSnackMachineWithSnacks_whenBuysASnack_thenTradesInsertedMoneyForASnack() {

        // GIVEN
        snackMachine.loadSnacks(1, new SnackPile(CHOCOLATE, 10, new BigDecimal("1")));
        snackMachine.insertMoney(DOLLAR);

        // WHEN
        snackMachine.buySnack(1);

        // THEN
        assertThat(snackMachine.getMoneyInTransaction()).isZero();
        assertThat(snackMachine.getMoneyInside()).isEqualTo(DOLLAR);
        assertThat(snackMachine.getSnackPile(1).getQuantity()).isEqualTo(9);
    }

    @Test
    void givenSnackMachineWithEmptySlots_whenMakesAPurchase_thenThrowsIllegalStateException() {

        // WHEN - THEN
        assertThatThrownBy(() -> snackMachine.buySnack(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Insufficient quantity of goods.");
    }

    @Test
    void givenNotEnoughMoney_whenMakesAPurchase_thenThrowsIllegalStateException() {

        // GIVEN
        BigDecimal price = new BigDecimal("2.00");
        SnackPile snackPile = new SnackPile(CHOCOLATE, 1, price);
        snackMachine.loadSnacks(1, snackPile);

        snackMachine.insertMoney(DOLLAR);

        // WHEN - THEN
        assertThatThrownBy(() -> snackMachine.buySnack(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Not enough money.");
    }

    @Test
    void givenMoneyInsideMachine_whenReturnsChange_thenReturnsMoneyWithHighestDenominationFirst() {

        // GIVEN
        snackMachine.loadMoney(DOLLAR);
        snackMachine.insertMoney(QUARTER);
        snackMachine.insertMoney(QUARTER);
        snackMachine.insertMoney(QUARTER);
        snackMachine.insertMoney(QUARTER);

        // WHEN
        snackMachine.returnMoney();

        // THEN
        assertThat(snackMachine.getMoneyInside().getQuarterCount()).isEqualTo(4);
        assertThat(snackMachine.getMoneyInside().getOneDollarCount()).isZero();
    }

    @Test
    void givenMoneyInsideMachine_whenMakesAPurchase_thenReturnsAChange() {

        // GIVEN
        BigDecimal price = new BigDecimal("0.50");
        SnackPile snackPile = new SnackPile(CHOCOLATE, 1, price);
        snackMachine.loadSnacks(1, snackPile);
        snackMachine.loadMoney(TEN_CENT.multiply(10));

        // WHEN
        snackMachine.insertMoney(DOLLAR);
        snackMachine.buySnack(1);

        // THEN
        assertThat(snackMachine.getMoneyInside().getAmount()).isEqualTo(new BigDecimal("1.50"));
        assertThat(snackMachine.getMoneyInTransaction()).isZero();
    }

    @Test
    void givenNotEnoughMoneyInsideMachineForAChange_whenMakesAPurchase_thenThrowsIllegalStateException() {

        // GIVEN
        BigDecimal price = new BigDecimal("0.50");
        SnackPile snackPile = new SnackPile(CHOCOLATE, 1, price);
        snackMachine.loadSnacks(1, snackPile);

        // WHEN - THEN
        snackMachine.insertMoney(DOLLAR);
        assertThatThrownBy(() -> snackMachine.buySnack(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Not enough money for a change.");
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
           10.99 | false
           5.00  | true
           3.75  | true""")
    void givenCertainAmountOfMoneyInsertedIntoTheMachine_whenChecksIfCanMakeAPurchase_thanReturnBooleanAccordingToTheGivenMoney
            (String money, boolean expectedPurchaseDecision) {

        // GIVEN
        BigDecimal price = new BigDecimal(money);
        SnackPile snackPile = new SnackPile(CHOCOLATE, 1, price);
        snackMachine.loadSnacks(1, snackPile);

        // WHEN
        snackMachine.insertMoney(FIVE_DOLLAR);
        boolean canBuyASnack = snackMachine.canMakePurchase(1);

        // THEN
        assertThat(canBuyASnack).isEqualTo(expectedPurchaseDecision);
    }
}