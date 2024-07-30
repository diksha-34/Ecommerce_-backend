package com.ecommerce.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.ecommerce.Model.Rating;
import java.util.*;
public interface RatingRepository extends JpaRepository<Rating, Long>{
    @Query("SELECT r FROM Rating r WHERE r.product.id=:productId")
    public List<Rating>getAllProductRatings(@Param("productId") Long productId);
}
