package com.asc2526.da.unit5.vtschool_rest_api.controller;

import com.asc2526.da.unit5.vtschool_rest_api.entity.Score;
import com.asc2526.da.unit5.vtschool_rest_api.service.ScoreService;
import com.asc2526.da.unit5.vtschool_rest_api.web.dto.ScoreCourseYearDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<Score> getScores(
            @RequestParam(required = false) Integer enrollmentId,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) Integer courseId,
            @RequestParam(required = false) Boolean passed,
            @RequestParam(required = false) Boolean nullScores
    ) {
        return scoreService.findScores(enrollmentId, studentId, courseId, passed, nullScores);
    }

    @GetMapping
    public List<ScoreCourseYearDTO> getScoresInCourseYear(@RequestParam Integer courseId,
                                              @RequestParam Integer year) {
        return scoreService.getScoresForCourseYear(courseId, year);
    }

    @PostMapping
    public List<Score> saveScores(@Valid @RequestBody List<Score> scores) {
        return scoreService.saveAll(scores);
    }

}
