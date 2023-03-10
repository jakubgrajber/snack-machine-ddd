package com.greybear.snackmachine.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

import static com.greybear.snackmachine.domain.Snack.NONE;
import static java.math.BigDecimal.ZERO;


@NoArgsConstructor
@Getter
@ToString
public class SnackPile {

    public static final SnackPile EMPTY = new SnackPile(NONE, 0, ZERO);
    @ToString.Exclude
//    @Transient
    private Snack snack;
//    private long snackId;
    private int quantity;
    private BigDecimal price;

    public SnackPile(Snack snack, int quantity, BigDecimal price) {
        if (quantity < 0)
            throw new IllegalArgumentException("The quantity cannot be less than zero.");
        if (price != null && price.compareTo(ZERO) < 0)
            throw new IllegalArgumentException("The price cannot be less than zero.");
        if (price != null && price.remainder(new BigDecimal("0.01")).compareTo(ZERO) > 0)
            throw new IllegalArgumentException("The value of the price cannot be more precise than one cent.");
        this.snack = snack;
        this.quantity = quantity;
        this.price = price;
//        this.snackId = snack.getId();
    }

    public SnackPile subtractOne() {
        return new SnackPile(snack, quantity-1, price);
    }
}
