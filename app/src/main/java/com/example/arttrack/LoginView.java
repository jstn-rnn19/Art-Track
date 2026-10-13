package com.example.arttrack;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginView extends AppCompatActivity {

    private boolean isClient = true;
    private Button btnRoleClient;
    private Button btnRoleArtist;
    private EditText etEmailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        btnRoleClient = findViewById(R.id.btnRoleClient);
        btnRoleArtist = findViewById(R.id.btnRoleArtist);
        etEmailLogin = findViewById(R.id.etEmailLogin);
        EditText etPasswordLogin = findViewById(R.id.etPasswordLogin);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnRoleClient.setOnClickListener(v -> setRole(true));
        btnRoleArtist.setOnClickListener(v -> setRole(false));

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                String email = etEmailLogin != null ? etEmailLogin.getText().toString().trim() : "";
                String password = etPasswordLogin != null ? etPasswordLogin.getText().toString() : "";

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = "Jonas";
                if (email.contains("@")) {
                    String extracted = email.split("@")[0];
                    if (!extracted.isEmpty()) {
                        name = extracted.substring(0, 1).toUpperCase() + extracted.substring(1);
                    }
                }

                if (isClient) {
                    Intent intent = new Intent(LoginView.this, ClientHomeDashboardView.class);
                    intent.putExtra("DISPLAY_NAME", name);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LoginView.this, ArtistHomeDashboardView.class);
                    intent.putExtra("DISPLAY_NAME", name);
                    startActivity(intent);
                }
            });
        }
    }

    private void setRole(boolean client) {
        isClient = client;
        if (client) {
            btnRoleClient.setBackgroundResource(R.drawable.role_card_selected);
            btnRoleClient.setTextColor(Color.parseColor("#7351B5"));
            btnRoleArtist.setBackgroundColor(Color.TRANSPARENT);
            btnRoleArtist.setTextColor(Color.parseColor("#5A5760"));
        } else {
            btnRoleArtist.setBackgroundResource(R.drawable.role_card_selected);
            btnRoleArtist.setTextColor(Color.parseColor("#7351B5"));
            btnRoleClient.setBackgroundColor(Color.TRANSPARENT);
            btnRoleClient.setTextColor(Color.parseColor("#5A5760"));
        }
    }
}