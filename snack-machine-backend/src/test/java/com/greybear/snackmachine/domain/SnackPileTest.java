package com.greybear.snackmachine.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.greybear.snackmachine.domain.Snack.*;
import static org.assertj.core.api.Assertions.*;


class SnackPileTest {

    @Test
    void givenQuantityLessThenZero_whenCreatesNewSnackPile_thenThrowIllegalArgumentException() {

        // GIVEN
        int quantity = -1;
        BigDecimal price = new BigDecimal("1.0");

        // WHEN - THEN
        assertThatThrownBy(() -> new SnackPile(CHOCOLATE, quantity, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenPriceLessThenZero_whenCreatesNewSnackPile_thenThrowsIllegalArgumentException() {

        // GIVEN
        int quantity = 10;
        BigDecimal price = new BigDecimal("-1.0");

        // WHEN - THEN
        assertThatThrownBy(() -> new SnackPile(CHOCOLATE, quantity, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenPriceMorePreciseThanOneCent_whenCreatesNewSnackPile_thenThrowsIllegalArgumentException() {

        // GIVEN
        int quantity = 10;
        BigDecimal price = new BigDecimal("99.009");

        // WHEN - THEN
        assertThatThrownBy(() -> new SnackPile(CHOCOLATE, quantity, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenASnackPile_whenSubtractsOneSnack_thenReturnsNewInstanceWithQuantityReducedByOne() {

        // GIVEN
        int initQuantity = 10;
        int reducedQuantity = 9;
        BigDecimal price = new BigDecimal("12.35");
        SnackPile initSnackPile = new SnackPile(CHOCOLATE, initQuantity, price);

        // WHEN
        SnackPile resultSnackPile = initSnackPile.subtractOne();

        // THEN
        assertThat(resultSnackPile.getQuantity()).isEqualTo(reducedQuantity);
        assertThat(resultSnackPile.getSnackReference()).isEqualTo(CHOCOLATE);
        assertThat(resultSnackPile.getPrice()).isEqualTo(price);

    }
}