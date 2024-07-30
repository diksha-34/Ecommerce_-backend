package com.ecommerce.ecommerce.Service;

import java.util.List;
import java.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Model.Product;
import com.ecommerce.ecommerce.Model.Review;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Repository.ReviewRepository;
import com.ecommerce.ecommerce.Request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductService productService;

    @Override
    public Review createRating(ReviewRequest req, User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());
        Review review=new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews(Long productid) {
        return reviewRepository.getAllProductReviews(productid);
    }
    
}
