package com.ecommerce.ecommerce.Request;

public class RatingRequest {
    private Long ProductId;
    private double rating;
    public RatingRequest() {
    }
    public Long getProductId() {
        return ProductId;
    }
    public void setProductId(Long productId) {
        ProductId = productId;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    
}
