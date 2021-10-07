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
public class Resource {

    @Id @GeneratedValue
    @Column(name = "resource_id")
    private Long id;

    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    @ManyToMany
    @JoinTable(name = "resource_role", joinColumns = @JoinColumn(name = "resource_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
