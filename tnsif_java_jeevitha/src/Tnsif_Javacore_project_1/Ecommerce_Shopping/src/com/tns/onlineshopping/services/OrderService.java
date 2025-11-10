package com.tns.onlineshopping.services;

import com.tns.onlineshopping.entities.Order;
import com.tns.onlineshopping.entities.Product;
import com.tns.onlineshopping.entities.Customer;
import com.tns.onlineshopping.utils.DiscountUtil;


import java.util.*; // ✅ Fixes Map, List, LinkedHashMap, Collections, Optional

public class OrderService {
    private final List<Order> orders = new ArrayList<>();
    private final ProductService productService;
    private double totalRevenue = 0.0; // for admin dashboard

    public OrderService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Places order, applies discounts (category + coupon), awards reward points to customer.
     * couponCode can be null or empty.
     */
    public Order placeOrder(Customer customer, String couponCode, int rewardPointsToRedeem) {
        Map<Product, Integer> items = new LinkedHashMap<>(customer.getShoppingCart().getItems());
        if (items.isEmpty()) throw new IllegalStateException("Cart is empty.");

        // check stock
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            Product p = productService.findById(e.getKey().getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + e.getKey().getProductId()));
            if (!p.isInStock(e.getValue()))
                throw new IllegalStateException("Not enough stock for " + p.getName());
        }

        // compute subtotal
        double subtotal = 0.0;
        for (Map.Entry<Product, Integer> e : items.entrySet())
            subtotal += e.getKey().getPrice() * e.getValue();

        // apply discounts
        double discountFromRules = DiscountUtil.calculateCategoryDiscountAmount(items);
        double couponDisc = DiscountUtil.applyCoupon(couponCode, subtotal);
        double discountAmount = discountFromRules + couponDisc;

        // reward points redemption: 1 point = ₹1
        int redeem = Math.max(0, Math.min(rewardPointsToRedeem, customer.getRewardPoints()));
        double redeemAmount = redeem;

        double finalAmount = subtotal - discountAmount - redeemAmount;
        if (finalAmount < 0) finalAmount = 0;

        // reduce stock
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            Product p = productService.findById(e.getKey().getProductId()).get();
            p.reduceStock(e.getValue());
        }

        // ✅ create order (ensure Order.java supports this constructor)
        Order order = new Order(customer, items, subtotal, discountAmount + redeemAmount, finalAmount);
        orders.add(order);
        customer.addOrder(order);

        // clear cart
        customer.getShoppingCart().clear();

        // deduct redeemed points
        if (redeem > 0) customer.redeemRewardPoints(redeem);

        // award reward points: 1 point per ₹100 spent (on finalAmount)
        int earned = (int) (finalAmount / 100);
        if (earned > 0) customer.addRewardPoints(earned);

        // update revenue
        totalRevenue += finalAmount;

        return order;
    }

    public List<Order> listAllOrders() {
        return Collections.unmodifiableList(orders);
    }

    public List<Order> listOrdersForCustomer(Customer c) {
        List<Order> out = new ArrayList<>();
        for (Order o : orders) if (o.getCustomer().equals(c)) out.add(o);
        return out;
    }

    public Optional<Order> findById(String id) {
        return orders.stream().filter(o -> o.getOrderId().equals(id)).findFirst();
    }

    public void updateOrderStatus(String orderId, Order.OrderStatus status) {
        Order o = findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (o.getStatus() != Order.OrderStatus.CANCELLED && status == Order.OrderStatus.CANCELLED) {
            for (Map.Entry<Product, Integer> e : o.getItems().entrySet()) {
                productService.findById(e.getKey().getProductId())
                        .ifPresent(p -> p.increaseStock(e.getValue()));
            }
        }
        o.setStatus(status);
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}
