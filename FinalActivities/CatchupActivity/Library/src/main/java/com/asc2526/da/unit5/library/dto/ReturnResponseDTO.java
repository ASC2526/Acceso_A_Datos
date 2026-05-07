package com.asc2526.da.unit5.library.dto;

import com.asc2526.da.unit5.library.model.Lending;

public class ReturnResponseDTO {
    private final Lending lending;
    private final String message;
    private final String nextReservationUser;

    public ReturnResponseDTO(Lending lending, String message, String nextReservationUser) {
        this.lending = lending;
        this.message = message;
        this.nextReservationUser = nextReservationUser;
    }

    public Lending getLending() { return lending; }
    public String getMessage() { return message; }
    public String getNextReservationUser() { return nextReservationUser; }
}