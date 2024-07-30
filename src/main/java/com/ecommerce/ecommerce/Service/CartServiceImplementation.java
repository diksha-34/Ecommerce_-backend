package com.ecommerce.ecommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Model.Cart;
import com.ecommerce.ecommerce.Model.CartItem;
import com.ecommerce.ecommerce.Model.Product;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Repository.CartRepository;
import com.ecommerce.ecommerce.Request.addItemRequest;

@Service
public class CartServiceImplementation implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public Cart createCart(User user) {
        Cart cart=new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, addItemRequest req) throws ProductException {
        Cart cart=cartRepository.findByUserId(userId);
        Product product=productService.findProductById(req.getProductId());
        CartItem isPresent=cartItemService.isCartExist(cart, product, req.getSize(), userId);
        if(isPresent==null){
            CartItem cartItem=new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            int price=req.getQuantity()*product.getDiscountPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem=cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item Add to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart=cartRepository.findByUserId(userId);
        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;
        for(CartItem cartItem:cart.getCartItems()){
            totalPrice=totalPrice+ cartItem.getPrice();
            totalDiscountedPrice=totalDiscountedPrice+ cartItem.getDiscountedPrice();
            totalItem=totalItem+ cartItem.getQuantity();
        }
        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice-totalDiscountedPrice);
        return cartRepository.save(cart);
    }
    
}
