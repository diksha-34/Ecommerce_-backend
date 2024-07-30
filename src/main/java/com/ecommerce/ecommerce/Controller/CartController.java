package com.ecommerce.ecommerce.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Exception.UserException;
import com.ecommerce.ecommerce.Model.Cart;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Request.addItemRequest;
import com.ecommerce.ecommerce.Response.ApiResponse;
import com.ecommerce.ecommerce.Service.CartService;
import com.ecommerce.ecommerce.Service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart>findUserCart(@RequestHeader("Authorization") String jwt)throws UserException{
        User user=userService.findUserProfileByJwt(jwt);
        Cart cart=cartService.findUserCart(user.getId());
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }
    @PutMapping("/add")
    public ResponseEntity<ApiResponse>addItemToCart(@RequestBody addItemRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        User user=userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);
        ApiResponse res=new ApiResponse();
        res.setMessage("Item added to cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
