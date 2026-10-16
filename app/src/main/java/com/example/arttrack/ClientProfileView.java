package com.example.arttrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ClientProfileView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_profile_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String name = intent != null ? intent.getStringExtra("DISPLAY_NAME") : null;
        if (name == null || name.trim().isEmpty()) {
            name = intent != null ? intent.getStringExtra("FULL_NAME") : null;
        }

        TextView tvProfileName = findViewById(R.id.tvProfileName);
        TextView tvAvatarLetter = findViewById(R.id.tvAvatarLetter);

        if (name != null && !name.trim().isEmpty()) {
            String trimmed = name.trim();
            if (tvProfileName != null) tvProfileName.setText(trimmed);
            if (tvAvatarLetter != null && !trimmed.isEmpty()) {
                tvAvatarLetter.setText(String.valueOf(Character.toUpperCase(trimmed.charAt(0))));
            }
        }

        // Bottom Navigation Bar wiring
        View navTabHome = findViewById(R.id.navTabHome);
        if (navTabHome != null) {
            navTabHome.setOnClickListener(v -> {
                Intent homeIntent = new Intent(ClientProfileView.this, ClientHomeDashboardView.class);
                startActivity(homeIntent);
                finish();
            });
        }

        View navTabCommissions = findViewById(R.id.navTabCommissions);
        if (navTabCommissions != null) {
            navTabCommissions.setOnClickListener(v -> {
                Intent commIntent = new Intent(ClientProfileView.this, ClientCommissionsView.class);
                startActivity(commIntent);
                finish();
            });
        }

        View navTabPayments = findViewById(R.id.navTabPayments);
        if (navTabPayments != null) {
            navTabPayments.setOnClickListener(v -> {
                Intent payIntent = new Intent(ClientProfileView.this, ClientPaymentsView.class);
                startActivity(payIntent);
                finish();
            });
        }

        View navTabAlerts = findViewById(R.id.navTabAlerts);
        if (navTabAlerts != null) {
            navTabAlerts.setOnClickListener(v -> {
                Intent alertIntent = new Intent(ClientProfileView.this, ClientAlertsView.class);
                startActivity(alertIntent);
                finish();
            });
        }

        View navTabProfile = findViewById(R.id.navTabProfile);
        if (navTabProfile != null) {
            navTabProfile.setOnClickListener(v -> {
                // already on profile
            });
        }

        Button btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Intent logoutIntent = new Intent(ClientProfileView.this, WelcomeView.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                finish();
            });
        }

        Button btnHistory = findViewById(R.id.btnHistory);
        if (btnHistory != null) {
            btnHistory.setOnClickListener(v -> Toast.makeText(this, "Commission history tapped", Toast.LENGTH_SHORT).show());
        }

        Button btnSettings = findViewById(R.id.btnSettings);
        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> Toast.makeText(this, "Account settings tapped", Toast.LENGTH_SHORT).show());
        }

        Button btnHelp = findViewById(R.id.btnHelp);
        if (btnHelp != null) {
            btnHelp.setOnClickListener(v -> Toast.makeText(this, "Help tapped", Toast.LENGTH_SHORT).show());
        }
    }
}