package com.firstapp.saleshub.Model;

import java.util.ArrayList;

public class Cart {

    private static ArrayList<CartItem> cartItems = new ArrayList<>();

    public static void addItem(Product product, int quantity) {
        CartItem cartItem = new CartItem(product, quantity);
        cartItems.add(cartItem);
    }

    public static ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public static double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public static void clearCart() {
        cartItems.clear();
    }
}