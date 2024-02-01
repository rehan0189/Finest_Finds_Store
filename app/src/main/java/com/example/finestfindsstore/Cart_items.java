package com.example.finestfindsstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Cart_items extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private Button checkoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Find views
        recyclerView = findViewById(R.id.rv_cart);
        checkoutButton = findViewById(R.id.btn_checkout);

        // Initialize RecyclerView
        cartAdapter = new CartAdapter(getSampleCartItems(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);
        ImageView btn_iv_back = findViewById(R.id.btn_back);
        btn_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set click listener for checkout button
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your checkout logic here
                // For simplicity, let's show a toast message
                Toast.makeText(Cart_items.this, "Checkout clicked", Toast.LENGTH_SHORT).show();

                // Example: Start a new activity
                // Intent intent = new Intent(Cart_items.this, YourCheckoutActivity.class);
                // startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.cart);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.favorite) {
                    startActivity(new Intent(getApplicationContext(), Favorite_items.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.cart) {
                    return true;
                } else if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }
        });
    }

    // Method to update cart items (replace this with your actual cart data)
    private void updateCartItems() {
        // Example: Update TextViews with dummy data
        TextView subtotalTextView = findViewById(R.id.total);
        TextView shippingCostTextView = findViewById(R.id.ship_fee);
        TextView totalTextView = findViewById(R.id.Total);

        if (cartAdapter.getItemCount() == 0) {
            // Handle empty cart case
            // For example, hide checkout button and show a message
            checkoutButton.setVisibility(View.GONE);
            // You may also set subtotal, shipping cost, and total to zero or any default value
        } else {
            // Replace the dummy data with your actual cart data
            subtotalTextView.setText("Subtotal: $100.00");
            shippingCostTextView.setText("Shipping cost: $10.00");
            totalTextView.setText("Total: $110.00");
        }
    }

    // Method to provide sample cart items (replace this with your actual cart data)
    private List<CartItemClass> getSampleCartItems() {
        List<CartItemClass> cartItems = new ArrayList<>();
        // Add sample items to the cart
          cartItems.add(new CartItemClass(R.drawable.logo,"Product","1500",3));
        return cartItems;
    }
}
