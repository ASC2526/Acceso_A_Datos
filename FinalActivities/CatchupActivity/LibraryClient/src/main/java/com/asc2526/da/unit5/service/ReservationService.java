package com.asc2526.da.unit5.service;

import com.asc2526.da.unit5.api.ApiManager;

public class ReservationService {

    private static final ApiManager api = new ApiManager();

    public static void reserve(String isbn, String userCode) {

        try {
            api.makeReservation(isbn, userCode);
            System.out.println("Reservation created successfully.");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}