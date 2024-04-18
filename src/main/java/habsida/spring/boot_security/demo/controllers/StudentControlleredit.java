
package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.models.Student;
import habsida.spring.boot_security.demo.services.RoleService;
import habsida.spring.boot_security.demo.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class StudentControlleredit {

    private final StudentService studentService;
    private final RoleService roleService;

    @GetMapping("/admin/roles")
    @ResponseBody
    public List<Role> getRoles() {
        return roleService.listRoles();
    }

    @GetMapping(value = "/admin/adminPage")
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

    @GetMapping(value = "/admin/studentsData")
    @ResponseBody
    public List<Student> getStudentsData() {
        return studentService.listStudents();
    }

    @GetMapping("/admin/new")
    public ModelAndView newPerson(@ModelAttribute("student") Student student) {
        ModelAndView mov = new ModelAndView("/new");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Student> loggedInUserOptional = studentService.findByName(username);
        if (loggedInUserOptional.isPresent()) {
            Student loggedInUser = loggedInUserOptional.get();
            mov.addObject("loggedInUser", loggedInUser);
        } else {
            mov.addObject("loggedInUser", null);
        }
        mov.addObject("roles", roleService.listRoles());
        return mov;
    }

    @PostMapping("/admin/adminPage")
    public String create(@ModelAttribute("student") Student student) {
        studentService.add(student);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/admin/edit")
    public ModelAndView edit(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("/edit");
        Student student = studentService.studentById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        mav.addObject("student", student);
        mav.addObject("roles", roleService.listRoles());
        return mav;
    }

    @PostMapping("/admin/edit")
    @ResponseBody
    public ResponseEntity<String> updateStudent(@RequestBody Student student) {
        try {
            studentService.update(student);
            return ResponseEntity.ok("Student updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating student: " + e.getMessage());
        }
    }

    @GetMapping("/admin/delete")
    public ModelAndView delete(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("/delete");
        Student student = studentService.studentById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        mav.addObject("student", student);
        mav.addObject("roles", roleService.listRoles());
        return mav;
    }

    @PostMapping("/admin/delete")
    @ResponseBody
    public ResponseEntity<String> deleteStudent(@RequestParam long id) {
        try {
            studentService.remove(id);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting student: " + e.getMessage());
        }
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/student")
    public ModelAndView students(Principal principal) {
        ModelAndView mov = new ModelAndView("/student");
        mov.addObject("student", studentService.findByName(principal.getName()));
        return mov;
    }
}
