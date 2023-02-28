package com.greybear.snackmachine.domain;


import lombok.Getter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Accessors(fluent = true)
@Getter
public class Money {
    private static final String LESS_THAN_ZERO_EXCEPTION_MESSAGE = "The value must be greater than or equal to zero.";

    private final int oneCentCount;
    private final int tenCentCount;
    private final int quarterCount;
    private final int oneDollarCount;
    private final int fiveDollarCount;
    private final int twentyDollarCount;
    private final BigDecimal amount;


    public Money(int oneCentCount, int tenCentCount, int quarterCount,
                 int oneDollarCount, int fiveDollarCount, int twentyDollarCount) {
        if (oneCentCount < 0) throw new IllegalArgumentException(LESS_THAN_ZERO_EXCEPTION_MESSAGE);
        if (tenCentCount < 0) throw new IllegalArgumentException(LESS_THAN_ZERO_EXCEPTION_MESSAGE);
        if (quarterCount < 0) throw new IllegalArgumentException(LESS_THAN_ZERO_EXCEPTION_MESSAGE);
        if (oneDollarCount < 0) throw new IllegalArgumentException(LESS_THAN_ZERO_EXCEPTION_MESSAGE);
        if (fiveDollarCount < 0) throw new IllegalArgumentException(LESS_THAN_ZERO_EXCEPTION_MESSAGE);
        if (twentyDollarCount < 0) throw new IllegalArgumentException(LESS_THAN_ZERO_EXCEPTION_MESSAGE);

        this.oneCentCount = oneCentCount;
        this.tenCentCount = tenCentCount;
        this.quarterCount = quarterCount;
        this.oneDollarCount = oneDollarCount;
        this.fiveDollarCount = fiveDollarCount;
        this.twentyDollarCount = twentyDollarCount;

        amount = calculateAmount();
    }

    public Money add(Money moneyToAdd) {
        return new Money(
                this.oneCentCount + moneyToAdd.oneCentCount,
                this.tenCentCount + moneyToAdd.tenCentCount,
                this.quarterCount + moneyToAdd.quarterCount,
                this.oneDollarCount + moneyToAdd.oneDollarCount,
                this.fiveDollarCount + moneyToAdd.fiveDollarCount,
                this.twentyDollarCount + moneyToAdd.twentyDollarCount);
    }

    public Money subtract(Money moneyToSubtract) {
        return new Money(
                this.oneCentCount - moneyToSubtract.oneCentCount,
                this.tenCentCount - moneyToSubtract.tenCentCount,
                this.quarterCount - moneyToSubtract.quarterCount,
                this.oneDollarCount - moneyToSubtract.oneDollarCount,
                this.fiveDollarCount - moneyToSubtract.fiveDollarCount,
                this.twentyDollarCount - moneyToSubtract.twentyDollarCount);
    }

    private BigDecimal calculateAmount() {
        return valueOf(0.01).multiply(valueOf(oneCentCount))
                .add(valueOf(0.1).multiply(valueOf(tenCentCount)))
                .add(valueOf(0.25).multiply(valueOf(quarterCount)))
                .add(valueOf(oneDollarCount))
                .add(valueOf(5).multiply(valueOf(fiveDollarCount)))
                .add(valueOf(20).multiply(valueOf(twentyDollarCount)));
    }
}