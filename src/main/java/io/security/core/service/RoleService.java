package io.security.core.service;

import io.security.core.domain.entity.Role;

import java.util.List;

public interface RoleService {

    void createRole(Role role);
    Role getRole(Long id);
    List<Role> getRoles();
    void deleteRole(Long id);
}
