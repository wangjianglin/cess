package io.cess.auth.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author lin
 * @date 28/06/2017.
 */
public interface PermissionService {
    Collection<? extends GrantedAuthority> findAuthorityByScopes(Collection<String> scopes);

    Collection<? extends GrantedAuthority> getAuthorityByUserId(long userId);
}
