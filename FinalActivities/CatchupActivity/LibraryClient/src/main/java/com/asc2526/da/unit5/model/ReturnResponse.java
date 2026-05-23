package com.asc2526.da.unit5.model;

public class ReturnResponse {

    private Lending lending;
    private String message;
    private String nextReservationUser;

    public ReturnResponse() {}

    public Lending getLending() { return lending; }
    public void setLending(Lending lending) { this.lending = lending; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getNextReservationUser() { return nextReservationUser; }
    public void setNextReservationUser(String v) { this.nextReservationUser = v; }
}