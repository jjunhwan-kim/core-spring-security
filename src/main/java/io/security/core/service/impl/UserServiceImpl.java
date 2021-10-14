package io.security.core.service.impl;

import io.security.core.domain.dto.AccountDto;
import io.security.core.domain.entity.Account;
import io.security.core.domain.entity.Role;
import io.security.core.repository.RoleRepository;
import io.security.core.repository.UserRepository;
import io.security.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    @Transactional
    public void createUser(Account account) {
        userRepository.save(account);
    }

    @Override
    public AccountDto getUser(Long id) {

        Account account = userRepository.findById(id).orElse(new Account());
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        List<String> roles = account.getRoles()
                .stream()
                .map(role -> role.getRoleName())
                .collect(Collectors.toList());

        accountDto.setRoles(roles);

        return accountDto;
    }

    @Override
    public List<Account> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void modifyUser(AccountDto accountDto) {

        Account account = modelMapper.map(accountDto, Account.class);

        if (accountDto.getRoles() != null) {
            Set<Role> roles = new HashSet<>();
            accountDto.getRoles().forEach(role -> {
                Role r = roleRepository.findByRoleName(role);
                roles.add(r);
            });
            account.setRoles(roles);
        }
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        userRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Secured("ROLE_MANAGER")
    public void order() {
        System.out.println("UserServiceImpl.order");
    }
}
