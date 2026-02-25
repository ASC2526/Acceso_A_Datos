package com.asc2526.da.unit5.vtschool_rest_api.web.controller;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Enrollment;
import com.asc2526.da.unit5.vtschool_rest_api.entity.Student;
import com.asc2526.da.unit5.vtschool_rest_api.exception.EnrollmentAlreadyExistsException;
import com.asc2526.da.unit5.vtschool_rest_api.service.*;
import com.asc2526.da.unit5.vtschool_rest_api.web.dto.ScoreDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    // examen
    @GetMapping("/admin/add-student")
    public String addStudent(Model model) {
        return "add-student";
    }

    @PostMapping("/admin/add-student")
    public String addStudentSubmit(@ModelAttribute("student") @Valid Student student,
                                   RedirectAttributes redirectAttributes,
                                   BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()) {
            return "add-student";
        }
        try {
            studentService.create(student);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Student created successfully."
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error saving the student"
            );
        }
        return "redirect:/admin/add-student";
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

        } catch (EnrollmentAlreadyExistsException e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Student is already enrolled this year."
            );

        } catch (IllegalStateException e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage()
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Unexpected error enrolling student."
            );
        }

        redirectAttributes.addFlashAttribute("selectedStudent", studentId);
        redirectAttributes.addFlashAttribute("selectedCourse", courseId);

        return "redirect:/admin/enroll";
    }

    // qualify

    @GetMapping("/admin/qualify")
    public String showQualifyPage(Model model) {

        List<Student> students =
                studentService.findStudentsWithPendingScores();

        model.addAttribute("students", students);
        model.addAttribute("courses", courseService.findAll());

        if (students.isEmpty()) {
            model.addAttribute("message",
                    "No students pending qualification.");
        }

        if (model.containsAttribute("selectedStudent")
                && model.containsAttribute("selectedCourse")) {

            String studentId =
                    (String) model.getAttribute("selectedStudent");

            Integer courseId =
                    (Integer) model.getAttribute("selectedCourse");

            List<ScoreDTO> pending =
                    scoreService.findPendingScores(studentId, courseId);

            if (pending.isEmpty()) {
                model.addAttribute("message",
                        "No pending subjects to qualify.");
            } else {
                model.addAttribute("scores", pending);
            }
        }

        return "qualify";
    }

    @PostMapping("/admin/qualify/load")
    public String loadPendingSubjects(
            String studentId,
            Integer courseId,
            RedirectAttributes redirectAttributes
    ) {

        redirectAttributes.addFlashAttribute("selectedStudent", studentId);
        redirectAttributes.addFlashAttribute("selectedCourse", courseId);

        return "redirect:/admin/qualify";
    }

    @PostMapping("/admin/qualify/save")
    public String saveScores(
            @RequestParam List<Integer> scoreId,
            @RequestParam List<Integer> scoreValue,
            @RequestParam String studentId,
            @RequestParam Integer courseId,
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

        redirectAttributes.addFlashAttribute("selectedStudent", studentId);
        redirectAttributes.addFlashAttribute("selectedCourse", courseId);

        return "redirect:/admin/qualify";
    }
}