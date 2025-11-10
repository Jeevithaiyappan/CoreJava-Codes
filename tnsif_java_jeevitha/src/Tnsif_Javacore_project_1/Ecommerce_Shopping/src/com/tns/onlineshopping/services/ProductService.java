package com.tns.onlineshopping.services;

import com.tns.onlineshopping.entities.Product;
import com.tns.onlineshopping.entities.ProductCategory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public List<Product> listAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProduct(Product product) {
        if (product == null) return;
        Optional<Product> existing = findById(product.getProductId());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Product with same ID already exists.");
        }
        products.add(product);
    }

    public void updateProduct(Product updated) {
        Optional<Product> opt = findById(updated.getProductId());
        if (!opt.isPresent()) {
            throw new IllegalArgumentException("Product not found.");
        }
        Product p = opt.get();
        p.setName(updated.getName());
        p.setPrice(updated.getPrice());
        p.setStockQuantity(updated.getStockQuantity());
        p.setCategory(updated.getCategory());
    }

    public void removeProduct(String productId) {
        findById(productId).ifPresent(products::remove);
    }

    public Optional<Product> findById(String productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();
    }

    public List<Product> findByCategory(ProductCategory category) {
        List<Product> out = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory() == category) out.add(p);
        }
        return out;
    }

    // helper to seed sample data
    public void seedSampleProducts() {
        addProduct(new Product("P101", "Wireless Mouse", 599.0, 25, ProductCategory.ELECTRONICS));
        addProduct(new Product("P102", "Bluetooth Headphones", 1999.0, 10, ProductCategory.ELECTRONICS));
        addProduct(new Product("P220", "Smartphone", 25999.0, 20, ProductCategory.ELECTRONICS));
        addProduct(new Product("P103", "Laptop", 55999.0, 15, ProductCategory.ELECTRONICS));
        addProduct(new Product("P104", "Wireless Earbuds", 2999.0, 50, ProductCategory.ELECTRONICS));
        addProduct(new Product("P105", "Smartwatch", 4999.0, 30, ProductCategory.ELECTRONICS));
        addProduct(new Product("P106", "Bluetooth Speaker", 1999.0, 40, ProductCategory.ELECTRONICS));
        addProduct(new Product("P107", "LED TV", 39999.0, 10, ProductCategory.ELECTRONICS));
        addProduct(new Product("P108", "Power Bank", 1499.0, 60, ProductCategory.ELECTRONICS));
        addProduct(new Product("P109", "Tablet", 21999.0, 25, ProductCategory.ELECTRONICS));
        addProduct(new Product("P110", "Gaming Mouse", 999.0, 35, ProductCategory.ELECTRONICS));
        addProduct(new Product("P111", "External Hard Drive", 3999.0, 25, ProductCategory.ELECTRONICS));
        addProduct(new Product("P112", "Cotton T-Shirt", 399.0, 50, ProductCategory.FASHION));
        addProduct(new Product("P113", "Men’s T-Shirt", 799.0, 50, ProductCategory.FASHION));
        addProduct(new Product("P114", "Women’s Kurti", 1299.0, 40, ProductCategory.FASHION));
        addProduct(new Product("P115", "Jeans", 1599.0, 35, ProductCategory.FASHION));
        addProduct(new Product("P116", "Sneakers", 2499.0, 30, ProductCategory.FASHION));
        addProduct(new Product("P117", "Handbag", 1799.0, 25, ProductCategory.FASHION));
        addProduct(new Product("P118", "Wrist Watch", 3499.0, 20, ProductCategory.FASHION));
        addProduct(new Product("P119", "Sunglasses", 999.0, 45, ProductCategory.FASHION));
        addProduct(new Product("P120", "Formal Shoes", 2999.0, 20, ProductCategory.FASHION));
        addProduct(new Product("P121", "Hoodie", 1499.0, 30, ProductCategory.FASHION));
        addProduct(new Product("P122", "Cap", 499.0, 60, ProductCategory.FASHION));
        addProduct(new Product("P123", "Stainless Steel Water Bottle", 299.0, 30, ProductCategory.HOME));
        addProduct(new Product("P124", "Yoga Mat", 799.0, 15, ProductCategory.SPORTS));
        addProduct(new Product("P125", "Cricket Bat", 2499.0, 20, ProductCategory.SPORTS));
        addProduct(new Product("P126", "Football", 999.0, 35, ProductCategory.SPORTS));
        addProduct(new Product("P127", "Badminton Racquet", 1199.0, 40, ProductCategory.SPORTS));
        addProduct(new Product("P128", "Yoga Mat", 599.0, 50, ProductCategory.SPORTS));
        addProduct(new Product("P129", "Dumbbells Set", 1599.0, 30, ProductCategory.SPORTS));
        addProduct(new Product("P130", "Skipping Rope", 199.0, 70, ProductCategory.SPORTS));
        addProduct(new Product("P131", "Gym Bag", 799.0, 25, ProductCategory.SPORTS));
        addProduct(new Product("P132", "Tennis Ball Pack", 299.0, 45, ProductCategory.SPORTS));
        addProduct(new Product("P133", "Sports Towel", 499.0, 40, ProductCategory.SPORTS));
        addProduct(new Product("P134", "Water Bottle", 349.0, 60, ProductCategory.SPORTS));
        addProduct(new Product("P135", "Programming Book", 899.0, 20, ProductCategory.BOOKS));
        addProduct(new Product("P136", "Novel - The Alchemist", 499.0, 50, ProductCategory.BOOKS));
        addProduct(new Product("P137", "Textbook - Java Programming", 899.0, 30, ProductCategory.BOOKS));
        addProduct(new Product("P138", "Cookbook - Healthy Recipes", 699.0, 25, ProductCategory.BOOKS));
        addProduct(new Product("P139", "Children Story Book", 399.0, 40, ProductCategory.BOOKS));
        addProduct(new Product("P140", "Self Help - Atomic Habits", 599.0, 35, ProductCategory.BOOKS));
        addProduct(new Product("P141", "Thriller - The Silent Patient", 499.0, 25, ProductCategory.BOOKS));
        addProduct(new Product("P142", "Biography - Elon Musk", 799.0, 20, ProductCategory.BOOKS));
        addProduct(new Product("P143", "Comics - Marvel Avengers", 349.0, 45, ProductCategory.BOOKS));
        addProduct(new Product("P144", "Notebook Set", 299.0, 60, ProductCategory.BOOKS));
        addProduct(new Product("P145", "Diary 2025", 399.0, 55, ProductCategory.BOOKS));
        addProduct(new Product("P146", "Rice 5kg", 349.0, 40, ProductCategory.GROCERIES));
        addProduct(new Product("P147", "Wheat Flour 5kg", 299.0, 35, ProductCategory.GROCERIES));
        addProduct(new Product("P148", "Cooking Oil 1L", 189.0, 50, ProductCategory.GROCERIES));
        addProduct(new Product("P149", "Sugar 1kg", 55.0, 60, ProductCategory.GROCERIES));
        addProduct(new Product("P150", "Tea Powder 250g", 129.0, 45, ProductCategory.GROCERIES));
        addProduct(new Product("P151", "Coffee Powder 200g", 149.0, 45, ProductCategory.GROCERIES));
        addProduct(new Product("P152", "Detergent Powder 1kg", 99.0, 50, ProductCategory.GROCERIES));
        addProduct(new Product("P153", "Soap Pack of 4", 89.0, 60, ProductCategory.GROCERIES));
        addProduct(new Product("P154", "Toothpaste 150g", 69.0, 55, ProductCategory.GROCERIES));
        addProduct(new Product("P155", "Shampoo 200ml", 149.0, 50, ProductCategory.GROCERIES));
        addProduct(new Product("P156", "Face Wash", 199.0, 40, ProductCategory.BEAUTY));
        addProduct(new Product("P157", "Moisturizer", 299.0, 35, ProductCategory.BEAUTY));
        addProduct(new Product("P158", "Lipstick", 499.0, 50, ProductCategory.BEAUTY));
        addProduct(new Product("P159", "Eyeliner", 249.0, 45, ProductCategory.BEAUTY));
        addProduct(new Product("P160", "Compact Powder", 399.0, 30, ProductCategory.BEAUTY));
        addProduct(new Product("P161", "Foundation", 599.0, 25, ProductCategory.BEAUTY));
        addProduct(new Product("P162", "Perfume", 899.0, 40, ProductCategory.BEAUTY));
        addProduct(new Product("P163", "Hair Serum", 349.0, 50, ProductCategory.BEAUTY));
        addProduct(new Product("P164", "Nail Polish", 199.0, 60, ProductCategory.BEAUTY));
        addProduct(new Product("P165", "Face Mask", 149.0, 70, ProductCategory.BEAUTY));
        addProduct(new Product("P167", "Body Lotion", 299.0, 40, ProductCategory.BEAUTY));
        addProduct(new Product("P168", "Shampoo", 249.0, 50, ProductCategory.BEAUTY));
        addProduct(new Product("P169", "Conditioner", 249.0, 50, ProductCategory.BEAUTY));
        addProduct(new Product("P170", "Face Cream", 399.0, 40, ProductCategory.BEAUTY));
        addProduct(new Product("P171", "Makeup Brush Set", 799.0, 20, ProductCategory.BEAUTY));
        addProduct(new Product("P172", "Sunscreen Lotion", 499.0, 45, ProductCategory.BEAUTY));
        addProduct(new Product("P173", "Beard Oil", 349.0, 30, ProductCategory.BEAUTY));
        addProduct(new Product("P174", "Face Scrub", 249.0, 50, ProductCategory.BEAUTY));
        addProduct(new Product("P175", "Hand Cream", 199.0, 40, ProductCategory.BEAUTY));
        addProduct(new Product("P176", "Lip Balm", 99.0, 70, ProductCategory.BEAUTY));
        addProduct(new Product("P177", "Building Blocks Set", 999.0, 40, ProductCategory.TOYS));
        addProduct(new Product("P178", "Remote Control Car", 1499.0, 25, ProductCategory.TOYS));
        addProduct(new Product("P179", "Barbie Doll", 899.0, 30, ProductCategory.TOYS));
        addProduct(new Product("P180", "Action Figure", 699.0, 35, ProductCategory.TOYS));
        addProduct(new Product("P181", "Puzzle Game", 499.0, 40, ProductCategory.TOYS));
        addProduct(new Product("P182", "Stuffed Teddy Bear", 799.0, 45, ProductCategory.TOYS));
        addProduct(new Product("P183", "Toy Train Set", 1299.0, 20, ProductCategory.TOYS));
        addProduct(new Product("P184", "Lego Set", 2499.0, 15, ProductCategory.TOYS));
        addProduct(new Product("P185", "Drawing Kit", 349.0, 50, ProductCategory.TOYS));
        addProduct(new Product("P186", "Kids Kitchen Set", 999.0, 30, ProductCategory.TOYS));
        addProduct(new Product("P187", "Toy Gun", 599.0, 40, ProductCategory.TOYS));
        addProduct(new Product("P188", "Rubik’s Cube", 199.0, 60, ProductCategory.TOYS));
        addProduct(new Product("P189", "Musical Toy Piano", 1199.0, 25, ProductCategory.TOYS));
        addProduct(new Product("P190", "Baby Rattle Set", 249.0, 55, ProductCategory.TOYS));
        addProduct(new Product("P191", "Toy Helicopter", 1799.0, 20, ProductCategory.TOYS));
        addProduct(new Product("P192", "Board Game - Ludo", 299.0, 70, ProductCategory.TOYS));
        addProduct(new Product("P193", "Chess Board", 499.0, 45, ProductCategory.TOYS));
        addProduct(new Product("P194", "Toy Drum Set", 999.0, 25, ProductCategory.TOYS));
        addProduct(new Product("P195", "Robot Toy", 1599.0, 20, ProductCategory.TOYS));
        addProduct(new Product("P196", "Coloring Book", 149.0, 80, ProductCategory.TOYS));
        addProduct(new Product("P200", "Curtain Set", 899.0, 25, ProductCategory.HOME));
        addProduct(new Product("P201", "Wall Clock", 499.0, 40, ProductCategory.HOME));
        addProduct(new Product("P202", "Cushion Cover Set", 299.0, 45, ProductCategory.HOME));
        addProduct(new Product("P203", "Doormat", 199.0, 60, ProductCategory.HOME));
        addProduct(new Product("P204", "Table Lamp", 999.0, 30, ProductCategory.HOME));
        addProduct(new Product("P205", "Wall Painting", 1299.0, 20, ProductCategory.HOME));
        addProduct(new Product("P206", "Flower Vase", 499.0, 35, ProductCategory.HOME));
        addProduct(new Product("P207", "Bed Sheet Set", 1499.0, 25, ProductCategory.HOME));
        addProduct(new Product("P208", "Blanket", 1999.0, 20, ProductCategory.HOME));
        addProduct(new Product("P209", "Pillow", 499.0, 50, ProductCategory.HOME));
        addProduct(new Product("P210", "Wall Mirror", 899.0, 25, ProductCategory.HOME));
        addProduct(new Product("P211", "Scented Candle", 349.0, 45, ProductCategory.HOME));
        addProduct(new Product("P212", "Laundry Basket", 599.0, 35, ProductCategory.HOME));
        addProduct(new Product("P213", "Photo Frame", 249.0, 40, ProductCategory.HOME));
        addProduct(new Product("P214", "Storage Box", 399.0, 50, ProductCategory.HOME));
        addProduct(new Product("P215", "Dinner Set", 2499.0, 20, ProductCategory.HOME));
        addProduct(new Product("P216", "Wall Shelf", 999.0, 30, ProductCategory.HOME));
        addProduct(new Product("P217", "Carpet", 2999.0, 15, ProductCategory.HOME));
        addProduct(new Product("P218", "Room Freshener", 299.0, 45, ProductCategory.HOME));
        addProduct(new Product("P219", "Clock Lamp Combo", 1299.0, 20, ProductCategory.HOME));

    }
}
