package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import habsida.spring.boot_security.demo.models.Student;
import habsida.spring.boot_security.demo.services.StudentService;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {

    private final StudentService studentService;
    private final RoleService roleService;

    @Autowired
    public StudentController(StudentService studentService, RoleService roleService) {
        this.studentService = studentService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/roles")
    @ResponseBody
    public List<Role> getRoles() {
        return roleService.listRoles();
    }

    @GetMapping("/admin/adminPage")
    public ModelAndView showStudents() {
        ModelAndView mov = new ModelAndView("/adminPage");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Student> loggedInUserOptional = studentService.findByName(username);
        if (loggedInUserOptional.isPresent()) {
            Student loggedInUser = loggedInUserOptional.get();
            mov.addObject("loggedInUser", loggedInUser);
        } else {
            mov.addObject("loggedInUser", null);
        }
        mov.addObject("students", studentService.listStudents());
        return mov;
    }

    @GetMapping("/admin/studentsData")
    @ResponseBody
    public ResponseEntity<List<Student>> getAllStudentsData() {
        List<Student> students = studentService.listStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/admin/edit")
    public Object editStudent(@RequestParam long id, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        Optional<Student> studentOptional = studentService.studentById(id);
        if (studentOptional.isPresent()) {
            ModelAndView mav = new ModelAndView("/edit");
            mav.addObject("student", studentOptional.get());
            mav.addObject("roles", roleService.listRoles());

            // Check if the request is AJAX or not
            if (requestedWith != null && requestedWith.equals("XMLHttpRequest")) {
                return ResponseEntity.ok(studentOptional.get());
            } else {
                return mav;
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/admin/student/update")
    public ResponseEntity<Student> updateStudent(@RequestParam Long id, @RequestBody Student student) {
        Student updatedStudent = studentService.update(id, student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @PostMapping("/admin/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.add(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/student")
    public ResponseEntity<Void> deleteStudent(@RequestParam long id) {
        studentService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/student")
    public ModelAndView students() {
        ModelAndView mov = new ModelAndView("/student");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        mov.addObject("student", studentService.findByName(username));
        return mov;
    }
}
