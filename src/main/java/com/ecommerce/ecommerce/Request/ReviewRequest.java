package com.ecommerce.ecommerce.Request;

public class ReviewRequest {
    private Long ProductId;
    private String review;
    public ReviewRequest() {
    }
    public Long getProductId() {
        return ProductId;
    }
    public void setProductId(Long productId) {
        ProductId = productId;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
    
}
