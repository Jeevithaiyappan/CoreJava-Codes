package com.tns.onlineshopping.entities;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {
    private final Customer owner;
    // Maintain insertion order (LinkedHashMap) for predictable listing
    private final Map<Product, Integer> items = new LinkedHashMap<>();

    public ShoppingCart(Customer owner) {
        this.owner = owner;
    }

    public Customer getOwner() { return owner; }

    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) return;
        int current = items.getOrDefault(product, 0);
        items.put(product, current + quantity);
    }

    public void removeProduct(Product product, int quantity) {
        if (product == null || !items.containsKey(product)) return;
        int current = items.get(product);
        if (quantity >= current) {
            items.remove(product);
        } else {
            items.put(product, current - quantity);
        }
    }

    public void clear() {
        items.clear();
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public String toString() {
        if (items.isEmpty()) return "Shopping cart is empty.";
        StringBuilder sb = new StringBuilder("Shopping Cart:\n");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            sb.append(String.format("%s x %d = ₹%.2f\n", e.getKey().getName(), e.getValue(), e.getKey().getPrice() * e.getValue()));
        }
        sb.append(String.format("Total: ₹%.2f", calculateTotal()));
        return sb.toString();
    }
}
