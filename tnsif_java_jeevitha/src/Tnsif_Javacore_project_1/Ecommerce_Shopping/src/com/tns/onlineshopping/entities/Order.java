package com.tns.onlineshopping.entities;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final AtomicInteger COUNTER = new AtomicInteger(1000);

    private final String orderId;
    private final Customer customer;
    private final Map<Product, Integer> items = new LinkedHashMap<>();
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private double subtotal;         // ✅ added
    private double discountAmount;
    private double finalAmount;
    private String trackingId;

    public Order(Customer customer, Map<Product, Integer> items,
                 double subtotal, double discountAmount, double finalAmount) { // ✅ updated constructor
        this.orderId = "ORD" + COUNTER.getAndIncrement();
        this.customer = customer;
        if (items != null) this.items.putAll(items);
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.subtotal = subtotal;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.trackingId = "TRK" + (100000 + (int)(Math.random() * 900000));
    }

    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Map<Product, Integer> getItems() { return Collections.unmodifiableMap(items); }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public double getSubtotal() { return subtotal; }
    public double getDiscountAmount() { return discountAmount; }
    public double getFinalAmount() { return finalAmount; }
    public String getTrackingId() { return trackingId; }

    public String generateInvoice() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== ORDER INVOICE ==========\n");
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Customer: ").append(customer.getUsername()).append(" | ").append(customer.getEmail()).append("\n");
        sb.append("Order Date: ").append(createdAt).append("\n");
        sb.append("-----------------------------------\n");
        for (Map.Entry<Product,Integer> e : items.entrySet()) {
            sb.append(String.format("%s (%s) x %d = ₹%.2f\n",
                    e.getKey().getName(),
                    e.getKey().getProductId(),
                    e.getValue(),
                    e.getKey().getPrice() * e.getValue()));
        }
        sb.append("-----------------------------------\n");
        sb.append(String.format("Subtotal: ₹%.2f\n", subtotal));
        sb.append(String.format("Discount: -₹%.2f\n", discountAmount));
        sb.append(String.format("Total Payable: ₹%.2f\n", finalAmount));
        sb.append("Status: ").append(status).append("\n");
        sb.append("Tracking ID: ").append(trackingId).append("\n");
        sb.append("===================================\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Order %s | Customer: %s | Status: %s | Subtotal: ₹%.2f | Final: ₹%.2f | Created: %s\n",
                orderId, customer.getUsername(), status, subtotal, finalAmount, createdAt));
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            sb.append(String.format("  - %s x %d = ₹%.2f\n",
                    e.getKey().getName(),
                    e.getValue(),
                    e.getKey().getPrice() * e.getValue()));
        }
        return sb.toString();
    }

    public enum OrderStatus {
        PENDING, COMPLETED, DELIVERED, CANCELLED
    }
}
