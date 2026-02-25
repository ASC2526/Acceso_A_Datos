package com.asc2526.da.unit5.vtschool_rest_api.web.controller;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Student;
import com.asc2526.da.unit5.vtschool_rest_api.exception.EmailAlreadyExistsException;
import com.asc2526.da.unit5.vtschool_rest_api.exception.StudentNotFoundException;
import com.asc2526.da.unit5.vtschool_rest_api.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class RegisterWebController {
    private final StudentService studentService;
    public RegisterWebController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(BindingResult bindingResult,
                                 @Valid String idCard,
                                 @Valid String emailIntroduced,
                                 RedirectAttributes redirectAttributes
                                  ) {
        if(bindingResult.hasErrors()) {
            return "register";
        }
        try {

            Student exists = studentService
                    .findById(idCard);

            if(exists == null) {
                throw new StudentNotFoundException(idCard + " not found. Please enter an existing idCard");
            }

            Optional<Student> existsByEmail = studentService.findByEmail(emailIntroduced);

            if(existsByEmail.isPresent()) {
                throw new EmailAlreadyExistsException("The email is already used by another student");
            }

            return "redirect:/login";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error registering the user"
            );

            return "redirect:/register";
        }
    }

}