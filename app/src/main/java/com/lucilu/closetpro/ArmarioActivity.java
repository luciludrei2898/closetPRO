package com.lucilu.closetpro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ArmarioActivity extends AppCompatActivity {

    public FirebaseFirestore db;
    public ScrollView scrollCategorias;
    public LinearLayout linearLayoutContainer;

    public LinearLayout linearLayoutContainerProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_armario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fondo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar Firestore
       // db = FirebaseFirestore.getInstance();

        // Inicializar ScrollView y LinearLayout dentro del ScrollView
        scrollCategorias = findViewById(R.id.scroll_categorias_armario);
        linearLayoutContainer = findViewById(R.id.scroll_categorias_armario_layout);
        linearLayoutContainer.setOrientation(LinearLayout.VERTICAL);

        // Llamada para obtener IDs de la colección "categorias"
        obtenerIDsCategorias();
    }

    // QUE SALGA EN EL LAYOUT LAS CATEGORIAS
    int i = 1;

    private void obtenerIDsCategorias() {

        db = FirebaseFirestore.getInstance();

        db.collection("categorias")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String usernameLogin = "admin";

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String documentId = document.getId();


                            // Crear un TextView para mostrar el ID del documento
                            Button buttonCategoria = new Button(this);

                            // Le ponemos id al buttonCategoria con el i aleatorio

                            buttonCategoria.setId(1000 + i);

                        // Le ponemos un onclick al boton

                            buttonCategoria.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sacarProductos(documentId, usernameLogin); // Llamada a la función cuando se presione el botón
                                }
                            });

                            buttonCategoria.setText(documentId.toUpperCase());
                            buttonCategoria.setTextSize(18); // Size del text
                            buttonCategoria.setPadding(16, 16, 16, 16); // Espacio entre las lineas de texto

                            // Agregar el TextView al LinearLayout
                            linearLayoutContainer.addView(buttonCategoria);

                            // Le sumamos para que genere difirentes

                            i++;
                        }
                    } else {
                        // Si el metodo falla salta un toast
                        Toast.makeText(ArmarioActivity.this, "Error al cargar categorias.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void sacarProductos(String documentoId, String usernameLogin) {

        db = FirebaseFirestore.getInstance();

        String path = "categorias/" + documentoId + "/productos";
        // Acceder a la colección de productos
        db.collection(path)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                        List<Product> productosList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            // Obtener el valor de c_p_username del documento actual que recorre
                            String c_p_username = document.getString("c_p_username");

                            // Comparar c_p_username con el valor de usernameLogin que es el que se logueo
                            if (usernameLogin.equals(c_p_username)) {



                                for (DocumentSnapshot armario : documentSnapshots) {

                                    // el toObject parsea la informacion y la coloca segun el clase producto
                                    Product articulo = armario.toObject(Product.class);

                                    if (articulo != null) {

                                        productosList.add(articulo);

                                    }
                                }
                            }
                        }
                        cargarArticulos(productosList);

                    }
                }); // Cerrar correctamente el paréntesis y el punto y coma

        // Vamos a productoActivity
        startActivity(new Intent(ArmarioActivity.this, ProductoActivity.class));


    }

    public void cargarArticulos(List <Product> productos){

        LayoutInflater inflater = LayoutInflater.from(this);


        // FOR QUE SACA CADA PRODUCTO DE LA LISTA productos

        for (Product product : productos) {

            // Declaramos la vista de articulo card

            View articulate_card = inflater.inflate(R.layout.articulo_card, linearLayoutContainerProducts, false);

            ImageView imageArticulo = articulate_card.findViewById(R.id.img_card_product);
            TextView txtviewArticulo = articulate_card.findViewById(R.id.txt_card_product);

            // Trae la imagen

            Glide.with(this)
                    .load(product.getImg())
                    .into(imageArticulo);

            txtviewArticulo.setText(product.getMarca());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(

                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            int margin = getResources().getDimensionPixelSize(R.dimen.product_card_margin);

            params.setMargins(margin, margin, margin, margin);

            articulate_card.setLayoutParams(params);

            linearLayoutContainerProducts.addView(articulate_card);
        }
    }


    // BOTONES
    // BOTON HOME - FEED (VER MAS)

    // BOTON ARMARIO - ARMARIO
    public void changeArmarioToArmario(View view) {
        startActivity(new Intent(ArmarioActivity.this, ArmarioActivity.class));
    }
    // BOTON ARMARIO - HOME
    public void changeArmarioToOutfits(View view) {
        startActivity(new Intent(ArmarioActivity.this, OutfitsActivity.class));
    }
    // BOTON HOME
    public void changeArmarioToHome(View view) {
        startActivity(new Intent(ArmarioActivity.this, HomeActivity.class));
    }

    // BOTON USER

    public void changeArmarioToUser(View view) {
        startActivity(new Intent(ArmarioActivity.this, UserActivity.class));
    }

    // BOTON NUEVO PRODUCTO

    public void changeArmarioToNewProducto(View view) {
        startActivity(new Intent(ArmarioActivity.this, NewProductActivity.class));

    }


}