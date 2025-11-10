package com.tns.onlineshopping.services;

import com.tns.onlineshopping.entities.Admin;
import com.tns.onlineshopping.entities.Product;
import com.tns.onlineshopping.entities.ProductCategory;

import java.util.*;  // Import all utility classes

public class AdminService {
    private final Admin admin;
    private final ProductService productService;
    private final OrderService orderService;

    public AdminService(Admin admin, ProductService productService, OrderService orderService) {
        this.admin = admin;
        this.productService = productService;
        this.orderService = orderService;
    }

    // ✅ Add product
    public void addProduct(Product product) {
        productService.addProduct(product);
    }

    // ✅ Update product
    public void updateProduct(Product product) {
        productService.updateProduct(product);
    }

    // ✅ Remove product
    public void removeProduct(String productId) {
        productService.removeProduct(productId);
    }

    public ProductService getProductService() {
        return productService;
    }

    // ✅ Dashboard Metrics — works when required methods exist in ProductService and OrderService
    public Map<String, Object> getDashboardMetrics() {
        Map<String, Object> metrics = new LinkedHashMap<>();

        List<Product> products = productService.listAllProducts();
        List<?> orders = orderService.listAllOrders();

        metrics.put("totalProducts", products.size());
        metrics.put("totalOrders", orders.size());
        metrics.put("totalRevenue", orderService.getTotalRevenue());

        // Calculate top-selling category
        Map<ProductCategory, Integer> categoryCount = new HashMap<>();
        for (Product p : products) {
            ProductCategory category = p.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        ProductCategory topCategory = categoryCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        metrics.put("topCategory", topCategory);
        return metrics;
    }
}
