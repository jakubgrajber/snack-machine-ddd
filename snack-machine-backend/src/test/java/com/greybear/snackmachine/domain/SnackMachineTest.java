package com.greybear.snackmachine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.stream.IntStream;

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
        int initialQuantity = 10;
        BigDecimal price = new BigDecimal("1.00");
        snackMachine.loadSnacks(1, new SnackPile(CHOCOLATE, initialQuantity, price));

        // WHEN
        snackMachine.insertMoney(DOLLAR);
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
    void givenCertainAmountOfMoneyInsertedIntoTheMachine_whenChecksIfCanMakeAPurchase_thanReturnsBooleanAccordingToTheGivenMoney
            (String money, boolean expectedPurchaseDecision) {

        // GIVEN
        BigDecimal price = new BigDecimal(money);
        SnackPile snackPile = new SnackPile(CHOCOLATE, 1, price);
        snackMachine.loadSnacks(1, snackPile);
        snackMachine.loadMoney(new Money(10, 10,10,10,10,10));

        // WHEN
        snackMachine.insertMoney(FIVE_DOLLAR);
        boolean canBuyASnack = snackMachine.isEnoughMoneyToBuy(1);

        // THEN
        assertThat(canBuyASnack).isEqualTo(expectedPurchaseDecision);
    }

    @Test
    void givenNotEnoughMoneyInMachineToReturnChange_whenChecksIfCanMakeAPurchase_thenReturnsFalse() {

        // GIVEN
        BigDecimal price = new BigDecimal("2.99");
        SnackPile snackPile = new SnackPile(SODA, 3, price);
        snackMachine.loadSnacks(1, snackPile);

        Money moneyInsideMachine = new Money(0, 1, 1, 1,1,1);
        snackMachine.loadMoney(moneyInsideMachine);

        // WHEN
        IntStream.range(0,3).forEach(x -> snackMachine.insertMoney(DOLLAR));
        boolean canBuyASnack = snackMachine.isEnoughMoneyInsideForAChange(1);

        // THEN
        assertThat(canBuyASnack).isFalse();
    }

    @Test
    void givenNotSufficientAmountOfGoods_whenChecksIfCanMakeAPurchase_thenReturnsFalse() {

        // GIVEN
        BigDecimal price = new BigDecimal("12.12");
        SnackPile snackPile = new SnackPile(GUM, 0, price);
        snackMachine.loadSnacks(1, snackPile);

        // WHEN
        boolean canBuyASnack = snackMachine.isSnackAvailable(1);

        // THEN
        assertThat(canBuyASnack).isFalse();
    }
}