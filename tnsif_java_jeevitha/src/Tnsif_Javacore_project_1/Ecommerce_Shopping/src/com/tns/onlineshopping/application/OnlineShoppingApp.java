package com.tns.onlineshopping.application;

import com.tns.onlineshopping.entities.*;
import com.tns.onlineshopping.services.*;
import com.tns.onlineshopping.utils.DiscountUtil;

import java.util.*;
import java.util.HashMap;

public class OnlineShoppingApp {

    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService(productService);
    private final CustomerService customerService = new CustomerService(productService, orderService);
    private final Admin admin = new Admin("A1", "admin", "admin@shop.com", "admin123"); // ✅ added password
    private final AdminService adminService = new AdminService(admin, productService,orderService);
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        OnlineShoppingApp app = new OnlineShoppingApp();
        app.start();
    }

    public OnlineShoppingApp() {
        productService.seedSampleProducts();
        // sample customer
        Customer c = new Customer("C101", "alice", "alice@example.com", "alice123", "123 Main St");
        customerService.registerCustomer(c);
    }

    private void start() {
        System.out.println("=== Welcome to TNS Online Shopping App ===");
        boolean exit = false;
        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Register Customer");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": adminLogin(); break;
                case "2": customerLogin(); break;
                case "3": registerCustomer(); break;
                case "4": exit = true; break;
                default: System.out.println("Invalid option.");
            }
        }
        System.out.println("Thanks for using the app. Goodbye!");
    }

    /* ---------------- Admin ---------------- */
    private void adminLogin() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine().trim();

        if (!username.equalsIgnoreCase(admin.getUsername()) || !password.equals(admin.getPassword())) {
            System.out.println("❌ Invalid admin credentials.");
            return;
        }
        System.out.println("\n✅ Admin login successful!\n");
        adminMenu();
    }

    private void adminMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Remove Product");
            System.out.println("4. List Products");
            System.out.println("5. List All Orders");
            System.out.println("6. Update Order Status");
            System.out.println("7. Dashboard");
            System.out.println("8. Back to Main");
            System.out.print("Choose: ");
            String ch = scanner.nextLine().trim();
            switch (ch) {
                case "1": addProductFlow(); break;
                case "2": updateProductFlow(); break;
                case "3": removeProductFlow(); break;
                case "4": listProducts(); break;
                case "5": listAllOrders(); break;
                case "6": updateOrderStatusFlow(); break;
                case "7": showDashboard(); break;
                case "8": back = true; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void addProductFlow() {
        System.out.print("Product ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Stock quantity: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Category (ELECTRONICS,FASHION,HOME,BEAUTY,BOOKS,SPORTS,GROCERIES,TOYS,OTHER): ");
        ProductCategory cat;
        try {
            cat = ProductCategory.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (Exception e) {
            cat = ProductCategory.OTHER;
        }
        Product p = new Product(id, name, price, qty, cat);
        try {
            adminService.addProduct(p);
            System.out.println("✅ Product added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateProductFlow() {
        System.out.print("Product ID to update: ");
        String id = scanner.nextLine().trim();
        Optional<Product> opt = productService.findById(id);
        if (!opt.isPresent()) {
            System.out.println("Product not found.");
            return;
        }
        Product existing = opt.get();
        System.out.println("Current: " + existing);
        System.out.print("New name (leave blank to keep): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) existing.setName(name);
        System.out.print("New price (leave blank to keep): ");
        String pStr = scanner.nextLine().trim();
        if (!pStr.isEmpty()) existing.setPrice(Double.parseDouble(pStr));
        System.out.print("New stock (leave blank to keep): ");
        String qStr = scanner.nextLine().trim();
        if (!qStr.isEmpty()) existing.setStockQuantity(Integer.parseInt(qStr));
        System.out.print("New category (leave blank to keep): ");
        String cStr = scanner.nextLine().trim();
        if (!cStr.isEmpty()) {
            try {
                existing.setCategory(ProductCategory.valueOf(cStr.toUpperCase()));
            } catch (Exception e) { /* ignore */ }
        }
        try {
            adminService.updateProduct(existing);
            System.out.println("✅ Product updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating: " + e.getMessage());
        }
    }

    private void removeProductFlow() {
        System.out.print("Product ID to remove: ");
        String id = scanner.nextLine().trim();
        adminService.removeProduct(id);
        System.out.println("If product existed, it has been removed.");
    }

    private void listProducts() {
        System.out.println("\n--- Product Catalog ---");
        for (Product p : productService.listAllProducts()) {
            System.out.println(p);
        }
    }

    private void listAllOrders() {
        System.out.println("\n--- All Orders ---");
        for (Order o : orderService.listAllOrders()) {
            System.out.println(o);
        }
    }

    private void updateOrderStatusFlow() {
        System.out.print("Order ID: ");
        String oid = scanner.nextLine().trim();
        Optional<Order> opt = orderService.findById(oid);
        if (!opt.isPresent()) {
            System.out.println("Order not found.");
            return;
        }
        System.out.println("Current: " + opt.get());
        System.out.print("New status (PENDING, COMPLETED, DELIVERED, CANCELLED): ");
        try {
            Order.OrderStatus status = Order.OrderStatus.valueOf(scanner.nextLine().trim().toUpperCase());
            orderService.updateOrderStatus(oid, status);
            System.out.println("✅ Order status updated successfully!");
        } catch (Exception e) {
            System.out.println("Invalid status or update failed: " + e.getMessage());
        }
    }
    private void showDashboard() {
        Map<String,Object> m = adminService.getDashboardMetrics();
        System.out.println("\n--- Admin Dashboard ---");
        System.out.println("Total Products: " + m.get("totalProducts"));
        System.out.println("Total Orders: " + m.get("totalOrders"));
        System.out.println("Total Revenue: ₹" + String.format("%.2f", m.get("totalRevenue")));
        System.out.println("Top Category: " + m.get("topCategory"));
    }
    /* ---------------- Customer ---------------- */
    private void customerLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        Optional<Customer> opt = customerService.findByUsername(username);
        if (!opt.isPresent() || !opt.get().getPassword().equals(password)) {
            System.out.println("❌ Invalid customer credentials.");
            return;
        }
        Customer customer = opt.get();
        System.out.println("\n✅ Login successful! Welcome, " + customer.getUsername() + "!");
        customerMenu(customer);
    }

    private void registerCustomer() {
        System.out.print("New username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Address: ");
        String address = scanner.nextLine().trim();
        String userId = "C" + (100 + (int)(Math.random() * 900));
        Customer c = new Customer(userId, username, email, password, address);
        customerService.registerCustomer(c);
        System.out.println("✅ Registration successful! You can now log in as " + username);
    }

    private void customerMenu(Customer customer) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Customer Menu (" + customer.getUsername() + ") ---");
            System.out.println("1. Browse Products");
            System.out.println("2. View Cart");
            System.out.println("3. Add Product to Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. Apply Coupon & Place Order");
            System.out.println("6. List My Orders");
            System.out.println("8. View Reward Points");
            System.out.println("9. Back");
            System.out.print("Choose: ");
            String ch = scanner.nextLine().trim();
            switch (ch) {
                case "1": browseProducts(); break;
                case "2": viewCart(customer); break;
                case "3": addToCartFlow(customer); break;
                case "4": removeFromCartFlow(customer); break;
                case "5": placeOrderWithCouponFlow(customer); break;
                case "6": listCustomerOrders(customer); break;
                case "7": System.out.println("Reward Points: " + customer.getRewardPoints()); break;
                case "8": back = true; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void browseProducts() {
        System.out.println("\n--- Browse Products ---");
        listProducts();
    }

    private void viewCart(Customer customer) {
        System.out.println("\n--- Your Cart ---");
        System.out.println(customer.getShoppingCart());
    }

    private void addToCartFlow(Customer customer) {
        System.out.print("Product ID to add: ");
        String pid = scanner.nextLine().trim();
        Optional<Product> opt = productService.findById(pid);
        if (!opt.isPresent()) {
            System.out.println("Product not found.");
            return;
        }
        Product p = opt.get();
        System.out.print("Quantity: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());
        if (qty <= 0) {
            System.out.println("Quantity must be > 0.");
            return;
        }
        if (!p.isInStock(qty)) {
            System.out.println("Not enough stock. Available: " + p.getStockQuantity());
            return;
        }
        customer.getShoppingCart().addProduct(p, qty);
        System.out.println("Added to cart.");
    }

    private void removeFromCartFlow(Customer customer) {
        System.out.print("Product ID to remove from cart: ");
        String pid = scanner.nextLine().trim();
        Optional<Product> opt = productService.findById(pid);
        if (!opt.isPresent()) {
            System.out.println("Product not found.");
            return;
        }
        Product p = opt.get();
        System.out.print("Quantity to remove (or enter 0 to remove all): ");
        int qty = Integer.parseInt(scanner.nextLine().trim());
        if (qty <= 0) {
            customer.getShoppingCart().removeProduct(p, Integer.MAX_VALUE);
        } else {
            customer.getShoppingCart().removeProduct(p, qty);
        }
        System.out.println("Updated cart.");
    }

    private void placeOrderWithCouponFlow(Customer customer) {
        if (customer.getShoppingCart().isEmpty()) { System.out.println("Cart empty."); return; }
        System.out.print("Enter coupon code (or press Enter to skip): ");
        String coupon = scanner.nextLine().trim();
        System.out.print("Enter reward points to redeem (available " + customer.getRewardPoints() + "): ");
        int redeem = Integer.parseInt(scanner.nextLine().trim());
        if (redeem < 0) redeem = 0;
        try {
            Order order = orderService.placeOrder(customer, coupon.isEmpty()? null : coupon, redeem);
            order.setStatus(Order.OrderStatus.COMPLETED);
            System.out.println(order.generateInvoice());
        } catch (Exception e) {
            System.out.println("Failed to place order: " + e.getMessage());
        }
    }
    private void listCustomerOrders(Customer customer) {
        System.out.println("\n--- My Orders ---");
        for (Order o : orderService.listOrdersForCustomer(customer)) {
            System.out.println(o);
        }
    }
}
