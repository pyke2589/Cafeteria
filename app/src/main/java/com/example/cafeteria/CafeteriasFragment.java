package com.example.cafeteria;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CafeteriasFragment extends Fragment {

    public CafeteriasFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_cafeterias, container, false);

        Button btnCerca = vista.findViewById(R.id.btn_cerca);
        Button btnPopulares = vista.findViewById(R.id.btn_populares);
        Button btnAbierto = vista.findViewById(R.id.btn_abierto);
        TextView textoLista = vista.findViewById(R.id.texto_lista);

        btnCerca.setOnClickListener(v -> {
            textoLista.setText("Filtro activo: Mostrando cafeterías cerca de ti 📍");
            cambiarColorBotones(btnCerca, btnPopulares, btnAbierto);
        });

        btnPopulares.setOnClickListener(v -> {
            textoLista.setText("Filtro activo: Mostrando las más populares ⭐");
            cambiarColorBotones(btnPopulares, btnCerca, btnAbierto);
        });

        btnAbierto.setOnClickListener(v -> {
            textoLista.setText("Filtro activo: Mostrando solo las abiertas ahora ☕");
            cambiarColorBotones(btnAbierto, btnCerca, btnPopulares);
        });

        return vista;
    }

    private void cambiarColorBotones(Button activo, Button inactivo1, Button inactivo2) {
        activo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8D6E63")));
        activo.setTextColor(Color.parseColor("#FFFFFF"));

        inactivo1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D7CCC8")));
        inactivo1.setTextColor(Color.parseColor("#5D4037"));

        inactivo2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D7CCC8")));
        inactivo2.setTextColor(Color.parseColor("#5D4037"));
    }
}