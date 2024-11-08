package com.lucilu.closetpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addToFireBase_DB(View view) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Map<String, Object> values = new HashMap<>();

        TextView username = findViewById(R.id.edit_text_username);
        String usernameString = username.getText().toString();
        TextView name = findViewById(R.id.edit_txt_nombre);
        TextView email = findViewById(R.id.edit_txt_email);
        TextView password = findViewById(R.id.edit_txt_pass);
        String passwordString = password.getText().toString();
        TextView passwordConf = findViewById(R.id.edit_txt_pass_conf);
        String passwordConfString = passwordConf.getText().toString();

        // Validar contraseñas
        if (!passwordString.equals(passwordConfString)) {
            Toast.makeText(RegistroActivity.this, "Passwords diferentes. Prueba de nuevo.", Toast.LENGTH_LONG).show();
            return; // Salir si las passwords no coinciden
        }

        DocumentReference docRef = database.collection("users").document(usernameString);
        docRef.get().addOnSuccessListener(document -> {

            if (document.exists()) {
                // Nombre de usuario ya está en uso
                Toast.makeText(RegistroActivity.this, "Nombre de usuario en uso. Prueba otro.", Toast.LENGTH_SHORT).show();
            } else {
                // Nombre de usuario está disponible, proceder a registrar
                values.put("name", name.getText().toString());
                values.put("email", email.getText().toString());
                values.put("password", password.getText().toString());

                // Guardar los datos en la base de datos
                docRef.set(values).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistroActivity.this, "USUARIO REGISTRADO", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistroActivity.this, HomeActivity.class));

                    } else {
                        Toast.makeText(RegistroActivity.this, "Error al registrar usuario.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(RegistroActivity.this, "Error al acceder a la base de datos.", Toast.LENGTH_SHORT).show();
        });
    }

}













