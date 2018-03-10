package io.cess.cloud.oauth2;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

/**
 * @author lin
 * @date 28/06/2017
 */
public interface OAuth2ClientDetailsService {

    ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException;

}
