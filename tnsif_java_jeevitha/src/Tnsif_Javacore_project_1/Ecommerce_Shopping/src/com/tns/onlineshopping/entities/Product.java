package com.tns.onlineshopping.entities;

import java.util.Objects;

public class Product {
    private final String productId;
    private String name;
    private double price;
    private int stockQuantity;
    private ProductCategory category;

    public Product(String productId, String name, double price, int stockQuantity, ProductCategory category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }

    public boolean isInStock(int quantity) {
        return stockQuantity >= quantity;
    }

    public void reduceStock(int quantity) {
        if (quantity < 0) return;
        if (quantity > stockQuantity) throw new IllegalArgumentException("Not enough stock");
        stockQuantity -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity < 0) return;
        stockQuantity += quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | ₹%.2f | Stock: %d | %s", productId, name, price, stockQuantity, category);
    }
}
