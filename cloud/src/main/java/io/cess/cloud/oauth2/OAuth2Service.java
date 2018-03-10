package io.cess.cloud.oauth2;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * @author lin
 * @date 28/06/2017.
 */
public interface OAuth2Service {
    Collection<GrantedAuthority> authorityFilter(Collection<? extends GrantedAuthority> authorities, Set<String> scopes);
}
