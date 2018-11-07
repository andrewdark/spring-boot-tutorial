package ua.pp.darknsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.pp.darknsoft.entities.Student;
import ua.pp.darknsoft.exceptions.StudentNotFoundException;
import ua.pp.darknsoft.repositories.StudentRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

    @GetMapping("/findAll/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable long id) throws ClassNotFoundException {
        Optional<Student> student = studentRepository.findById(id);

        if (!student.isPresent())
            new ResponseEntity<Student>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Student>(student.get(), HttpStatus.OK);
    }

    //new approach
    @GetMapping(value = "/students")
    public List<Student> getAll() throws ClassNotFoundException {
        List<Student> students = studentRepository.findAll();
        if (students == null || students.isEmpty()) {
            throw new StudentNotFoundException("No one student is found");
        }
        return students;
    }

    @GetMapping("/students/{id}")
    public Resource<Student> retrieveStudent(@PathVariable long id) throws ClassNotFoundException {
        Optional<Student> student = studentRepository.findById(id);

        if (!student.isPresent())
            throw new StudentNotFoundException("id-" + id);

        Resource<Student> resource = new Resource<Student>(student.get());

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());

        resource.add(linkTo.withRel("all-students"));

        return resource;
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable long id) {
        studentRepository.deleteById(id);
    }

    @PostMapping("/students")
    public ResponseEntity<Object> createStudent(@Valid @RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStudent.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@Valid @RequestBody Student student, @PathVariable long id) {

        Optional<Student> studentOptional = studentRepository.findById(id);

        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();

        student.setId(id);

        studentRepository.save(student);

        return ResponseEntity.noContent().build();
    }

}
