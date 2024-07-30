package com.ecommerce.ecommerce.Service;

import java.util.*;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Model.Review;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Request.ReviewRequest;

public interface ReviewService {
    public Review createRating(ReviewRequest req, User userId)throws ProductException;
    public List<Review>getAllReviews(Long productid);
}
