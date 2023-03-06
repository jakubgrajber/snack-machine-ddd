package com.greybear.snackmachine.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static com.greybear.snackmachine.domain.Money.*;
import static org.assertj.core.api.Assertions.*;

class MoneyTest {

    @Test
    void givenTwoInstancesOfMoney_whenAdd_thenProduceCorrectResult() {

        // GIVEN
        Money money1 = new Money(1, 2,3,4,5,6);
        Money money2 = new Money(1, 2,3,4,5,6);

        // WHEN
        Money result = money1.add(money2);

        // THEN
        assertThat(result.getOneCentCount()).isEqualTo(2);
        assertThat(result.getTenCentCount()).isEqualTo(4);
        assertThat(result.getQuarterCount()).isEqualTo(6);
        assertThat(result.getOneDollarCount()).isEqualTo(8);
        assertThat(result.getFiveDollarCount()).isEqualTo(10);
        assertThat(result.getTwentyDollarCount()).isEqualTo(12);
    }

    @Test
    void givenTwoInstancesOfMoneyWithTheSameValues_whenComparedUsingEquals_thenReturnTrue() {

        // GIVEN
        Money money1 = new Money(1,0,0,0,0,0);
        Money money2 = new Money(1,0,0,0,0,0);

        // WHEN
        boolean result = money1.equals(money2);

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    void givenTwoInstancesOfMoneyWithDifferentValues_whenComparesUsingEquals_thenReturnFalse() {

        // GIVEN
        Money money1 = new Money(1,0,0,0,0,0);
        Money money2 = new Money(2,0,0,0,0,0);

        // WHEN
        boolean result = money1.equals(money2);

        // THEN
        assertThat(result).isFalse();

    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            -1 | 0 | 0 | 0 | 0 | 0
             0 |-1 | 0 | 0 | 0 | 0
             0 | 0 |-1 | 0 | 0 | 0
             0 | 0 | 0 |-1 | 0 | 0
             0 | 0 | 0 | 0 |-1 | 0
             0 | 0 | 0 | 0 | 0 |-1""")
    void givenEdgeCases_whenCreatesNewInstance_thenThrowIllegalArgumentException(int oneCent, int tenCent, int quarter, int oneDollar, int fiveDollar, int twentyDollar) {

        // WHEN - THEN
        assertThatThrownBy(() -> new Money(oneCent, tenCent, quarter, oneDollar, fiveDollar, twentyDollar)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            0 | 0 | 0 | 0 | 0 | 0 | 0
            1 | 0 | 0 | 0 | 0 | 0 | 0.01
            1 | 2 | 0 | 0 | 0 | 0 | 0.21
            1 | 2 | 3 | 0 | 0 | 0 | 0.96
            1 | 2 | 3 | 4 | 0 | 0 | 4.96
            1 | 2 | 3 | 4 | 5 | 0 | 29.96
            1 | 2 | 3 | 4 | 5 | 6 | 149.96
            11| 0 | 0 | 0 | 0 | 0 | 0.11
           110| 0 | 0 | 0 |100| 0 | 501.1""")
    void givenEachMoneyValueItsAmount_whenCreatesNewInstance_thenCalculatesWholeAmountProperly(
            int oneCent, int tenCent, int quarter, int oneDollar, int fiveDollar, int twentyDollar, double expectedAmount) {

        // WHEN
        Money money = new Money(oneCent, tenCent, quarter, oneDollar, fiveDollar, twentyDollar);

        // THEN
        assertThat(money.getAmount().doubleValue()).isEqualTo(expectedAmount);
    }

    @Test
    void givenTwoMoneyObjects_whenSubtractsThem_thenReturnNewObject() {

        // GIVEN
        Money moneyOne = new Money(10, 10, 10, 10, 10,10);
        Money moneyTwo = new Money(1, 2, 3, 4, 5,6);

        // WHEN
        Money result = moneyOne.subtract(moneyTwo);

        // THEN
        assertThat(result.getOneCentCount()).isEqualTo(9);
        assertThat(result.getTenCentCount()).isEqualTo(8);
        assertThat(result.getQuarterCount()).isEqualTo(7);
        assertThat(result.getOneDollarCount()).isEqualTo(6);
        assertThat(result.getFiveDollarCount()).isEqualTo(5);
        assertThat(result.getTwentyDollarCount()).isEqualTo(4);

    }

    @Test
    void givenTwoMoneyObjects_whenSubtractsMoreThanExists_thenThrowIllegalArgumentException() {

        // WHEN - THEN
        assertThatThrownBy(() -> CENT.subtract(TEN_CENT)).isInstanceOf(IllegalArgumentException.class);
    }
}

