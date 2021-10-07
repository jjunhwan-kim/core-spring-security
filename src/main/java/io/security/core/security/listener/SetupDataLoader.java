package io.security.core.security.listener;

import io.security.core.domain.entity.Account;
import io.security.core.domain.entity.Resource;
import io.security.core.domain.entity.Role;
import io.security.core.repository.ResourceRepository;
import io.security.core.repository.RoleRepository;
import io.security.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ResourceRepository resourcesRepository;
    private final PasswordEncoder passwordEncoder;
    private static AtomicInteger count = new AtomicInteger(0);

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        setupSecurityResources();

        alreadySetup = true;
    }

    private void setupSecurityResources() {
        Set<Role> userRoles = new HashSet<>();
        Role userRole = createRoleIfNotFound("ROLE_USER", "사용자");
        userRoles.add(userRole);
        createUserIfNotFound("user", "pass", "user@gmail.com", 20, userRoles);

        Set<Role> managerRoles = new HashSet<>();
        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저");
        managerRoles.add(userRole);
        managerRoles.add(managerRole);
        createUserIfNotFound("manager", "pass", "manager@gmail.com", 20, managerRoles);

        Set<Role> adminRoles = new HashSet<>();
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        adminRoles.add(userRole);
        adminRoles.add(managerRole);
        adminRoles.add(adminRole);
        createUserIfNotFound("admin", "pass", "admin@gmail.com", 20, adminRoles);

        createResourceIfNotFound("/admin/**", "", adminRoles, "url");
    }

    @Transactional
    public Role createRoleIfNotFound(String roleName, String roleDesc) {

        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            role = Role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
        }
        return roleRepository.save(role);
    }

    @Transactional
    public Account createUserIfNotFound(String username, String password, String email, int age, Set<Role> roles) {

        Account account = userRepository.findByUsername(username);

        if (account == null) {
            account = Account.builder()
                    .username(username)
                    .email(email)
                    .age(age)
                    .password(passwordEncoder.encode(password))
                    .roles(roles)
                    .build();
        }
        return userRepository.save(account);
    }

    @Transactional
    public Resource createResourceIfNotFound(String resourceName, String httpMethod, Set<Role> roles, String resourceType) {

        Resource resources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);

        if (resources == null) {
            resources = Resource.builder()
                    .resourceName(resourceName)
                    .roles(roles)
                    .httpMethod(httpMethod)
                    .resourceType(resourceType)
                    .build();
        }
        return resourcesRepository.save(resources);
    }
}