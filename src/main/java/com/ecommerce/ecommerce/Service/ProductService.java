package com.ecommerce.ecommerce.Service;

import java.util.*;

import org.springframework.data.domain.Page;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Model.Product;
import com.ecommerce.ecommerce.Request.CreateProductRequest;

public interface ProductService {
    public Product createProduct(CreateProductRequest req);
    public String deleteProduct(Long productId)throws ProductException;
    public Product updateProduct(Long productId, Product req)throws ProductException;
    public Product findProductById(Long id)throws ProductException;
    public List<Product> findProductByCategory(String category);
    public Page<Product>getAllProduct(String category, List<String>colors, List<String>size, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);
    public List<Product> findAllProducts();
}
