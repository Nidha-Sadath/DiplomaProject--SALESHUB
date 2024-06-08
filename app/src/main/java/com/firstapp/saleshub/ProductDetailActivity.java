package com.firstapp.saleshub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstapp.saleshub.Model.Cart;
import com.firstapp.saleshub.Model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView detailProductImage;
    ImageButton backBtn;
    private TextView detailProductName, detailProductPrice, detailProductDescription;
    private EditText quantityEditText;
    private Button addToCartButton, viewCartButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener((v) -> {
            onBackPressed();
        });

        detailProductImage = findViewById(R.id.detailProductImage);
        detailProductName = findViewById(R.id.detailProductName);
        detailProductPrice = findViewById(R.id.detailProductPrice);
        detailProductDescription = findViewById(R.id.detailProductDescription);
        quantityEditText = findViewById(R.id.quantityEditText);
        addToCartButton = findViewById(R.id.addToCartButton);
        viewCartButton = findViewById(R.id.viewCartButton);



        Product product = (Product) getIntent().getSerializableExtra("product");
        if (product != null) {
            detailProductName.setText(product.getName());
            detailProductImage.setImageResource(product.getImage());
            detailProductPrice.setText("OMR " + product.getPrice());
            detailProductDescription.setText(product.getDescription());
        }

        addToCartButton.setOnClickListener(v -> {
            String quantityText = quantityEditText.getText().toString();
            if (!quantityText.isEmpty()) {
                int quantity = Integer.parseInt(quantityText);
                Cart.addItem(product, quantity);
                showAddToCartDialog();
            } else {
                // Show a toast or another dialog to indicate that quantity is required
            }
        });

        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void showAddToCartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Item Added")
                .setMessage("Item successfully added to cart")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}