package com.lucilu.closetpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OutfitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_outfits);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fondo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    // BOTONES

    // BOTON OUTFITS - ARMARIO
    public void changeOutfitsToArmario(View view) {
        startActivity(new Intent(OutfitsActivity.this, ArmarioActivity.class));
    }
    // BOTON ARMARIO - HOME
    public void changeOutfitsToOutfits(View view) {
        startActivity(new Intent(OutfitsActivity.this, OutfitsActivity.class));
    }
    // BOTON HOME
    public void changeOutfitsToHome(View view) {
        startActivity(new Intent(OutfitsActivity.this, HomeActivity.class));
    }

    // BOTON USER

    public void changeOutfitsToUser(View view) {
        startActivity(new Intent(OutfitsActivity.this, UserActivity.class));
    }
}