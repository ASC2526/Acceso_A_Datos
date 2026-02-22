package com.asc2526.da.unit5.vtschool_rest_api.web.controller;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Enrollment;
import com.asc2526.da.unit5.vtschool_rest_api.service.*;
import com.asc2526.da.unit5.vtschool_rest_api.web.dto.ScoreDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Year;
import java.util.List;

@Controller
public class AdminWebController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final ScoreService scoreService;

    public AdminWebController(StudentService studentService,
                              CourseService courseService,
                              EnrollmentService enrollmentService,
                              ScoreService scoreService) {

        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.scoreService = scoreService;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    // enroll

    @GetMapping("/admin/enroll")
    public String enrollForm(Model model) {

        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());

        return "enroll";
    }

    @PostMapping("/admin/enroll")
    public String enrollStudent(
            String studentId,
            Integer courseId,
            RedirectAttributes redirectAttributes
    ) {
        try {

            Enrollment enrollment = new Enrollment();
            enrollment.setStudentId(studentId);
            enrollment.setCourseId(courseId);
            enrollment.setYear(Year.now().getValue());

            enrollmentService.create(enrollment);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Student enrolled successfully."
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error enrolling student"
            );
        }

        return "redirect:/admin/enroll";
    }

    // qualify

    @GetMapping("/admin/qualify")
    public String showQualifyPage(Model model) {

        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());

        return "qualify";
    }

    @PostMapping("/admin/qualify/load")
    public String loadPendingSubjects(
            String studentId,
            Integer courseId,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        try {

            List<ScoreDTO> pending =
                    scoreService.findPendingScores(studentId, courseId);

            model.addAttribute("students", studentService.findAll());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("selectedStudent", studentId);
            model.addAttribute("selectedCourse", courseId);

            if (pending.isEmpty()) {
                model.addAttribute("message",
                        "No pending subjects to qualify.");
            } else {
                model.addAttribute("scores", pending);
            }

            return "qualify";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Unexpected error loading subjects"
            );

            return "redirect:/admin/qualify";
        }
    }

    @PostMapping("/admin/qualify/save")
    public String saveScores(
            @RequestParam List<Integer> scoreId,
            @RequestParam List<Integer> scoreValue,
            RedirectAttributes redirectAttributes
    ) {

        try {

            for (int i = 0; i < scoreId.size(); i++) {

                if (scoreValue.get(i) != null) {
                    scoreService.updateScore(
                            scoreId.get(i),
                            scoreValue.get(i)
                    );
                }
            }

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Scores saved successfully"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error saving scores"
            );
        }

        return "redirect:/admin/qualify";
    }
}