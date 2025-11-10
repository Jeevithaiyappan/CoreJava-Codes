package com.tns.onlineshopping.utils;

import com.tns.onlineshopping.entities.Product;
import com.tns.onlineshopping.entities.ProductCategory;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for handling category-based and coupon-based discounts.
 */
public class DiscountUtil {

    // Category-based discounts
    private static final Map<ProductCategory, Double> CATEGORY_DISCOUNTS = new HashMap<>();

    // Coupon code discounts
    private static final Map<String, Double> COUPONS = new HashMap<>();

    static {
        CATEGORY_DISCOUNTS.put(ProductCategory.ELECTRONICS, 0.05); // 5%
        CATEGORY_DISCOUNTS.put(ProductCategory.FASHION, 0.07);     // 7%
        CATEGORY_DISCOUNTS.put(ProductCategory.BOOKS, 0.03);       // 3%

        COUPONS.put("WELCOME10", 0.10); // 10% off
        COUPONS.put("DIWALI20", 0.20);  // 20% off
        COUPONS.put("FLAT50", 50.0);    // Flat ₹50 off
    }

    /**
     * Calculates discount based on product category.
     */
    public static double calculateCategoryDiscountAmount(Map<Product, Integer> items) {
        double discount = 0.0;
        for (var entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double categoryPercent = CATEGORY_DISCOUNTS.getOrDefault(product.getCategory(), 0.0);
            discount += product.getPrice() * quantity * categoryPercent;
        }
        return discount;
    }

    /**
     * Apply coupon code and return the discount amount.
     */
    public static double applyCoupon(String couponCode, double subtotal) {
        if (couponCode == null || couponCode.isEmpty()) return 0.0;
        couponCode = couponCode.trim().toUpperCase();

        if (!COUPONS.containsKey(couponCode)) return 0.0;

        double value = COUPONS.get(couponCode);

        // If between 0 and 1 => percentage, otherwise flat ₹ amount
        if (value > 0 && value <= 1.0) {
            return subtotal * value;
        } else {
            return value;
        }
    }

    /**
     * Combine both category discount and coupon discount.
     */
    public static double calculateTotalDiscount(Map<Product, Integer> items, String couponCode, double subtotal) {
        double categoryDiscount = calculateCategoryDiscountAmount(items);
        double couponDiscount = applyCoupon(couponCode, subtotal);
        return categoryDiscount + couponDiscount;
    }

    /**
     * Order-level discount based on total amount thresholds.
     */
    public static double calculateDiscountAmount(double total) {
        if (total >= 5000) return total * 0.10;
        if (total >= 2000) return total * 0.05;
        return 0.0;
    }

    /**
     * Apply discount and return final payable amount.
     */
    public static double applyDiscount(double total) {
        return total - calculateDiscountAmount(total);
    }
}
