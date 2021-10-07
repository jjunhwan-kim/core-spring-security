package io.security.core.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ToString(exclude = {"roles"})
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;
    private String username;
    private String password;
    private String email;
    private int age;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "account_role", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
