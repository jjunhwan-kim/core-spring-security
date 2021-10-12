package io.security.core.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ToString(exclude = {"parent", "roleHierarchies"})
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoleHierarchy {

    @Id @GeneratedValue
    @Column(name = "role_hierarchy_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RoleHierarchy parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<RoleHierarchy> roleHierarchies = new HashSet<>();
}
