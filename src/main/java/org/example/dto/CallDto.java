package org.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CallDto {

    private Long id;
    private String abonentName;
    private String cityName;
    private LocalDate date;
    private LocalTime time;
    private Integer minutes;
    private BigDecimal price;
}
