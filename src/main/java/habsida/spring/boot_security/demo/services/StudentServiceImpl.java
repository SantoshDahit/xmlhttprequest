package habsida.spring.boot_security.demo.services;

import habsida.spring.boot_security.demo.models.Student;
import habsida.spring.boot_security.demo.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Student add(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return student;
    }

    @Override
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void remove(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student update(Long id, Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return student;
    }

    @Override
    public Optional<Student> studentById(long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Optional<Student> findByName(String userName) {
        return studentRepository.findByName(userName);
    }
}
