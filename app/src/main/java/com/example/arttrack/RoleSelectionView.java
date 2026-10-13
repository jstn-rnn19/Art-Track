package com.example.arttrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RoleSelectionView extends AppCompatActivity {

    public enum Role {
        NONE,
        CLIENT,
        ARTIST
    }

    private Role selectedRole = Role.NONE;
    private ConstraintLayout cardClient;
    private ConstraintLayout cardArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_role_selection_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cardClient = findViewById(R.id.cardClient);
        cardArtist = findViewById(R.id.cardArtist);
        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnContinue = findViewById(R.id.btnContinue);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        if (cardClient != null) {
            cardClient.setOnClickListener(v -> selectRole(Role.CLIENT));
        }

        if (cardArtist != null) {
            cardArtist.setOnClickListener(v -> selectRole(Role.ARTIST));
        }

        String fullNameExtra = getIntent() != null ? getIntent().getStringExtra("FULL_NAME") : "";
        final String fullName = fullNameExtra != null ? fullNameExtra : "";

        if (btnContinue != null) {
            btnContinue.setOnClickListener(v -> {
                if (selectedRole == Role.CLIENT) {
                    Intent intent = new Intent(RoleSelectionView.this, ProfileSetupForClientsView.class);
                    intent.putExtra("SELECTED_ROLE", selectedRole.name());
                    intent.putExtra("FULL_NAME", fullName);
                    startActivity(intent);
                } else if (selectedRole == Role.ARTIST) {
                    Intent intent = new Intent(RoleSelectionView.this, MainActivity.class);
                    intent.putExtra("SELECTED_ROLE", selectedRole.name());
                    intent.putExtra("FULL_NAME", fullName);
                    startActivity(intent);
                }
            });
        }
    }

    private void selectRole(Role role) {
        selectedRole = role;
        if (role == Role.CLIENT) {
            cardClient.setBackgroundResource(R.drawable.role_card_selected);
            cardArtist.setBackgroundResource(R.drawable.role_card_unselected);
        } else if (role == Role.ARTIST) {
            cardArtist.setBackgroundResource(R.drawable.role_card_selected);
            cardClient.setBackgroundResource(R.drawable.role_card_unselected);
        }
    }
}