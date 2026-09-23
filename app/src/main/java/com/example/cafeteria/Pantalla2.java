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

    ImageView iconoMapa, iconoCafe, iconoPerfil;
    TextView textoMapa, textoCafe, textoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

        LinearLayout btnMapa = findViewById(R.id.Layoutdos);
        LinearLayout btnCafeterias = findViewById(R.id.Layouttres);
        LinearLayout btnPerfil = findViewById(R.id.Layoutcuatro);

        iconoMapa = findViewById(R.id.IconoMap);
        textoMapa = findViewById(R.id.NombreMapa);
        iconoCafe = findViewById(R.id.Iconocafeterias);
        textoCafe = findViewById(R.id.Nombrecafe);
        iconoPerfil = findViewById(R.id.Iconoperfil);
        textoPerfil = findViewById(R.id.Nombreperfil);

        // Leemos el mensaje (Extra) enviado desde el Login
        String fragmentoInicial = getIntent().getStringExtra("fragmento_inicial");

        // Evaluamos si debemos abrir el perfil directamente o el mapa por defecto
        if (fragmentoInicial != null && fragmentoInicial.equals("perfil")) {
            cambiarFragmento(new PerfilFragment());
            iluminarMenu(iconoPerfil, textoPerfil);
        } else if (savedInstanceState == null) {
            cambiarFragmento(new GoogleMapsFragment());
            iluminarMenu(iconoMapa, textoMapa);
        }

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

    private void cambiarFragmento(Fragment fragmentoNuevo) {
        FragmentManager gestor = getSupportFragmentManager();
        FragmentTransaction transaccion = gestor.beginTransaction();
        transaccion.replace(R.id.contenedor_principal, fragmentoNuevo);
        transaccion.commit();
    }

    private void iluminarMenu(ImageView iconoActivo, TextView textoActivo) {
        int colorActivo = Color.parseColor("#5D4037");   // Café oscuro
        int colorInactivo = Color.parseColor("#A1887F"); // Café claro/apagado

        iconoMapa.setImageTintList(ColorStateList.valueOf(colorInactivo));
        textoMapa.setTextColor(colorInactivo);

        iconoCafe.setImageTintList(ColorStateList.valueOf(colorInactivo));
        textoCafe.setTextColor(colorInactivo);

        iconoPerfil.setImageTintList(ColorStateList.valueOf(colorInactivo));
        textoPerfil.setTextColor(colorInactivo);

        iconoActivo.setImageTintList(ColorStateList.valueOf(colorActivo));
        textoActivo.setTextColor(colorActivo);
    }
}