package com.example.cafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MensajesCoffee {

    public static void mostrar(Context contexto, String texto) {
        // 1. Inflamos (dibujamos) el diseño XML que creaste
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View layout = inflater.inflate(R.layout.diseno_mensaje_personalizado, null);

        // 2. Buscamos el TextView dentro del diseño y le ponemos tu texto
        TextView textView = layout.findViewById(R.id.texto_del_mensaje);
        textView.setText(texto);

        // 3. Creamos el Toast y le aplicamos tu diseño
        Toast toast = new Toast(contexto);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}