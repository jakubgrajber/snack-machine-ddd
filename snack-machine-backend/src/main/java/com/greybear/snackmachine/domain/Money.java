package com.greybear.snackmachine.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Getter
@EqualsAndHashCode
@ToString
public class Money {
    private static final String LESS_THAN_ZERO_EXCEPTION_MESSAGE = "The value must be greater than or equal to zero.";

    public static final Money NONE = new Money(0, 0, 0, 0, 0, 0);
    public static final Money CENT = new Money(1, 0, 0, 0, 0, 0);
    public static final Money TEN_CENT = new Money(0, 1, 0, 0, 0, 0);
    public static final Money QUARTER = new Money(0, 0, 1, 0, 0, 0);
    public static final Money DOLLAR = new Money(0, 0, 0, 1, 0, 0);
    public static final Money FIVE_DOLLAR = new Money(0, 0, 0, 0, 1, 0);
    public static final Money TWENTY_DOLLAR = new Money(0, 0, 0, 0, 0, 1);

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

    public Money multiply(int multiplier) {
        return new Money(
                this.oneCentCount * multiplier,
                this.tenCentCount * multiplier,
                this.quarterCount * multiplier,
                this.oneDollarCount * multiplier,
                this.fiveDollarCount * multiplier,
                this.twentyDollarCount * multiplier);
    }

    private BigDecimal calculateAmount() {
        return valueOf(0.01).multiply(valueOf(oneCentCount))
                .add(valueOf(0.1).multiply(valueOf(tenCentCount)))
                .add(valueOf(0.25).multiply(valueOf(quarterCount)))
                .add(valueOf(oneDollarCount))
                .add(valueOf(5).multiply(valueOf(fiveDollarCount)))
                .add(valueOf(20).multiply(valueOf(twentyDollarCount)));
    }

    public Money allocate(BigDecimal amount) {

        double doubleAmount = amount.doubleValue();

        int aTwentyDollarCount = Math.min((int) (doubleAmount / 20), twentyDollarCount);
        doubleAmount = doubleAmount - aTwentyDollarCount * 20;

        int aFiveDollarCount = Math.min((int) (doubleAmount / 5), fiveDollarCount);
        doubleAmount = doubleAmount - aFiveDollarCount * 5;

        int aDollarCount = Math.min((int) doubleAmount, oneDollarCount);
        doubleAmount = doubleAmount - aDollarCount;

        int aQuarterCount = Math.min((int) (doubleAmount / 0.25), quarterCount);
        doubleAmount = doubleAmount - aQuarterCount * 0.25;

        int aTenCentCount = Math.min((int) (doubleAmount / 0.1), tenCentCount);
        doubleAmount = doubleAmount - aTenCentCount * 0.1;

        int aOneCentCount = Math.min((int) (doubleAmount / 0.01), oneCentCount);

        return new Money(
                aOneCentCount,
                aTenCentCount,
                aQuarterCount,
                aDollarCount,
                aFiveDollarCount,
                aTwentyDollarCount);
    }

    public enum Value {
        ONE_CENT, TEN_CENT, QUARTER, ONE_DOLLAR, FIVE_DOLLAR, TWENTY_DOLLAR
    }
}