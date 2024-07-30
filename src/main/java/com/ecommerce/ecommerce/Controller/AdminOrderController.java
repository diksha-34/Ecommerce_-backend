package com.ecommerce.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.Exception.OrderException;
import com.ecommerce.ecommerce.Model.Order;
import com.ecommerce.ecommerce.Response.ApiResponse;
import com.ecommerce.ecommerce.Service.OrderService;
import java.util.*;
@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    public OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>>getAllOrdersHandler(){
        List<Order> orders=orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
    }
    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order>confirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order orders=orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order>shippedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order orders=orderService.shippedOrder(orderId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order>deliveredOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order orders=orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order>cancelledOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order orders=orderService.canceledOrder(orderId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse>deleteOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteOrder(orderId);
        ApiResponse res=new ApiResponse();
        res.setMessage("Order deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
