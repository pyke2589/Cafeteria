package com.example.cafeteria;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CafeteriasFragment extends Fragment {

    private RecyclerView recyclerView;
    private CafeteriaAdapter adapter;
    private List<CafeteriaModelo> listaCompleta; // Respaldo de todos los datos
    private List<CafeteriaModelo> listaMostrada; // Datos que se muestran tras filtrar
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cafeterias, container, false);

        recyclerView = view.findViewById(R.id.recycler_cafeterias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaCompleta = new ArrayList<>();
        listaMostrada = new ArrayList<>();

        // Instanciamos el adaptador y programamos QUÉ PASA al tocar Información
        adapter = new CafeteriaAdapter(listaMostrada, new CafeteriaAdapter.OnItemClickListener() {
            @Override
            public void onInfoClick(CafeteriaModelo cafe) {
                // Pasamos TODO el objeto de la cafetería al nuevo fragmento
                Fragment detalleFragment = new DetalleCafeteriaFragment(cafe);

                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contenedor_principal, detalleFragment) // Tu ID real
                            .addToBackStack(null) // Permite volver con la flecha o botón del celular
                            .commit();
                }
            }
        });

        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        configurarBotones(view);
        cargarDatosDeFirebase();

        return view;
    }

    private void configurarBotones(View view) {
        Button btnCerca = view.findViewById(R.id.btn_cerca);
        Button btnPopulares = view.findViewById(R.id.btn_populares);
        Button btnAbierto = view.findViewById(R.id.btn_abierto);
        ImageView btnVolver = view.findViewById(R.id.btn_volver_cafeterias);

        // Colores en código hexadecimal (puedes ajustarlos a tu gusto)
        int colorActivo = android.graphics.Color.parseColor("#8D6E63"); // Café oscuro
        int colorInactivo = android.graphics.Color.parseColor("#D7CCC8"); // Café claro
        int textoActivo = android.graphics.Color.parseColor("#FFFFFF"); // Blanco
        int textoInactivo = android.graphics.Color.parseColor("#5D4037"); // Café texto

        // 1. Botón de Regresar
        btnVolver.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        // 2. Botón Cerca de ti (El predeterminado)
        btnCerca.setOnClickListener(v -> {
            // Pintar este botón y despintar los demás
            btnCerca.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorActivo));
            btnCerca.setTextColor(textoActivo);
            btnPopulares.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorInactivo));
            btnPopulares.setTextColor(textoInactivo);
            btnAbierto.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorInactivo));
            btnAbierto.setTextColor(textoInactivo);

            // Filtrar datos
            listaMostrada.clear();
            listaMostrada.addAll(listaCompleta);
            adapter.notifyDataSetChanged();
        });

        // 3. Botón Populares
        btnPopulares.setOnClickListener(v -> {
            // Pintar este botón y despintar los demás
            btnPopulares.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorActivo));
            btnPopulares.setTextColor(textoActivo);
            btnCerca.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorInactivo));
            btnCerca.setTextColor(textoInactivo);
            btnAbierto.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorInactivo));
            btnAbierto.setTextColor(textoInactivo);

            // Filtrar datos
            listaMostrada.clear();
            listaMostrada.addAll(listaCompleta);
            Collections.sort(listaMostrada, (c1, c2) -> Integer.compare(c2.getTotal_likes(), c1.getTotal_likes()));
            adapter.notifyDataSetChanged();
        });

        // 4. Botón Abierto
        btnAbierto.setOnClickListener(v -> {
            // Pintar este botón y despintar los demás
            btnAbierto.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorActivo));
            btnAbierto.setTextColor(textoActivo);
            btnCerca.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorInactivo));
            btnCerca.setTextColor(textoInactivo);
            btnPopulares.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorInactivo));
            btnPopulares.setTextColor(textoInactivo);

            // Filtrar datos
            listaMostrada.clear();
            for (CafeteriaModelo cafe : listaCompleta) {
                if (cafe.getCategoria().equalsIgnoreCase("Abierto")) {
                    listaMostrada.add(cafe);
                }
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void cargarDatosDeFirebase() {
        db.collection("Cafeterias")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaCompleta.clear();
                        listaMostrada.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CafeteriaModelo cafe = document.toObject(CafeteriaModelo.class);

                            // ¡CLAVE! Guardamos el ID único de Firebase en nuestra tarjeta
                            cafe.setId(document.getId());

                            listaCompleta.add(cafe);
                            listaMostrada.add(cafe);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("Firebase", "Error obteniendo documentos", task.getException());
                    }
                });
    }
}