package com.firstapp.saleshub;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.saleshub.Adapter.CartAdapter;
import com.firstapp.saleshub.Model.Cart;
import com.firstapp.saleshub.Model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private RecyclerView cartRecyclerView;
    private TextView totalPriceTextView;
    private Spinner salespersonSpinner;
    private EditText locationEditText;
    private Button placeOrderButton;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        salespersonSpinner = findViewById(R.id.salespersonSpinner);
        locationEditText = findViewById(R.id.locationEditText);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener((v) -> {
            onBackPressed();
        });

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<CartItem> cartItems = Cart.getCartItems();
        CartAdapter adapter = new CartAdapter(this, cartItems);
        cartRecyclerView.setAdapter(adapter);

        double totalPrice = Cart.getTotalPrice();
        totalPriceTextView.setText("Total Price: OMR " + totalPrice);


        // Populate the salesperson spinner
        List<String> salespersons = getSalespersons();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, salespersons);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salespersonSpinner.setAdapter(spinnerAdapter);

        placeOrderButton.setOnClickListener(v -> {
            String salespersonNumber = (String) salespersonSpinner.getSelectedItem();
            String location = locationEditText.getText().toString();

            if (salespersonNumber.isEmpty() || location.isEmpty()) {
                // Show an error message
                Toast.makeText(CartActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            } else {
                placeOrder(salespersonNumber, location);
            }
        });
    }
    private List<String> getSalespersons() {
        // Return a list of salesperson phone numbers
        // In a real application, this might be fetched from a database or API
        List<String> salespersons = new ArrayList<>();
        salespersons.add("+96897912277");
        salespersons.add("+0987654321");
        // Add more salesperson numbers as needed
        return salespersons;
    }
    private void placeOrder(String salespersonNumber, String location) {
        sendOrderNotification();
        sendOrderConfirmation(salespersonNumber, location);
        Cart.clearCart();
        ((CartAdapter) cartRecyclerView.getAdapter()).notifyDataSetChanged();
        totalPriceTextView.setText("Total Price: OMR 0.0");
    }
    private void sendOrderNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "order_channel";
        String channelName = "Order Notifications";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Order Placed")
                .setContentText("Your order has been successfully placed!")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .build();
        notificationManager.notify(1, notification);
    }
    private void sendOrderConfirmation(String salespersonNumber, String location) {
        String message = "Order successfully placed!\nLocation: " + location + "\nTotal Price: $" + Cart.getTotalPrice();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(salespersonNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "SMS failed, please try again later.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String salespersonNumber = (String) salespersonSpinner.getSelectedItem();
                String location = locationEditText.getText().toString();
                placeOrder(salespersonNumber, location);
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
