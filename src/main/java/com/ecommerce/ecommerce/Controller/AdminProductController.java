package com.ecommerce.ecommerce.Controller;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Model.Product;
import com.ecommerce.ecommerce.Request.CreateProductRequest;
import com.ecommerce.ecommerce.Response.ApiResponse;
import com.ecommerce.ecommerce.Service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    @Autowired
    public ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product>createProduct(@RequestBody CreateProductRequest req){
        Product product=productService.createProduct(req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }
    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse>deleteProduct(@PathVariable Long productId)throws ProductException{
        productService.deleteProduct(productId);
        ApiResponse res=new ApiResponse();
        res.setMessage("Product deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Product>>getAllProduct(){
        List<Product> products=productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @PutMapping("/{productId}/update")
    public ResponseEntity<Product>updateProduct(@RequestBody Product req, @PathVariable Long productId) throws ProductException{
        Product product=productService.updateProduct(productId, req);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }
    @PostMapping("/creates")
    public ResponseEntity<ApiResponse>createMultipleProducts(@RequestBody CreateProductRequest[] req){
        for(CreateProductRequest product:req){
            productService.createProduct(product);
        }
        ApiResponse res=new ApiResponse();
        res.setMessage("Product created successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
