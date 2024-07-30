package com.ecommerce.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.ecommerce.ecommerce.Exception.OrderException;
import com.ecommerce.ecommerce.Exception.UserException;
import com.ecommerce.ecommerce.Model.Address;
import com.ecommerce.ecommerce.Model.Order;
import com.ecommerce.ecommerce.Model.User;
import com.ecommerce.ecommerce.Service.OrderService;
import com.ecommerce.ecommerce.Service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    public OrderService orderService;

    @Autowired
    public UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order>createOrder(@RequestBody Address shippedAddress, @RequestHeader("Authorization") String jwt)throws UserException{
        User user=userService.findUserProfileByJwt(jwt);
        Order order=orderService.createOrder(user, shippedAddress);
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
    @GetMapping("/user")
    public ResponseEntity<List<Order>>userOrderHistory(@RequestHeader("Authorization") String jwt)throws UserException{
        User user=userService.findUserProfileByJwt(jwt);
        List<Order> orders=orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }
    @GetMapping("/{Id}")
    public ResponseEntity<Order>findOrderById(@PathVariable("Id") Long Id)throws UserException, OrderException{
        Order order=orderService.findOrderById(Id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
