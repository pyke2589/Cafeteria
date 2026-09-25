package com.example.cafeteria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText cajaCorreo, cajaPass;
    FirebaseAuth mAuth;

    // Variable para el truco de la pantalla secreta
    private int contadorToques = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializamos Firebase y la Autenticación
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        //CargadorDatos.subir(this);

        // 2. Conectamos con los IDs exactos de tu activity_main.xml
        cajaCorreo = findViewById(R.id.input_correo);
        cajaPass = findViewById(R.id.input_contrasena);

        // 3. TRUCO DE LA PANTALLA SECRETA (Easter Egg)
        ImageView logoApp = findViewById(R.id.logo_app);
        logoApp.setOnClickListener(v -> {
            contadorToques++;
            if (contadorToques == 10) {
                // Reiniciamos el contador para la próxima vez
                contadorToques = 0;
                // Abrimos la pantalla fantasma
                Intent intent = new Intent(MainActivity.this, CreadoresActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método del botón "Iniciar Sesión"
    public void ingresarApp(View view) {
        String correo = cajaCorreo.getText().toString().trim();
        String pass = cajaPass.getText().toString().trim();

        if (correo.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa tu correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // 4. Validamos en la base de datos de Firebase
        mAuth.signInWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        MensajesCoffee.mostrar(MainActivity.this, "¡Bienvenido a CoffeeSpot!");

                        // Te lleva a Pantalla2 (donde está el mapa y la lista)
                        Intent intent = new Intent(MainActivity.this, Pantalla2.class);
                        startActivity(intent);
                        finish(); // Cierra esta pantalla para no volver atrás
                    } else {
                        Toast.makeText(MainActivity.this, "Error: Correo o contraseña incorrectos", Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Método del botón "Crear Cuenta"
    public void crearCuenta(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }
}