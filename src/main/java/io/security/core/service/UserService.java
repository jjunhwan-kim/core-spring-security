package io.security.core.service;

import io.security.core.domain.dto.AccountDto;
import io.security.core.domain.entity.Account;

import java.util.List;

public interface UserService {

    void createUser(Account account);
    List<Account> getUsers();
    AccountDto getUser(Long id);
    void modifyUser(AccountDto accountDto);
    void deleteUser(Long id);
    void order();
}
