package com.ecommerce.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Exception.UserException;
import com.ecommerce.ecommerce.Model.Rating;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Request.RatingRequest;
import com.ecommerce.ecommerce.Service.RatingService;
import com.ecommerce.ecommerce.Service.UserService;

import java.util.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    public UserService userService;

    @Autowired
    public RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating>createRating(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt)throws UserException, ProductException{
        User user=userService.findUserProfileByJwt(jwt);
        Rating rating=ratingService.createRating(req, user);
        return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
    }
    @PostMapping("/product/{productId}")
    public ResponseEntity<List<Rating>>getProductRating(@PathVariable Long productId, @RequestBody RatingRequest req)throws UserException, ProductException{
        List<Rating> rating=ratingService.getProductRatings(productId);
        return new ResponseEntity<>(rating, HttpStatus.ACCEPTED);
    }
}
