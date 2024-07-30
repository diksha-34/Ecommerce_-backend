package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Model.Rating;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Request.RatingRequest;

import java.util.*;
public interface RatingService {
    public Rating createRating(RatingRequest req, User userId)throws ProductException;
    public List<Rating>getProductRatings(Long productid);
}
