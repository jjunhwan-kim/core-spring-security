package io.security.core.service;

import io.security.core.domain.entity.Resource;
import io.security.core.repository.AccessIpRepository;
import io.security.core.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SecurityResourceService {

    private final ResourceRepository resourceRepository;
    private final AccessIpRepository accessIpRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();

        List<Resource> resources = resourceRepository.findAllResource();
        resources.forEach(resource -> {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            resource.getRoles().forEach(role -> {
                configAttributes.add(new SecurityConfig(role.getRoleName()));
            });
            result.put(new AntPathRequestMatcher(resource.getResourceName()), configAttributes);
        });

        return result;
    }

    public List<String> getAccessIpAddresses() {
        List<String> accessIpAddresses = accessIpRepository.findAll().stream()
                .map(accessIp -> accessIp.getIpAddress())
                .collect(Collectors.toList());
        return accessIpAddresses;
    }
}
