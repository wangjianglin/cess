package io.cess.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.ArrayList;
import java.util.List;

public class OAuthHelper {

//    @Autowired
    private DefaultTokenServices tokenservice = new DefaultTokenServices();

    @Autowired(required = false)
    private TokenEnhancer tokenEnhancer;

    public String getAuthorization(AuthInfo authInfo) {

//        return mockRequest -> {
        // Create OAuth2 token
        OAuth2Request oauth2Request = new OAuth2Request(null, "ClientService.OAUTH_CLIENT_ID", null, true, null, null, null, null, null);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (authInfo.getAuthorities() != null) {
            for (String authority : authInfo.getAuthorities()) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        if (authInfo.getRoles() != null) {
            for (String role : authInfo.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }


        Authentication userauth = new TestingAuthenticationToken(authInfo.getUsername(), null, authorities);
        OAuth2Authentication oauth2auth = new OAuth2Authentication(oauth2Request, userauth);
        OAuth2AccessToken token = tokenservice.createAccessToken(oauth2auth);

        if(tokenEnhancer != null){
            token = tokenEnhancer.enhance(token,oauth2auth);
        }

        return "Bearer " + token.getValue();

        // Set Authorization header to use Bearer
//            mockRequest.addHeader("Authorization", "Bearer " + token.getValue());
//            return mockRequest;
//        return null;
//        };
    }

    @Autowired
    public void setTokenStore(TokenStore tokenStore) {
        tokenservice.setTokenStore(tokenStore);
    }
}
