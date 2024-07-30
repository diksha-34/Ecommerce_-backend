package com.ecommerce.ecommerce.Service;

import java.util.*;
import java.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Model.Product;
import com.ecommerce.ecommerce.Model.Rating;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Repository.RatingRepository;
import com.ecommerce.ecommerce.Request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService{

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductService productService;
    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());
        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRatings(Long productid) {
        return ratingRepository.getAllProductRatings(productid);
    }
    
}
