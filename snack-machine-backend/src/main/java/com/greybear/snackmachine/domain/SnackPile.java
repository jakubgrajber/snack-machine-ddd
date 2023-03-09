package com.greybear.snackmachine.domain;


import java.math.BigDecimal;

import static com.greybear.snackmachine.domain.Snack.*;
import static java.math.BigDecimal.*;


public record SnackPile(Snack snack, int quantity, BigDecimal price) {

    public static final SnackPile EMPTY = new SnackPile(NONE, 0, ZERO);

    public SnackPile {
        if (quantity < 0)
            throw new IllegalArgumentException("The quantity cannot be less than zero.");
        if (price != null && price.compareTo(ZERO) < 0)
            throw new IllegalArgumentException("The price cannot be less than zero.");
        if (price != null && price.remainder(new BigDecimal("0.01")).compareTo(ZERO) > 0)
            throw new IllegalArgumentException("The value of the price cannot be more precise than one cent.");
    }

    public SnackPile subtractOne() {
        return new SnackPile(snack, quantity-1, price);
    }
}
