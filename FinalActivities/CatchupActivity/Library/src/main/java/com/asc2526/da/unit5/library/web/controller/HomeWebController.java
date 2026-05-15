package com.asc2526.da.unit5.library.web.controller;

import com.asc2526.da.unit5.library.dto.BookRequestDTO;
import com.asc2526.da.unit5.library.dto.ReturnResponseDTO;
import com.asc2526.da.unit5.library.model.Book;
import com.asc2526.da.unit5.library.model.Lending;
import com.asc2526.da.unit5.library.model.User;
import com.asc2526.da.unit5.library.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
public class HomeWebController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ReservationService reservationService;
    private final LendingService lendingService;

    public HomeWebController(BookService bookService,
                             CategoryService categoryService,
                             UserService userService,
                             ReservationService reservationService, LendingService lendingService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.reservationService = reservationService;
        this.lendingService = lendingService;
    }

    @GetMapping("/home")
    public String home(Model model, Authentication auth, @RequestParam(required = false) String categoryCode) {
        if (auth == null) {
            return "redirect:/login";
        }

        if (auth.getAuthorities().stream().anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"))) {
            model.addAttribute("books", bookService.getAll());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "librarian-home";
        }

        List<Book> books = (categoryCode != null && !categoryCode.isBlank())
                ? bookService.findByCategory(categoryCode)
                : bookService.getAll();

        User user = userService.getUserById(auth.getName());
        if (user.getFined() != null && user.getFined().isAfter(java.time.LocalDate.now())) {
            model.addAttribute("warningMessage", "Warning: You are currently fined until " + user.getFined());
        }

        model.addAttribute("books", books);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedCategory", categoryCode);

        return "home";
    }

    @GetMapping("/profile")
    public String editProfile(Model model, Authentication auth) {
        if (auth == null) {
            return "redirect:/login";
        }
        if ("library".equals(auth.getName())) {
            return "redirect:/home";
        }
        User user = userService.getUserById(auth.getName());
        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String name,
                                @RequestParam String surname,
                                Authentication auth,
                                HttpServletRequest request,
                                RedirectAttributes ra) {
        try {
            boolean logoutRequired = userService.updateUser(auth.getName(), name, surname);
            if (logoutRequired) {
                request.getSession().invalidate();
                ra.addFlashAttribute("successMessage", "Surname updated. Please login with your new username.");
                return "redirect:/login";
            }
            userService.updateUser(auth.getName(), name, surname);
            ra.addFlashAttribute("successMessage", "Profile updated successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Error updating profile: " + e.getMessage());
        }
        return "redirect:/profile";
    }

    @PostMapping("/reserve")
    public String reserveBook(@RequestParam String isbn,
                              Authentication auth,
                              RedirectAttributes redirectAttributes) {
        try {
            String userCode = auth.getName();
            reservationService.createReservation(userCode, isbn);

            redirectAttributes.addFlashAttribute("successMessage", "Book reserved successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/home";
    }

    @PostMapping("/books/new")
    public String addBook(@ModelAttribute BookRequestDTO bookDto, RedirectAttributes ra) {
        try {
            bookService.createBook(bookDto);
            ra.addFlashAttribute("successMessage", "Book added successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Error adding book: " + e.getMessage());
        }
        return "redirect:/home";
    }

    @PostMapping("/lend")
    public String lendBook(@RequestParam String isbn,
                           @RequestParam String userCode,
                           RedirectAttributes ra) {
        try {
            Lending lending = new Lending();

            Book b = new Book();
            b.setIsbn(isbn);
            User u = new User();
            u.setCode(userCode);

            lending.setBook(b);
            lending.setBorrower(u);

            lendingService.lendBook(lending);
            ra.addFlashAttribute("successMessage", "Book lent successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/home";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam String isbn,
                             @RequestParam String userCode,
                             RedirectAttributes ra) {
        try {
            ReturnResponseDTO response = lendingService.returnBook(isbn, userCode);
            ra.addFlashAttribute("successMessage", response.getMessage());
            if (response.getNextReservationUser() != null) {
                ra.addFlashAttribute("warningMessage", "ADVICE: Next reservation for " + response.getNextReservationUser());
            }
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/home";
    }
}