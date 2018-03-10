package io.cess.auth.repository;

import io.cess.auth.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author lin
 * @date 28/06/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    User findByMobile(String mobile);
    User findByUsername(String username);
}
