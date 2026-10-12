package com.example.arttrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeView extends AppCompatActivity {

    private final Handler handler =
            new Handler(Looper.getMainLooper());

    private ImageView imageView1, imageView2,
            imageView3;

    private void animateImage(ImageView image) {
        image.setVisibility(View.VISIBLE);
        image.setAlpha(0f);
        image.setTranslationY(80f);

        image.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(700)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_view);

        imageView1 = findViewById(R.id.welcomeImageFinished);
        imageView2 = findViewById(R.id.welcomeimageFinished_2);
        imageView3 = findViewById(R.id.welcomeimageFinished_3);


        // Hide images at the start
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);


        // Animate images at different delays
        handler.postDelayed(() ->
                animateImage(imageView1), 0);

        handler.postDelayed(() ->
                animateImage(imageView2), 500);

        handler.postDelayed(() ->
                animateImage(imageView3), 750);

        Button CreateAcc = findViewById(R.id.welcomeViewCreateBtn);
        CreateAcc.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeView.this, RegistrationView.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);

        imageView1 = null;
        imageView2 = null;
        imageView3 = null;


        super.onDestroy();
    }
}