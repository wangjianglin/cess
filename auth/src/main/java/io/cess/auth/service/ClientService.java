package io.cess.auth.service;

import io.cess.auth.entity.ClientDetail;

/**
 * @author lin
 * @date 28/06/2017.
 */
public interface ClientService {

    ClientDetail loadClientByClientId(String clientId);
}
