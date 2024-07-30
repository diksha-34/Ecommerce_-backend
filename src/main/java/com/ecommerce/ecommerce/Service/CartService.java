package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Model.Cart;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Request.addItemRequest;

public interface CartService {
    public Cart createCart(User user);
    public String addCartItem(Long userId, addItemRequest req) throws ProductException;
    public Cart findUserCart(Long userId);
}
