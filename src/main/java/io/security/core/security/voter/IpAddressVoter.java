package io.security.core.security.voter;

import io.security.core.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class IpAddressVoter implements AccessDecisionVoter<Object> {

    private final SecurityResourceService securityResourceService;

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();

        List<String> accessIpAddresses = securityResourceService.getAccessIpAddresses();

        for (String accessIpAddress : accessIpAddresses) {
            if (remoteAddress.equals(accessIpAddress)) {
                // AffirmativeBased 구현체는 ACCESS_GRANTED 가 하나라도 반환되면 인가처리가 됨
                // IpAddress 가 일치한 이후에 다음 Voter 가 인가 승인을 검사해야 하므로 ACCESS_ABSTAIN 를 반환하여 다음 Voter 가 동작하도록 함
                return ACCESS_ABSTAIN;
            }
        }

        throw new AccessDeniedException("Invalid IpAddress");
    }
}
