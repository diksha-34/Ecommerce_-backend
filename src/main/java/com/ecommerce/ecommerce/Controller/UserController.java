package com.ecommerce.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.Exception.UserException;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User>getUserProfileHandler(@RequestHeader("Authorization") String jwt)throws UserException{
        User user=userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }
}
