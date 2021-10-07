package io.security.core.domain.dto;

import io.security.core.domain.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class ResourceDto {

    private Long id;
    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    private String roleName;
    private Set<Role> roles;
}
