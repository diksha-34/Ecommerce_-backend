package com.ecommerce.ecommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.Model.OrderItem;
import com.ecommerce.ecommerce.Repository.OrderItemRepository;

@Service
public class OrderItemServiceImplementation implements OrderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public OrderItem creaOrderItem(OrderItem orderItem) {
       return orderItemRepository.save(orderItem);
    }
    
}
