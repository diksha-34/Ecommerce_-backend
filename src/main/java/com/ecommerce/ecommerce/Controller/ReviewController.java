package com.ecommerce.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Exception.UserException;
import com.ecommerce.ecommerce.Model.Review;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Request.ReviewRequest;
import com.ecommerce.ecommerce.Service.ReviewService;
import com.ecommerce.ecommerce.Service.UserService;

import java.util.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    public UserService userService;

    @Autowired
    public ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review>createReview(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt)throws UserException, ProductException{
        User user=userService.findUserProfileByJwt(jwt);
        Review review=reviewService.createRating(req, user);
        return new ResponseEntity<Review>(review, HttpStatus.CREATED);
    }
    @PostMapping("/product/{productId}")
    public ResponseEntity<List<Review>>getProductReview(@PathVariable Long productId)throws UserException, ProductException{
        List<Review> reviews=reviewService.getAllReviews(productId);
        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }
}
