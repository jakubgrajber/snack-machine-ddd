package com.greybear.snackmachine.domain;


import java.math.BigDecimal;


public record SnackPile(Snack snack, int quantity, BigDecimal price) {

    public SnackPile {
        if (quantity < 0) throw new IllegalArgumentException("The quantity cannot be less than zero.");
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("The price cannot be less than zero.");
        if (price.remainder(new BigDecimal("0.01")).compareTo(BigDecimal.ZERO) > 0)
            throw new IllegalArgumentException("The value of the price cannot be more precise than one cent.");
    }

    public SnackPile subtractOne() {
        return new SnackPile(snack, quantity-1, price);
    }
}
