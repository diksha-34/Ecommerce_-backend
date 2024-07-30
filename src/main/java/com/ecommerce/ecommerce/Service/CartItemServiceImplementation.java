package com.ecommerce.ecommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import com.ecommerce.ecommerce.Exception.CartItemException;
import com.ecommerce.ecommerce.Exception.UserException;
import com.ecommerce.ecommerce.Model.Cart;
import com.ecommerce.ecommerce.Model.CartItem;
import com.ecommerce.ecommerce.Model.Product;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Repository.CartItemRepository;
import com.ecommerce.ecommerce.Repository.CartRepository;

@Service
public class CartItemServiceImplementation  implements CartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Override
    public CartItem createCartItem(CartItem cartItem) {
       cartItem.setQuantity(1);
       cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
       cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountPercent()*cartItem.getQuantity());
       CartItem createdCartItem=cartItemRepository.save(cartItem);
       return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item= findCartItemById(id);
        User user=userService.findUserById(item.getUserId());
        if(user.getId().equals(userId)){
            item.setQuantity(item.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountPercent()*item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem=cartItemRepository.isCartExists(cart, product, size, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem item= findCartItemById(cartItemId);
        User user=userService.findUserById(item.getUserId());
        User reqUser=userService.findUserById(userId);
        if(user.getId().equals(reqUser.getId())){
            cartItemRepository.deleteById(cartItemId);
        }
        else{
            throw new UserException("you can't remove another user's item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem>opt=cartItemRepository.findById(cartItemId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("cartItem not found with id - " + cartItemId);
    }
    
}
