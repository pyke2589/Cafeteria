package com.example.cafeteria;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Pantalla2 extends AppCompatActivity {

    // Declaramos las vistas de los íconos y textos
    ImageView iconoMapa, iconoCafe, iconoPerfil;
    TextView textoMapa, textoCafe, textoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

        // Los botones principales
        LinearLayout btnMapa = findViewById(R.id.Layoutdos);
        LinearLayout btnCafeterias = findViewById(R.id.Layouttres);
        LinearLayout btnPerfil = findViewById(R.id.Layoutcuatro);

        // Vinculamos los íconos y textos específicos usando tus IDs actuales
        iconoMapa = findViewById(R.id.IconoMap);
        textoMapa = findViewById(R.id.NombreMapa);
        iconoCafe = findViewById(R.id.Iconocafeterias);
        textoCafe = findViewById(R.id.Nombrecafe);
        iconoPerfil = findViewById(R.id.Iconoperfil);
        textoPerfil = findViewById(R.id.Nombreperfil);

        // Cargar el mapa por defecto y pintar su botón
        if (savedInstanceState == null) {
            cambiarFragmento(new GoogleMapsFragment());
            iluminarMenu(iconoMapa, textoMapa);
        }

        // Programar los clics
        btnMapa.setOnClickListener(view -> {
            cambiarFragmento(new GoogleMapsFragment());
            iluminarMenu(iconoMapa, textoMapa);
        });

        btnCafeterias.setOnClickListener(view -> {
            cambiarFragmento(new CafeteriasFragment());
            iluminarMenu(iconoCafe, textoCafe);
        });

        btnPerfil.setOnClickListener(view -> {
            cambiarFragmento(new PerfilFragment());
            iluminarMenu(iconoPerfil, textoPerfil);
        });
    }

    // Método que cambia la pantalla de arriba
    private void cambiarFragmento(Fragment fragmentoNuevo) {
        FragmentManager gestor = getSupportFragmentManager();
        FragmentTransaction transaccion = gestor.beginTransaction();
        transaccion.replace(R.id.contenedor_principal, fragmentoNuevo);
        transaccion.commit();
    }

    // Método que pinta el menú inferior dinámicamente
    private void iluminarMenu(ImageView iconoActivo, TextView textoActivo) {
        int colorActivo = Color.parseColor("#5D4037");   // Café oscuro
        int colorInactivo = Color.parseColor("#A1887F"); // Café claro/apagado

        // 1. Apagamos todos los botones primero
        iconoMapa.setImageTintList(ColorStateList.valueOf(colorInactivo));
        textoMapa.setTextColor(colorInactivo);

        iconoCafe.setImageTintList(ColorStateList.valueOf(colorInactivo));
        textoCafe.setTextColor(colorInactivo);

        iconoPerfil.setImageTintList(ColorStateList.valueOf(colorInactivo));
        textoPerfil.setTextColor(colorInactivo);

        // 2. Encendemos únicamente el que el usuario acaba de tocar
        iconoActivo.setImageTintList(ColorStateList.valueOf(colorActivo));
        textoActivo.setTextColor(colorActivo);
    }
}