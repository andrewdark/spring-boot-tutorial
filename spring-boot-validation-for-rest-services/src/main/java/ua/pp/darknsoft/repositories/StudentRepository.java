package ua.pp.darknsoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.darknsoft.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
