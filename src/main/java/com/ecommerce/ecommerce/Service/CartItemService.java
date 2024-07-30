package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Exception.CartItemException;
import com.ecommerce.ecommerce.Exception.UserException;
import com.ecommerce.ecommerce.Model.Cart;
import com.ecommerce.ecommerce.Model.CartItem;
import com.ecommerce.ecommerce.Model.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem)throws CartItemException, UserException;
    public CartItem isCartExist(Cart cart, Product product, String size, Long userId);
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
    public CartItem findCartItemById(Long cartItemId)throws CartItemException;

}
