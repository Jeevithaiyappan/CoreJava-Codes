package com.tns.onlineshopping.services;

import com.tns.onlineshopping.entities.Customer;
import com.tns.onlineshopping.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final List<Customer> customers = new ArrayList<>();
    private final ProductService productService;
    private final OrderService orderService;

    public CustomerService(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    public void registerCustomer(Customer customer) {
        customers.add(customer);
    }

    public Optional<Customer> findByUsername(String username) {
        return customers.stream().filter(c -> c.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    public List<Product> browseAllProducts() {
        return productService.listAllProducts();
    }

    public ProductService getProductService() { return productService; }

    public OrderService getOrderService() { return orderService; }
    
}
