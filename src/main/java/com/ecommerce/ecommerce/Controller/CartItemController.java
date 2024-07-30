package com.ecommerce.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.Exception.CartItemException;
import com.ecommerce.ecommerce.Exception.UserException;
import com.ecommerce.ecommerce.Model.CartItem;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Response.ApiResponse;
import com.ecommerce.ecommerce.Service.CartItemService;
import com.ecommerce.ecommerce.Service.UserService;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        User user=userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse res=new ApiResponse();
        res.setMessage("Item deleted from the cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItem(@RequestBody CartItem cartItem, @PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        User user=userService.findUserProfileByJwt(jwt);
        CartItem updateCartItem=cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        
        return new ResponseEntity<>(updateCartItem, HttpStatus.OK);
    }
}
