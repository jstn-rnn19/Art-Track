package com.example.arttrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ArtistPaymentDetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artist_payment_detail_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        Intent intent = getIntent();
        String title = intent != null ? intent.getStringExtra("TITLE") : "Watercolor Wedding Invitation";
        String client = intent != null ? intent.getStringExtra("CLIENT") : "Client: Jonas Villanueva";
        String type = intent != null ? intent.getStringExtra("TYPE") : "Down payment";
        String amount = intent != null ? intent.getStringExtra("AMOUNT") : "₱1,500";

        TextView tvPaymentTitle = findViewById(R.id.tvPaymentTitle);
        TextView tvPaymentClient = findViewById(R.id.tvPaymentClient);
        TextView tvPaymentType = findViewById(R.id.tvPaymentType);
        TextView tvPaymentAmount = findViewById(R.id.tvPaymentAmount);

        if (tvPaymentTitle != null) tvPaymentTitle.setText(title);
        if (tvPaymentClient != null) tvPaymentClient.setText(client);
        if (tvPaymentType != null) tvPaymentType.setText(type);
        if (tvPaymentAmount != null) tvPaymentAmount.setText(amount);

        Button btnMarkVerified = findViewById(R.id.btnMarkVerified);
        Button btnMarkUnverified = findViewById(R.id.btnMarkUnverified);

        if (btnMarkVerified != null) {
            btnMarkVerified.setOnClickListener(v -> {
                Toast.makeText(this, "Payment marked as verified for " + title, Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        if (btnMarkUnverified != null) {
            btnMarkUnverified.setOnClickListener(v -> {
                Toast.makeText(this, "Payment marked as unverified", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}