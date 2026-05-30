package com.asc2526.da.unit5.api;

import com.asc2526.da.unit5.model.*;
import com.asc2526.da.unit5.util.HttpResponse;
import com.asc2526.da.unit5.util.RestApiConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiManager {

    private final RestApiConnection connection;

    public ApiManager() {
        this.connection = new RestApiConnection(
                "http://localhost:8080",
                "/"
        );
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

        String json = new JSONObject()
                .put("isbn", book.getIsbn())
                .put("title", book.getTitle())
                .put("copies", book.getCopies())
                .put("categoryCode", categoryCode)
                .toString();

        HttpResponse response = connection.post("books", json);

        if (response.getStatusCode() == 409)
            throw new ApiConflictException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() != 201)
            throw new RuntimeException(parseErrorMessage(response.getBody()));
    }

    // lendings
    public Lending lendBook(String isbn, String userCode) throws Exception {

        String json = new JSONObject()
                .put("book", new JSONObject().put("isbn", isbn))
                .put("borrower", new JSONObject().put("code", userCode))
                .toString();

        HttpResponse response = connection.post("lendings", json);

        if (response.getStatusCode() == 409)
            throw new NoAvailableCopiesException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() == 404)
            throw new RuntimeException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() != 201)
            throw new RuntimeException(parseErrorMessage(response.getBody()));

        return parseLending(response.getBody());
    }

    public ReturnResponse returnBook(String isbn, String userCode) throws Exception {

        String json = new JSONObject()
                .put("book", new JSONObject().put("isbn", isbn))
                .put("borrower", new JSONObject().put("code", userCode))
                .toString();

        HttpResponse response = connection.put("lendings/return", json);

        if (response.getStatusCode() == 404)
            throw new RuntimeException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() != 200)
            throw new RuntimeException(parseErrorMessage(response.getBody()));

        return parseReturnResponse(response.getBody());
    }

    // reservations
    public void makeReservation(String isbn, String userCode) throws Exception {

        String json = new JSONObject()
                .put("book", new JSONObject().put("isbn", isbn))
                .put("borrower", new JSONObject().put("code", userCode))
                .toString();

        HttpResponse response = connection.post("reservations", json);

        if (response.getStatusCode() == 409)
            throw new ApiConflictException(parseErrorMessage(response.getBody()));

        if (response.getStatusCode() != 201)
            throw new RuntimeException(parseErrorMessage(response.getBody()));
    }

    // parsers
    private String parseErrorMessage(String body) {
        try {
            return new JSONObject(body).getString("message");
        } catch (Exception e) {
            return body;
        }
    }

    private String extractDate(JSONObject obj, String... keys) throws JSONException {
        for (String key : keys) {
            Object val = obj.opt(key);
            if (val != null && !JSONObject.NULL.equals(val)) {
                if (val instanceof org.json.JSONArray arr) {
                    return String.format("%04d-%02d-%02d",
                            arr.getInt(0), arr.getInt(1), arr.getInt(2));
                }
                return val.toString();
            }
        }
        return null;
    }

    private Lending parseLending(String body) {
        if (body == null || body.trim().isEmpty()) return new Lending();
        try {
            JSONObject obj = new JSONObject(body);
            Lending lending = new Lending();

            lending.setId(obj.optInt("id", 0));

            lending.setLendingDate(extractDate(obj, "lendingDate", "lendingdate"));
            lending.setReturningDate(extractDate(obj, "returningDate", "returningdate"));

            return lending;
        } catch (Exception e) {
            return new Lending();
        }
    }

    private ReturnResponse parseReturnResponse(String body) {
        try {
            JSONObject obj = new JSONObject(body);
            ReturnResponse r = new ReturnResponse();

            r.setMessage(obj.optString("message", null));
            r.setNextReservationUser(obj.optString("nextReservationUser", null));

            JSONObject lObj = obj.optJSONObject("lending");
            if (lObj != null) {
                Lending lending = new Lending();
                lending.setId(lObj.optInt("id", 0));
                lending.setLendingDate(extractDate(lObj, "lendingdate", "lendingDate"));
                lending.setReturningDate(extractDate(lObj, "returningdate", "returningdate"));
                r.setLending(lending);
            }
            return r;
        } catch (Exception e) {
            return new ReturnResponse();
        }
    }
}