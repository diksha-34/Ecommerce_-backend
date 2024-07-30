package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Exception.OrderException;
import com.ecommerce.ecommerce.Model.Address;
import com.ecommerce.ecommerce.Model.Order;
import com.ecommerce.ecommerce.Model.User;
import java.util.*;
public interface OrderService {
    public Order createOrder(User user, Address shippingAddress);
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order>userOrderHistory(Long userId);
    public Order placedOrder(Long orderId) throws OrderException;
    public Order confirmedOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long orderId) throws OrderException;
    public Order canceledOrder(Long orderId) throws OrderException;
    public List<Order>getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;
}
