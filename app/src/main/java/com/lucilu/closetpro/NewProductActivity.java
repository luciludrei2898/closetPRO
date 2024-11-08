package com.lucilu.closetpro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class NewProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText nombreProducto, categoria, color, marca, precio;
    private Uri imageUri;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
    }

    public final ActivityResultLauncher<Intent> selectImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();

                }
            }
    );

    public void addToFireBase_DB(View view) {
        String productName = nombreProducto.getText().toString();
        String productCategory = categoria.getText().toString();
        String productColor = color.getText().toString();
        String productBrand = marca.getText().toString();
        double productPrice = Double.parseDouble(precio.getText().toString());

        if (imageUri != null) {
            try {
                int imageData = getContentResolver().openInputStream(imageUri).read();

                // Crear un mapa para representar el producto
                Map<String, Object> product = new HashMap<>();
                product.put("nombre", productName);
                product.put("p_categoria", productCategory);
                product.put("p_color", productColor);
                product.put("p_marca", productBrand);
                product.put("p_precio", productPrice);
                product.put("p_img", imageData); // Almacenar la imagen como bytes

                // Agregar el producto a Firestore
                db.collection("productos")
                        .add(product)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();

                        });
            } catch (IOException e) {
                Toast.makeText(this, "Error al leer la imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Selecciona una imagen", Toast.LENGTH_SHORT).show();
        }
    }
}