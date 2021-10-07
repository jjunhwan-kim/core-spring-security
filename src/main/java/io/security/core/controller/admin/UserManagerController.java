package io.security.core.controller.admin;

import io.security.core.domain.dto.AccountDto;
import io.security.core.domain.entity.Account;
import io.security.core.domain.entity.Role;
import io.security.core.service.RoleService;
import io.security.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserManagerController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/admin/accounts")
    public String getUsers(Model model) {

        List<Account> accounts = userService.getUsers();
        model.addAttribute("accounts", accounts);

        return "admin/user/list";
    }

    @PostMapping("/admin/accounts")
    public String modifyUser(AccountDto accountDto) {

        userService.modifyUser(accountDto);

        return "redirect:/admin/accounts";
    }

    @GetMapping("/admin/accounts/{id}")
    public String getUser(@PathVariable Long id, Model model) {

        AccountDto accountDto = userService.getUser(id);
        List<Role> roles = roleService.getRoles();

        model.addAttribute("account", accountDto);
        model.addAttribute("roleList", roles);

        return "admin/user/detail";
    }

    @GetMapping("/admin/accounts/delete/{id}")
    public String removeUser(@PathVariable Long id, Model model) {

        userService.deleteUser(id);

        return "redirect:/admin/accounts";
    }
}
