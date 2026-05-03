package com.asc2526.da.unit5.library.controller;

import com.asc2526.da.unit5.library.model.Lending;
import com.asc2526.da.unit5.library.service.LendingService;
import jakarta.validation.Valid;
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
    public List<Lending> getAllLendings() {
        return lendingService.getAllLendings();
    }

    @GetMapping("/user/{userId}")
    public List<Lending> getLendingsByUser(@PathVariable String userId) {
        return lendingService.getLendingsByUser(userId);
    }

    @PostMapping
    public Lending lendBook(@Valid @RequestBody Lending lending) {
        return lendingService.lendBook(lending);
    }

    @GetMapping("/active")
    public List<Lending> getActiveLendings() {
        return lendingService.getActiveLendings();
    }

    @GetMapping("/book/{isbn}")
    public List<Lending> getLendingsByBook(@PathVariable String isbn) {
        return lendingService.getLendingsByBook(isbn);
    }

    @PutMapping("/return")
    public Lending returnBook(
            @RequestParam String isbn,
            @RequestParam String userId) {

        return lendingService.returnBook(isbn, userId);
    }
}
