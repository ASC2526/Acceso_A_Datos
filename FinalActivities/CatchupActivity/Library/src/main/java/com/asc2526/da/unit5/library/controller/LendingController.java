package com.asc2526.da.unit5.library.controller;

import com.asc2526.da.unit5.library.dto.ReturnResponseDTO;
import com.asc2526.da.unit5.library.model.Lending;
import com.asc2526.da.unit5.library.service.LendingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lendings")
public class LendingController {

    private final LendingService lendingService;

    public LendingController(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    @GetMapping
    public ResponseEntity<List<Lending>> getAllLendings() {
        return ResponseEntity.ok(lendingService.getAllLendings());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Lending>> getLendingsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(lendingService.getLendingsByUser(userId));
    }

    @PostMapping
    public ResponseEntity<Lending> lendBook(@Valid @RequestBody Lending lending) {
        Lending newLending = lendingService.lendBook(lending);
        return new ResponseEntity<>(newLending, HttpStatus.CREATED);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Lending>> getActiveLendings() {
        return ResponseEntity.ok(lendingService.getActiveLendings());
    }

    @GetMapping("/book/{isbn}")
    public ResponseEntity<List<Lending>> getLendingsByBook(@PathVariable String isbn) {
        return ResponseEntity.ok(lendingService.getLendingsByBook(isbn));
    }

    @PutMapping("/return")
    public ResponseEntity<ReturnResponseDTO> returnBook(
            @RequestParam String isbn,
            @RequestParam String userId) {

        ReturnResponseDTO response = lendingService.returnBook(isbn, userId);
        return ResponseEntity.ok(response);
    }
}
