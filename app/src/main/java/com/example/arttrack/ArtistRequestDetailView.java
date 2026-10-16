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

public class ArtistRequestDetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artist_request_detail_view);

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
        String title = intent != null ? intent.getStringExtra("TITLE") : "Cyberpunk Street Scene";
        String client = intent != null ? intent.getStringExtra("CLIENT") : "Client: Jonas Villanueva";
        String budget = intent != null ? intent.getStringExtra("BUDGET") : "₱5,000";
        String deadline = intent != null ? intent.getStringExtra("DEADLINE") : "Nov 15, 2026";

        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetailClient = findViewById(R.id.tvDetailClientName);
        TextView tvDetailBudget = findViewById(R.id.tvDetailBudget);
        TextView tvDetailDeadline = findViewById(R.id.tvDetailDeadline);

        if (tvDetailTitle != null) tvDetailTitle.setText(title);
        if (tvDetailClient != null) tvDetailClient.setText(client);
        if (tvDetailBudget != null) tvDetailBudget.setText(budget);
        if (tvDetailDeadline != null) tvDetailDeadline.setText(deadline);

        Button btnAcceptRequest = findViewById(R.id.btnAcceptRequest);
        Button btnDeclineRequest = findViewById(R.id.btnDeclineRequest);

        if (btnAcceptRequest != null) {
            btnAcceptRequest.setOnClickListener(v -> {
                Toast.makeText(this, "Agreement sent to client for " + title, Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        if (btnDeclineRequest != null) {
            btnDeclineRequest.setOnClickListener(v -> {
                Toast.makeText(this, "Request declined", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}