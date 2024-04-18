package habsida.spring.boot_security.demo.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "students")
@NoArgsConstructor
public class Student implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)

    private String name;
    private String LastName;
    private String degree;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_roles", joinColumns = {@JoinColumn(name = "student_id")},
                inverseJoinColumns = {@JoinColumn(name = "roles_id")})
    private Set<Role> roles;

    public Student(@NonNull String name, String lastName, String degree, @NonNull String password, @NonNull Set<Role> roles) {
        this.name = name;
        LastName = lastName;
        this.degree = degree;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
