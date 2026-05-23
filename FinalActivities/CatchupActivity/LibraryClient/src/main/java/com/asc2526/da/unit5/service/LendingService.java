package com.asc2526.da.unit5.service;

import com.asc2526.da.unit5.api.ApiManager;
import com.asc2526.da.unit5.api.NoAvailableCopiesException;
import com.asc2526.da.unit5.model.Lending;
import com.asc2526.da.unit5.model.ReturnResponse;

import java.util.Scanner;

public class LendingService {

    private static final ApiManager api = new ApiManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void lend(String isbn, String userCode) {

        try {
            Lending lending = api.lendBook(isbn, userCode);
            System.out.println("Book lent successfully.");
            System.out.println("Lending ID  : " + lending.getId());
            System.out.println("Lending date: " + lending.getLendingDate());

        } catch (NoAvailableCopiesException e) {
            System.out.println("No copies available for ISBN: " + isbn);
            System.out.print("Do you want to make a reservation? (yes/no): ");

            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("yes") || answer.equals("y")) {
                ReservationService.reserve(isbn, userCode);
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public static void returnBook(String isbn, String userCode) {

        try {
            ReturnResponse response = api.returnBook(isbn, userCode);
            System.out.println(response.getMessage());

            if (response.getNextReservationUser() != null
                    && !response.getNextReservationUser().isBlank()) {
                System.out.println("ADVICE: pending reservation for: " + response.getNextReservationUser());
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}