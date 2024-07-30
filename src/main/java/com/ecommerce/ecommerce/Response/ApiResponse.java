package com.ecommerce.ecommerce.Response;

public class ApiResponse {
    private String message;
    private boolean status;
    public ApiResponse() {
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
