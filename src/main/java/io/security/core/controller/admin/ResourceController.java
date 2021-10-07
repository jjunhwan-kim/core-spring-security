package io.security.core.controller.admin;

import io.security.core.domain.dto.ResourceDto;
import io.security.core.domain.entity.Resource;
import io.security.core.domain.entity.Role;
import io.security.core.repository.RoleRepository;
import io.security.core.service.ResourceService;
import io.security.core.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class ResourceController {

    private final ResourceService resourceService;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/admin/resources")
    public String getResources(Model model) {

        List<Resource> resources = resourceService.getResources();
        model.addAttribute("resources", resources);

        return "/admin/resource/list";
    }

    @PostMapping("/admin/resources")
    public String createResource(ResourceDto resourceDto) {

        Role role = roleRepository.findByRoleName(resourceDto.getRoleName());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Resource resource = modelMapper.map(resourceDto, Resource.class);
        resource.setRoles(roles);

        resourceService.createResource(resource);

        return "redirect:/admin/resources";
    }

    @GetMapping("/admin/resources/register")
    public String ViewResource(Model model) {

        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);

        ResourceDto resourceDto = new ResourceDto();
        Set<Role> roles = new HashSet<>();
        roles.add(new Role());
        resourceDto.setRoles(roles);
        model.addAttribute("resource", resourceDto);

        return "admin/resource/detail";
    }

    @GetMapping("/admin/resources/{id}")
    public String getResource(@PathVariable Long id, Model model) {

        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);
        Resource resource = resourceService.getResource(id);
        ResourceDto resourceDto = modelMapper.map(resource, ResourceDto.class);
        model.addAttribute("resource", resourceDto);

        return "admin/resource/detail";
    }

    @GetMapping("/admin/resources/delete/{id}")
    public String removeResource(@PathVariable Long id, Model model) {

        resourceService.deleteResource(id);

        return "redirect:/admin/resources";
    }
}
