package com.ecommerce.ecommerce.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.Exception.ProductException;
import com.ecommerce.ecommerce.Model.Category;
import com.ecommerce.ecommerce.Model.Product;
import com.ecommerce.ecommerce.Repository.CategoryRepository;
import com.ecommerce.ecommerce.Repository.ProductRepository;
import com.ecommerce.ecommerce.Request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topCategory = categoryRepository.findByName(req.getTopLevelCategory());
        if (topCategory == null) {
            Category topLavelcategory = new Category();
            topLavelcategory.setName(req.getTopLevelCategory());
            topLavelcategory.setLevel(1);

            topCategory = categoryRepository.save(topLavelcategory);
        }
        Category secondCategory = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),
                topCategory.getName());
        if (secondCategory == null) {
            Category secondLavelcategory = new Category();
            secondLavelcategory.setName(req.getSecondLevelCategory());
            secondLavelcategory.setParentCategory(topCategory);
            secondLavelcategory.setLevel(2);

            secondCategory = categoryRepository.save(secondLavelcategory);
        }
        Category thirdCategory = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),
                secondCategory.getName());
        if (thirdCategory == null) {
            Category thirdLavelcategory = new Category();
            thirdLavelcategory.setName(req.getThirdLevelCategory());
            thirdLavelcategory.setParentCategory(secondCategory);
            thirdLavelcategory.setLevel(3);

            thirdCategory = categoryRepository.save(thirdLavelcategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setDiscountPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setQuantity(req.getQuantity());
        product.setBrand(req.getBrand());
        product.setColor(req.getColor());
        product.setSizes(req.getSize());
        product.setImageUrl(req.getImageUrl());
        product.setCategory(thirdCategory);
        product.setCreatedAt(LocalDateTime.now());

        Product saveProduct = productRepository.save(product);
        return saveProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product Deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);
        if (product.getQuantity() != 0) {
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new ProductException("Product not found with this id - " + id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {

        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> size, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
        if (!colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }
        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }

        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProduct = new PageImpl<>(pageContent, pageable, products.size());
        return filteredProduct;
    }
    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

}
