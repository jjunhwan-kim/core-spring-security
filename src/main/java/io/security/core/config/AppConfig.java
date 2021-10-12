package io.security.core.config;

import io.security.core.repository.AccessIpRepository;
import io.security.core.repository.ResourceRepository;
import io.security.core.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SecurityResourceService securityResourceService(ResourceRepository resourceRepository, AccessIpRepository accessIpRepository) {
        return new SecurityResourceService(resourceRepository, accessIpRepository);
    }
}
