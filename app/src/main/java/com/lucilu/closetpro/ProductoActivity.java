package com.lucilu.closetpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ProductoActivity extends AppCompatActivity {

    GridView tablaProductos;
    List<String> nameProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fondo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // tablaProductos.findViewById(R.id.grid_tabla_productos);
    }

    // BOTONES

    // BOTON ARMARIO - ARMARIO
    public void changeProductosToArmario(View view) {
        startActivity(new Intent(ProductoActivity.this, ArmarioActivity.class));
    }
    // BOTON ARMARIO - HOME
    public void changeProductosToOutfits(View view) {
        startActivity(new Intent(ProductoActivity.this, OutfitsActivity.class));
    }
    // BOTON HOME
    public void changeProductosToHome(View view) {
        startActivity(new Intent(ProductoActivity.this, HomeActivity.class));
    }

}