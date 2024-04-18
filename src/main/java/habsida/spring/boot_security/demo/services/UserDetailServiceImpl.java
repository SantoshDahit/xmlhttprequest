package habsida.spring.boot_security.demo.services;

import habsida.spring.boot_security.demo.models.Student;
import habsida.spring.boot_security.demo.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {


    StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> user = studentRepository.findByName(username);
        user.orElseThrow(() -> new UsernameNotFoundException("None"));
        return user.get();
    }


}
