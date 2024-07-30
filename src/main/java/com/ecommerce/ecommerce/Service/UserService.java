package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Exception.UserException;
import com.ecommerce.ecommerce.Model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;
    public User findUserProfileByJwt(String jwt) throws UserException;
}
