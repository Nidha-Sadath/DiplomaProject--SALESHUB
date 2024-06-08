package com.firstapp.saleshub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.saleshub.Adapter.ProductAdapter;
import com.firstapp.saleshub.Model.Product;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    ImageButton backBtn;
    private ProductAdapter adapter;
    private ArrayList<Product> productList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener((v) -> {
            onBackPressed();
        });

        // Initialize product list
        productList = new ArrayList<>();
        productList.add(new Product("Chips Oman", R.drawable.chipsoman, 20.0, "Fresh Potato Chips With Chilli Flavour"));
        productList.add(new Product("Chips Oman Can", R.drawable.chipsomancan, 15.0, "Fresh Potato Chips With Chilli Flavour"));
        productList.add(new Product("Salad Chips", R.drawable.salad, 10.0, "Dehydrated Potato And Modified Starch Based Pellets With Hot And Sour Flavour"));
        productList.add(new Product("Sohar Chips", R.drawable.sohar, 10.0, "Potato Granules, Modified Starch And Rice Based Pellets With Chicken And Chilli flavour"));
        productList.add(new Product("Nano chips", R.drawable.nano, 15.0, "Potato Starch Wheat Based Pellets With Chilli Chicken Flavour"));
        productList.add(new Product("Qarmoosh", R.drawable.qarmoosh, 10.0, "Corn Powder And Wheat Based Pellets With Sweet and Chilli Flavour"));
        productList.add(new Product("Majid Chips", R.drawable.majid, 10.0, "Corn Curls with Chicken Flavour"));
        productList.add(new Product("Pofak Oman", R.drawable.pofakoman, 10.0, "Corn Curls with Cheese And Butter Flavour"));
        productList.add(new Product("Sky Chips", R.drawable.sky, 5.0, "Fresh Potato Crinckles Chips With Chilli Sour Flavour"));
        productList.add(new Product("Alibaba", R.drawable.alibaba, 10.0, "Corn Curls with Cheese flavour"));
        // Add more products as needed



        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);
    }
}
