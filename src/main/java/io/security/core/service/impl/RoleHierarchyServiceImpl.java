package io.security.core.service.impl;

import io.security.core.domain.entity.RoleHierarchy;
import io.security.core.repository.RoleHierarchyRepository;
import io.security.core.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    @Override
    @Transactional
    public String findAllHierarchy() {

        List<RoleHierarchy> roleHierarchies = roleHierarchyRepository.findAll();
        StringBuilder sb = new StringBuilder();

        for (RoleHierarchy roleHierarchy : roleHierarchies) {
            if (roleHierarchy.getParent() != null) {
                sb.append(roleHierarchy.getParent().getName());
                sb.append(" > ");
                sb.append(roleHierarchy.getName());
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
