package io.security.core.controller.user;

import io.security.core.domain.entity.Account;
import io.security.core.domain.dto.AccountDto;
import io.security.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal Account account, Authentication authentication, Principal principal) {
        return "user/mypage";
    }

    @GetMapping("/order")
    public String order() {
        userService.order();
        return "user/mypage";
    }

    @GetMapping("/users")
    public String createUser() {
        return "user/login/register";
    }

    @PostMapping("/users")
    public String createUser(AccountDto accountDto) {

        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        userService.createUser(account);

        return "redirect:/";
    }
}
