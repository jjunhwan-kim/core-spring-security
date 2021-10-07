package io.security.core.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private int age;
    private List<String> roles;
}
