package io.security.core.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@ToString(exclude = {"accounts", "resources"})
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {

    @Id @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String roleName;

    private String roleDesc;

    @ManyToMany(mappedBy = "roles")
    @OrderBy("orderNum desc")
    private Set<Resource> resources = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts = new HashSet<>();
}
