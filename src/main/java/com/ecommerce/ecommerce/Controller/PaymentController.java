package com.ecommerce.ecommerce.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.Exception.OrderException;
import com.ecommerce.ecommerce.Model.Order;
import com.ecommerce.ecommerce.Repository.OrderRepository;
import com.ecommerce.ecommerce.Response.ApiResponse;
import com.ecommerce.ecommerce.Response.PaymentLinkResponse;
import com.ecommerce.ecommerce.Service.OrderService;
import com.ecommerce.ecommerce.Service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Value("${razorpay.api.key}")
    String apiKey;

    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse>createPaymentLink(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.findOrderById(orderId);
        try {
            RazorpayClient razorpayClient=new RazorpayClient(apiKey, apiSecret);
            JSONObject paymentLinkRequest=new JSONObject();
            paymentLinkRequest.put("amount", order.getTotalPrice()*100);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer=new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("name", order.getUser().getEmail());
            paymentLinkRequest.put("customer", customer);

            JSONObject notify=new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/"+order.getOrderId());

            PaymentLink payment= razorpayClient.paymentLink.create(paymentLinkRequest);
        
            String paymentLinkId=payment.get("id");
            String paymentLinkUrl=payment.get("short_url");

            PaymentLinkResponse res=new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);
            return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.CREATED);
        
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentLinkResponse());
        }
    }
    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name="payment_id") String payment_id, @RequestParam(name="order_id") Long order_id)throws OrderException, RazorpayException{
        Order order=orderService.findOrderById(order_id);
        RazorpayClient razorpay=new RazorpayClient(apiKey, apiSecret);

        try {
            Payment payment=razorpay.payments.fetch(payment_id);
            if(payment.get("status").equals("captured")){
                order.getPaymentDetails().setPaymentId(payment_id);
                order.getPaymentDetails().setStatus("COMPLETED");
                order.setOrderStatus("PLACED");
                orderRepository.save(order);
            }
            ApiResponse res=new ApiResponse();
            res.setMessage("Your order get placed");
            res.setStatus(true);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }
}
