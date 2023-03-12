package com.greybear.snackmachine.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;


@Getter
@ToString
@EqualsAndHashCode
public class SnackPile {

    public static final SnackPile EMPTY = new SnackPile(AggregateReference.to(0L), 0, ZERO);

    @Column("snack_id")
    private final AggregateReference<Snack, Long> snackReference;
    private final int quantity;
    private final BigDecimal price;

    public SnackPile(AggregateReference<Snack, Long> snackReference, int quantity, BigDecimal price) {
        if (quantity < 0)
            throw new IllegalArgumentException("The quantity cannot be less than zero.");
        if (price != null && price.compareTo(ZERO) < 0)
            throw new IllegalArgumentException("The price cannot be less than zero.");
        if (price != null && price.remainder(new BigDecimal("0.01")).compareTo(ZERO) > 0)
            throw new IllegalArgumentException("The value of the price cannot be more precise than one cent.");
        this.snackReference = snackReference;
        this.quantity = quantity;
        this.price = price;
    }

    public SnackPile subtractOne() {
        return new SnackPile(snackReference, quantity-1, price);
    }
}