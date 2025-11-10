package com.tns.onlineshopping.entities;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String address;
    private ShoppingCart shoppingCart;
    private List<Order> orders;
    private int rewardPoints; // for reward system

    public Customer(String userId, String username, String email, String password, String address) {
        super(userId, username, email, password);
        this.address = address;
        this.shoppingCart = new ShoppingCart(this);
        this.orders = new ArrayList<>();
        this.rewardPoints = 0;
    }

    public String getAddress() { return address; }
    public ShoppingCart getShoppingCart() { return shoppingCart; }
    public List<Order> getOrders() { return orders; }
    public int getRewardPoints() { return rewardPoints; }

    // ✅ Correct single version
    public void addRewardPoints(int points) {
        this.rewardPoints += points;
    }

    public boolean redeemRewardPoints(int points) {
        if (points <= 0 || points > rewardPoints) return false;
        rewardPoints -= points;
        return true;
    }

    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userId='" + getUserId() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", address='" + address + '\'' +
                ", rewardPoints=" + rewardPoints +
                '}';
    }
}
