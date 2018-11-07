package ua.pp.darknsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.darknsoft.entities.Student;
import ua.pp.darknsoft.exceptions.StudentNotFoundException;
import ua.pp.darknsoft.repositories.StudentRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class StudentRestController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping(value = "findAll")
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentRepository.findAll();

        if (students == null || students.isEmpty()) {
            System.out.println("we are here");
            return new ResponseEntity<List<Student>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
    }

    @GetMapping(value = "getAll") //new approach
    public List<Student> getAll() throws ClassNotFoundException {
        List<Student> students = studentRepository.findAll();
        if (students == null || students.isEmpty()) {
            throw new StudentNotFoundException("No one student is found");
        }
        return students;
    }
    
}
