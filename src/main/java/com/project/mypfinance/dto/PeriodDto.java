package com.project.mypfinance.dto;

import java.time.LocalDate;

public class PeriodDto {
    private LocalDate from;
    private LocalDate to;

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
