package com.greybear.snackmachine.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@Getter
@EqualsAndHashCode(exclude = "name")
@ToString
public class Snack {

    public static final AggregateReference<Snack, Long> CHOCOLATE = AggregateReference.to(1L);
    public static final AggregateReference<Snack, Long> SODA = AggregateReference.to(2L);
    public static final AggregateReference<Snack, Long> GUM = AggregateReference.to(3L);

    @Id
    private final long id;
    private final String name;

    @PersistenceCreator
    public Snack(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
