package com.lucilu.closetpro;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db;

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
    }

    public void changeMainToRegistry(View view) {
        startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
    }

    public void Logueo(View view) {
        loginUser();
    }

    public void loginUser() {
        // Referencia a los campos de entrada
        TextView usernameField = findViewById(R.id.edit_txt_username_login);
        TextView passwordField = findViewById(R.id.edit_txt_password_login);

        // Obtiene el texto ingresado por el usuario
        String enteredUsername = usernameField.getText().toString();
        String enteredPassword = passwordField.getText().toString();

        // Comprueba si los campos están vacíos
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inicializamos Firestore
        db = FirebaseFirestore.getInstance();

        // Busca el documento del usuario con el nombre del "username" ingresado
        db.collection("users").document(enteredUsername)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Si el documento existe, verifica la contraseña
                                String storedPassword = document.getString("password");

                                if (storedPassword != null && storedPassword.equals(enteredPassword)) {
                                    // Login exitoso
                                    Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();


                                    // Almacena datos en variables locales si es necesario
                                    String username = document.getString("username");
                                    String name = document.getString("name");
                                    String email = document.getString("email");
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                                    createLocalRegistry(username, name, email);

                                } else {
                                    // Contraseña incorrecta
                                    Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Documento/usuario no encontrado
                                Toast.makeText(LoginActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void createLocalRegistry(String username, String name, String email) {
        if(username == null || name == null  || email == null)
            return;

        DataBaseAux dbaux = new DataBaseAux(this);
        SQLiteDatabase db = dbaux.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("username",username);
        values.put("email",email);

        db.insert("users", null, values);
    }



}