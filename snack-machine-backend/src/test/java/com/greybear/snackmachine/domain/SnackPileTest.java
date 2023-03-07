package com.greybear.snackmachine.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;


class SnackPileTest {

    @Test
    void givenQuantityLessThenZero_whenCreatesNewSnackPile_thenThrowIllegalArgumentException() {

        // GIVEN
        int quantity = -1;
        Snack snack = new Snack("A snack");
        BigDecimal price = new BigDecimal("1.0");

        // WHEN - THEN
        assertThatThrownBy(() -> new SnackPile(snack, quantity, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenPriceLessThenZero_whenCreatesNewSnackPile_thenThrowsIllegalArgumentException() {

        // GIVEN
        int quantity = 10;
        Snack snack = new Snack("A snack");
        BigDecimal price = new BigDecimal("-1.0");

        // WHEN - THEN
        assertThatThrownBy(() -> new SnackPile(snack, quantity, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenPriceMorePreciseThanOneCent_whenCreatesNewSnackPile_thenThrowsIllegalArgumentException() {

        // GIVEN
        int quantity = 10;
        Snack snack = new Snack("A snack");
        BigDecimal price = new BigDecimal("99.009");

        // WHEN - THEN
        assertThatThrownBy(() -> new SnackPile(snack, quantity, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenASnackPile_whenSubtractsOneSnack_thenReturnsNewInstanceWithQuantityReducedByOne() {

        // GIVEN
        int initQuantity = 10;
        int reducedQuantity = 9;
        Snack snack = new Snack("A snack");
        BigDecimal price = new BigDecimal("12.35");
        SnackPile initSnackPile = new SnackPile(snack, initQuantity, price);

        // WHEN
        SnackPile resultSnackPile = initSnackPile.subtractOne();

        // THEN
        assertThat(resultSnackPile.quantity()).isEqualTo(reducedQuantity);
        assertThat(resultSnackPile.snack()).isEqualTo(snack);
        assertThat(resultSnackPile.price()).isEqualTo(price);

    }
}