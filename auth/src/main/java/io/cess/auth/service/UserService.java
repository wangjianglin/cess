package io.cess.auth.service;

import io.cess.auth.entity.User;

/**
 * @author lin
 * @date 28/06/2017.
 */
public interface UserService {
    User loginUser(String username);

    User findById(long l);
}
