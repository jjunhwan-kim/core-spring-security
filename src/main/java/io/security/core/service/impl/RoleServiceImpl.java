package io.security.core.service.impl;

import io.security.core.domain.entity.Role;
import io.security.core.repository.RoleRepository;
import io.security.core.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id).orElse(new Role());
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
