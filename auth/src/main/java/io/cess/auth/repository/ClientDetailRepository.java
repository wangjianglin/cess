package io.cess.auth.repository;

import io.cess.auth.entity.ClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

/**
 * @author lin
 * @date 28/06/2017.
 */
public interface ClientDetailRepository extends Repository<ClientDetail, String> {


    ClientDetail findByClientId(String id);
}
