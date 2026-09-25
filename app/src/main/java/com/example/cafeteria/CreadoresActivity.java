package com.example.cafeteria;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CreadoresActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creadores);

        Button btnVolver = findViewById(R.id.btn_volver_secreto);
        btnVolver.setOnClickListener(v -> finish()); // Cierra la pantalla y vuelve al login
    }
}