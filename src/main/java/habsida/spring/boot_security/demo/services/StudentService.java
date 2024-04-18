package habsida.spring.boot_security.demo.services;


import habsida.spring.boot_security.demo.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    void add(Student student);
    List<Student> listStudents();

    void remove(long id);

    void update(Student student);

    Optional<Student> studentById(long id);

    Optional<Student> findByName(String userName);
}
