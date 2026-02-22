package com.asc2526.da.unit5.vtschool_rest_api.web.controller;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Student;
import com.asc2526.da.unit5.vtschool_rest_api.service.CourseService;
import com.asc2526.da.unit5.vtschool_rest_api.service.ScoreService;
import com.asc2526.da.unit5.vtschool_rest_api.service.StudentService;
import com.asc2526.da.unit5.vtschool_rest_api.web.dto.ScoreDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentWebController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final ScoreService scoreService;

    public StudentWebController(StudentService studentService,
                                CourseService courseService,
                                ScoreService scoreService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.scoreService = scoreService;
    }

    // panel

    @GetMapping
    public String studentPanel(Authentication authentication, Model model) {

        String email = authentication.getName();

        Student student = studentService
                .findByEmail(email)
                .orElseThrow();

        model.addAttribute("student", student);

        return "student";
    }

    // edit profile

    @GetMapping("/edit")
    public String editForm(Authentication authentication, Model model) {

        String email = authentication.getName();

        Student student = studentService
                .findByEmail(email)
                .orElseThrow();

        model.addAttribute("student", student);

        return "edit-profile";
    }

    @PostMapping("/edit")
    public String updateProfile(
            @Valid Student student,
            Authentication authentication,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {

        try {

            String loggedEmail = authentication.getName();

            Student current = studentService
                    .findByEmail(loggedEmail)
                    .orElseThrow();

            String oldEmail = current.getEmail();

            student.setIdcard(current.getIdcard());

            studentService.update(current.getIdcard(), student);

            if (!oldEmail.equals(student.getEmail())) {
                request.getSession().invalidate();

                redirectAttributes.addFlashAttribute(
                        "successMessage",
                        "Email changed. Please login again."
                );
                return "redirect:/login";
            }

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Profile updated. Please login again."
            );

            return "redirect:/student";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error updating profile"
            );

            return "redirect:/student/edit";
        }
    }

    // my scores

    @GetMapping("/scores")
    public String showScores(Authentication authentication, Model model) {

        String email = authentication.getName();

        Student student = studentService
                .findByEmail(email)
                .orElseThrow();

        model.addAttribute("courses", courseService.findAll());

        List<ScoreDTO> scores =
                scoreService.getScoresForStudent(student.getIdcard(), null);

        model.addAttribute("scores", scores);

        return "student-scores";
    }

    @PostMapping("/scores")
    public String filterScores(
            Authentication authentication,
            @RequestParam(required = false) Integer courseId,
            Model model
    ) {

        String email = authentication.getName();

        Student student = studentService
                .findByEmail(email)
                .orElseThrow();

        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("selectedCourse", courseId);

        List<ScoreDTO> scores =
                scoreService.getScoresForStudent(
                        student.getIdcard(),
                        courseId
                );

        model.addAttribute("scores", scores);

        return "student-scores";
    }
}