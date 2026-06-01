package com.asc2526.da.unit5.api;

import com.asc2526.da.unit5.model.*;
import com.asc2526.da.unit5.util.HttpResponse;
import com.asc2526.da.unit5.util.RestApiConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiManager {

    private final RestApiConnection connection;
    private final ObjectMapper mapper;

    public ApiManager() {
        this.connection = new RestApiConnection(
                "http://localhost:8080",
                "/"
        );
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public boolean checkBookExists(String isbn) {
        try {
            HttpResponse response = connection.getRequest("books/" + isbn);
            return response.getStatusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteBook(String isbn) {
        try {
            connection.delete("books", isbn);
        } catch (Exception ignored) {}
    }

    // books
    public void addBook(Book book) throws Exception {

        String categoryCode = (book.getCategory() != null)
                ? book.getCategory().getCode()
                : "OTHER";

        Map<String, Object> map = new HashMap<>();
        map.put("isbn", book.getIsbn());
        map.put("title", book.getTitle());
        map.put("copies", book.getCopies());
        map.put("categoryCode", categoryCode);

        String json = mapper.writeValueAsString(map);
        HttpResponse response = connection.post("books", json);

        if (response.getStatusCode() == 409)
            throw new ApiConflictException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() != 201)
            throw new RuntimeException(parseErrorMessage(response.getBody()));
    }

    // lendings
    public Lending lendBook(String isbn, String userCode) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("book", Map.of("isbn", isbn));
        map.put("borrower", Map.of("code", userCode));

        String json = mapper.writeValueAsString(map);

        HttpResponse response = connection.post("lendings", json);

        if (response.getStatusCode() == 409)
            throw new NoAvailableCopiesException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() == 404)
            throw new RuntimeException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() != 201)
            throw new RuntimeException(parseErrorMessage(response.getBody()));

        return mapper.readValue(response.getBody(), Lending.class);
    }

    public ReturnResponse returnBook(String isbn, String userCode) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("book", Map.of("isbn", isbn));
        map.put("borrower", Map.of("code", userCode));

        String json = mapper.writeValueAsString(map);
        HttpResponse response = connection.put("lendings/return", json);

        if (response.getStatusCode() == 404)
            throw new RuntimeException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() != 200)
            throw new RuntimeException(parseErrorMessage(response.getBody()));

        return mapper.readValue(response.getBody(), ReturnResponse.class);
    }

    // reservations
    public void makeReservation(String isbn, String userCode) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("book", Map.of("isbn", isbn));
        map.put("borrower", Map.of("code", userCode));

        HttpResponse response = connection.post("reservations", mapper.writeValueAsString(map));

        if (response.getStatusCode() == 409)
            throw new ApiConflictException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() != 201)
            throw new RuntimeException(parseErrorMessage(response.getBody()));
    }

    // users
    public User getUser(String userCode) throws Exception {
        HttpResponse response = connection.getRequest("users/" + userCode);

        if (response.getStatusCode() == 404)
            throw new RuntimeException("User not found: " + userCode);

        if (response.getStatusCode() != 200)
            throw new RuntimeException(parseErrorMessage(response.getBody()));

        return mapper.readValue(response.getBody(), User.class);
    }

    // parsers
    private String parseErrorMessage(String body) {
        try {
            return new JSONObject(body).getString("message");
        } catch (Exception e) {
            return body;
        }
    }

}