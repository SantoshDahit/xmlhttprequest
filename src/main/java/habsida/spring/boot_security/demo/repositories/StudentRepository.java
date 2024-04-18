package habsida.spring.boot_security.demo.repositories;

import habsida.spring.boot_security.demo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    default Optional<Student> findByName(String userName) {
        return findAll().stream().filter(user -> user.getName().equals(userName)).findFirst();
    };
}
