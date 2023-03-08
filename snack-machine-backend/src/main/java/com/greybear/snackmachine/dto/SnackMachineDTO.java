package com.greybear.snackmachine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Getter
@AllArgsConstructor
@ToString
@Table(name = "snack_machine")
public class SnackMachineDTO {

    @Id
    private long id;
    private int oneCentCount;
    private int tenCentCount;
    private int quarterCount;
    private int oneDollarCount;
    private int fiveDollarCount;
    private int twentyDollarCount;
    @MappedCollection(idColumn = "snack_machine_id")
    private Set<SlotDTO> slots;
}
