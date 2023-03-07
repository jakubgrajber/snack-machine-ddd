package com.greybear.snackmachine.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@AllArgsConstructor
@ToString
@Table(name = "snack")
public class SnackDTO {

    @Id
    private long id;

    private String name;
}
