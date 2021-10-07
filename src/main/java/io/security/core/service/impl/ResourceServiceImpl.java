package io.security.core.service.impl;

import io.security.core.domain.entity.Resource;
import io.security.core.repository.ResourceRepository;
import io.security.core.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Override
    @Transactional
    public void createResource(Resource resource) {
        resourceRepository.save(resource);
    }

    @Override
    public Resource getResource(Long id) {
        return resourceRepository.findById(id).orElse(new Resource());
    }

    @Override
    public List<Resource> getResources() {
        return resourceRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Override
    @Transactional
    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }
}
