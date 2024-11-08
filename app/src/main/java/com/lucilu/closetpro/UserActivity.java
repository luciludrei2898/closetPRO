package com.lucilu.closetpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // BOTONES

    // BOTON ARMARIO - ARMARIO
    public void changeUserToArmario(View view) {
        startActivity(new Intent(UserActivity.this, ArmarioActivity.class));
    }
    // BOTON ARMARIO - HOME
    public void changeUserToOutfits(View view) {
        startActivity(new Intent(UserActivity.this, OutfitsActivity.class));
    }
    // BOTON HOME
    public void changeUserToHome(View view) {
        startActivity(new Intent(UserActivity.this, HomeActivity.class));
    }

    // BOTON PAGINA REGISTRO

    public void changeUserToRegistro(View view) {
        startActivity(new Intent(UserActivity.this, RegistroActivity.class));
    }

}