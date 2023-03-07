package com.greybear.snackmachine.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "slot")
public class SlotDTO {

    @Id
    private long id;
    private int quantity;
    private BigDecimal price;
    private long snackMachineId;
    private long snackId;
    private int position;
}
