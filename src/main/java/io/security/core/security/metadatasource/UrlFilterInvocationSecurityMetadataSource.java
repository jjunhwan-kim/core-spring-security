package io.security.core.security.metadatasource;

import io.security.core.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Map<RequestMatcher, List<ConfigAttribute>> requestMap;
    private final SecurityResourceService securityResourceService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        if (requestMap != null) {
            for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
                RequestMatcher matcher = entry.getKey();
                if (matcher.matches(request)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        this.requestMap.values().forEach(allAttributes::addAll);
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void reload() {

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> reloadedMap = securityResourceService.getResourceList();

        requestMap.clear();

        for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : reloadedMap.entrySet()) {
            requestMap.put(entry.getKey(), entry.getValue());
        }
    }
}
