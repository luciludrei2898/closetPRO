package com.lucilu.closetpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fondo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // BOTONES
    // BOTON HOME - FEED (VER MAS)

    // BOTON HOME - ARMARIO
    public void changeHomeToArmario(View view) {
        startActivity(new Intent(HomeActivity.this, ArmarioActivity.class));
    }
    // BOTON HOME - OUTFITS
    public void changeHomeToOutfits(View view) {
        startActivity(new Intent(HomeActivity.this, OutfitsActivity.class));
    }
    // BOTON HOME
    public void changeHomeToHome(View view) {
        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
    }

    // BOTON USER

    public void changeHomeToUser(View view) {
        startActivity(new Intent(HomeActivity.this, UserActivity.class));
    }
}