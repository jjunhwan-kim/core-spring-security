package io.security.core.service;

import io.security.core.domain.entity.Resource;

import java.util.List;

public interface ResourceService {

    void createResource(Resource resource);
    Resource getResource(Long id);
    List<Resource> getResources();
    void deleteResource(Long id);
}
