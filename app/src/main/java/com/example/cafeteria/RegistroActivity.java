package com.example.cafeteria;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    EditText cajaNombre, cajaCorreo, cajaTelefono, cajaPass, cajaPassConf;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        cajaNombre = findViewById(R.id.reg_nombre);
        cajaCorreo = findViewById(R.id.reg_correo);
        cajaTelefono = findViewById(R.id.reg_telefono);
        cajaPass = findViewById(R.id.reg_pass);
        cajaPassConf = findViewById(R.id.reg_pass_conf);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void completarRegistro(View view) {
        String nombreText = cajaNombre.getText().toString().trim();
        String correoText = cajaCorreo.getText().toString().trim();
        String telefonoText = cajaTelefono.getText().toString().trim();
        String passText = cajaPass.getText().toString().trim();
        String passConfText = cajaPassConf.getText().toString().trim();

        if (nombreText.isEmpty() || correoText.isEmpty() || passText.isEmpty()) {
            Toast.makeText(this, "Completa nombre, correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passText.equals(passConfText)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Crear el usuario en Firebase Authentication
        mAuth.createUserWithEmailAndPassword(correoText, passText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = mAuth.getCurrentUser().getUid();

                            // 2. Guardar datos en Cloud Firestore (Colección "Usuarios")
                            Map<String, Object> usuario = new HashMap<>();
                            usuario.put("id_usuario", uid);
                            usuario.put("nombre_completo", nombreText);
                            usuario.put("correo", correoText);
                            usuario.put("telefono", telefonoText);

                            db.collection("Usuarios").document(uid).set(usuario)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(RegistroActivity.this, "¡Cuenta guardada exitosamente!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(RegistroActivity.this, "Error al guardar perfil: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(RegistroActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void volverLogin(View view) {
        this.finish();
    }
}