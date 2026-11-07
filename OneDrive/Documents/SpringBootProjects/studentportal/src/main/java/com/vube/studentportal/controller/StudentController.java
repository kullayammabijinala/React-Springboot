
package com.vube.studentportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vube.studentportal.dbconnect.FirstConnectJdbcToMysql;
import com.vube.studentportal.service.StudentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/show")
    public String show() {
        return "Welcome to the world";
    }

    // POST: Add student
    // POST http://localhost:8802/student/post1
    @PostMapping("/post1")
    public FirstConnectJdbcToMysql createStudent(
            @RequestBody FirstConnectJdbcToMysql fcs) {

        return studentService.createStudent(fcs);
    }

    // PUT: Update complete student
    // PUT http://localhost:8802/student/update/2
    @PutMapping("/update/{id}")
    public FirstConnectJdbcToMysql updateStudent(
            @RequestBody FirstConnectJdbcToMysql fcs,
            @PathVariable Integer id) {

        return studentService.updateStudent(fcs, id);
    }

    // GET: All students
    @GetMapping("/getallStudent")
    public List<FirstConnectJdbcToMysql> getAllStudent() {
        return studentService.getAllStudent();
    }

    // GET: Student by ID
    @GetMapping("/getStudentById/{id}")
    public FirstConnectJdbcToMysql getStudentById(
            @PathVariable Integer id) throws Exception {

        return studentService.getStudentById(id);
    }

    // DELETE: Delete student from database
    // DELETE http://localhost:8802/student/delete/2
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // PATCH: Update only selected fields
    // PATCH http://localhost:8802/student/partialUpdate/2
    @PatchMapping("/partialUpdate/{id}")
    public ResponseEntity<FirstConnectJdbcToMysql> partialUpdateStudent(
            @PathVariable Integer id,
            @RequestBody FirstConnectJdbcToMysql student) {

        FirstConnectJdbcToMysql updated =
                studentService.partialUpdateStudent(student, id);

        return ResponseEntity.ok(updated);
    }
}
