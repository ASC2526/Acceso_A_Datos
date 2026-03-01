package com.vtschool2526.model.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtschool2526.model.*;
import com.vtschool2526.util.HttpResponse;
import com.vtschool2526.util.RestApiConnection;

import java.util.List;

public class ApiManager {

    private final RestApiConnection connection;
    private final ObjectMapper mapper;

    public ApiManager() {
        this.connection = new RestApiConnection(
                "http://localhost:8080/",
                ""
        );
        this.mapper = new ObjectMapper();
    }

    // students

    public List<Student> getAllStudents() throws Exception {

        HttpResponse response = connection.getRequest("students");

        if (response.getStatusCode() != 200)
            throw new RuntimeException(response.getBody());

        return mapper.readValue(
                response.getBody(),
                new TypeReference<>() {
                }
        );
    }

    public Student getStudentById(String idcard) throws Exception {

        HttpResponse response = connection.getRequest("students/" + idcard);

        if (response.getStatusCode() != 200)
            throw new RuntimeException(response.getBody());

        return mapper.readValue(response.getBody(), Student.class);
    }

    public List<Student> createStudents(List<Student> students) throws Exception {

        String json = mapper.writeValueAsString(students);

        HttpResponse response = connection.post("students", json);

        if (response.getStatusCode() != 201 && response.getStatusCode() != 200)
            throw new RuntimeException(response.getBody());

        if (response.getBody() == null || response.getBody().isBlank()) {
            return students;
        }

        return mapper.readValue(
                response.getBody(),
                new TypeReference<>() {
                }
        );
    }

    public Student updateStudent(String idcard, Student student) throws Exception {

        String json = mapper.writeValueAsString(student);

        HttpResponse response =
                connection.put("students/" + idcard, json);

        if (response.getStatusCode() != 200)
            throw new RuntimeException(response.getBody());

        return mapper.readValue(response.getBody(), Student.class);
    }

    public void deleteStudent(String idcard) throws Exception {

        connection.delete("students", idcard);
    }


    // courses

    public List<Course> getAllCourses() throws Exception {

        HttpResponse response = connection.getRequest("courses");

        if (response.getStatusCode() != 200)
            throw new RuntimeException(response.getBody());

        return mapper.readValue(
                response.getBody(),
                new TypeReference<>() {
                }
        );
    }


    // subjects

    public List<Subject> getSubjectsByCourse(Integer courseId) throws Exception {

        HttpResponse response =
                connection.getRequest("subjects?courseId=" + courseId);

        if (response.getStatusCode() != 200)
            throw new RuntimeException(response.getBody());

        return mapper.readValue(
                response.getBody(),
                new TypeReference<>() {
                }
        );
    }


    // enrollments

    public Enrollment enrollStudent(Enrollment enrollment) throws Exception {

        String json = mapper.writeValueAsString(enrollment);

        HttpResponse response =
                connection.post("enrollments", json);

        if (response.getStatusCode() != 201 && response.getStatusCode() != 200)
            throw new RuntimeException(response.getBody());

        if (response.getBody() == null || response.getBody().isBlank()) {
            return enrollment;
        }

        return mapper.readValue(response.getBody(), Enrollment.class);
    }

    public List<Enrollment> getEnrollments(String studentId,
                                           Integer courseId,
                                           Integer year) throws Exception {

        String endpoint = "enrollments?";

        if (studentId != null)
            endpoint += "studentId=" + studentId + "&";

        if (courseId != null)
            endpoint += "courseId=" + courseId + "&";

        if (year != null)
            endpoint += "year=" + year;

        HttpResponse response = connection.getRequest(endpoint);

        if (response.getStatusCode() != 200)
            throw new RuntimeException(response.getBody());

        return mapper.readValue(
                response.getBody(),
                new TypeReference<>() {
                }
        );
    }

    public void deleteEnrollment(Integer id) throws Exception {

        connection.delete("enrollments", id.toString());
    }


    // scores

    public List<Score> saveScores(List<Score> scores) throws Exception {

        String json = mapper.writeValueAsString(scores);

        HttpResponse response =
                connection.post("scores", json);

        if (response.getStatusCode() != 200 && response.getStatusCode() != 201)
            throw new RuntimeException(response.getBody());

        return mapper.readValue(
                response.getBody(),
                new TypeReference<>() {
                }
        );
    }

    public List<Score> getScores(String studentId,
                                 Integer courseId) throws Exception {

        String endpoint =
                "scores?studentId=" + studentId + "&courseId=" + courseId;

        HttpResponse response = connection.getRequest(endpoint);

        if (response.getStatusCode() != 200)
            throw new RuntimeException(response.getBody());

        return mapper.readValue(
                response.getBody(),
                new TypeReference<>() {
                }
        );
    }
}