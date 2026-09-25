package com.example.cafeteria;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
    private List<CafeteriaModelo> listaCompleta;
    private List<CafeteriaModelo> listaMostrada;
    private FirebaseFirestore db;

    // Variables para guardar el estado de los filtros
    private String categoriaActual = "Cerca"; // Puede ser: Cerca, Populares, Abierto
    private String tipoActual = "Ambas";      // Puede ser: Ambas, Cafetería, Tostaduría

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cafeterias, container, false);

        recyclerView = view.findViewById(R.id.recycler_cafeterias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaCompleta = new ArrayList<>();
        listaMostrada = new ArrayList<>();

        adapter = new CafeteriaAdapter(listaMostrada, cafe -> {
            Fragment detalleFragment = new DetalleCafeteriaFragment(cafe);
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor_principal, detalleFragment)
                        .addToBackStack(null)
                        .commit();
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
        ImageView btnFiltroMenu = view.findViewById(R.id.btn_menu_filtro); // El nuevo botón de 3 rayas

        int colorActivo = android.graphics.Color.parseColor("#8D6E63");
        int colorInactivo = android.graphics.Color.parseColor("#D7CCC8");
        int textoActivo = android.graphics.Color.parseColor("#FFFFFF");
        int textoInactivo = android.graphics.Color.parseColor("#5D4037");

        // 1. Botón Volver
        btnVolver.setOnClickListener(v -> {
            if (getActivity() != null) {
                View btnMapa = getActivity().findViewById(R.id.Layoutdos);
                if (btnMapa != null) {
                    btnMapa.performClick();
                } else {
                    getActivity().onBackPressed();
                }
            }
        });

        // 2. NUEVO: Menú Desplegable (Hamburguesa)
        btnFiltroMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), v);
            popup.getMenu().add("Mostrar Ambas");
            popup.getMenu().add("Solo Cafeterías");
            popup.getMenu().add("Solo Tostadurías");

            popup.setOnMenuItemClickListener(item -> {
                String titulo = item.getTitle().toString();
                if (titulo.equals("Mostrar Ambas")) {
                    tipoActual = "Ambas";
                } else if (titulo.equals("Solo Cafeterías")) {
                    tipoActual = "Cafetería";
                } else if (titulo.equals("Solo Tostadurías")) {
                    tipoActual = "Tostaduría";
                }
                Toast.makeText(getContext(), "Filtro: " + tipoActual, Toast.LENGTH_SHORT).show();
                aplicarFiltrosCombinados();
                return true;
            });
            popup.show();
        });

        // 3. Botón Cerca de ti
        btnCerca.setOnClickListener(v -> {
            categoriaActual = "Cerca";
            actualizarColores(btnCerca, btnPopulares, btnAbierto, colorActivo, colorInactivo, textoActivo, textoInactivo);
            aplicarFiltrosCombinados();
        });

        // 4. Botón Populares
        btnPopulares.setOnClickListener(v -> {
            categoriaActual = "Populares";
            actualizarColores(btnPopulares, btnCerca, btnAbierto, colorActivo, colorInactivo, textoActivo, textoInactivo);
            aplicarFiltrosCombinados();
        });

        // 5. Botón Abierto
        btnAbierto.setOnClickListener(v -> {
            categoriaActual = "Abierto";
            actualizarColores(btnAbierto, btnCerca, btnPopulares, colorActivo, colorInactivo, textoActivo, textoInactivo);
            aplicarFiltrosCombinados();
        });
    }

    // Método de apoyo para cambiar colores rápidamente
    private void actualizarColores(Button activo, Button inactivo1, Button inactivo2, int cAct, int cInact, int tAct, int tInact) {
        activo.setBackgroundTintList(android.content.res.ColorStateList.valueOf(cAct));
        activo.setTextColor(tAct);
        inactivo1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(cInact));
        inactivo1.setTextColor(tInact);
        inactivo2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(cInact));
        inactivo2.setTextColor(tInact);
    }

    // EL CEREBRO DE LOS FILTROS: Mezcla la Categoría con el Tipo
    private void aplicarFiltrosCombinados() {
        listaMostrada.clear();

        for (CafeteriaModelo cafe : listaCompleta) {
            // Evaluamos si pasa el filtro de TIPO (Menú hamburguesa)
            boolean pasaTipo = tipoActual.equals("Ambas") || (cafe.getTipo() != null && cafe.getTipo().equalsIgnoreCase(tipoActual));

            // Evaluamos si pasa el filtro de CATEGORÍA (Los 3 botones)
            boolean pasaCat = true;
            if (categoriaActual.equals("Abierto")) {
                pasaCat = cafe.getCategoria() != null && cafe.getCategoria().equalsIgnoreCase("Abierto");
            }

            // Si cumple ambas condiciones, lo añadimos
            if (pasaTipo && pasaCat) {
                listaMostrada.add(cafe);
            }
        }

        // Si la categoría era "Populares", ordenamos los resultados de mayor a menor Likes
        if (categoriaActual.equals("Populares")) {
            Collections.sort(listaMostrada, (c1, c2) -> Integer.compare(c2.getTotal_likes(), c1.getTotal_likes()));
        }

        adapter.notifyDataSetChanged();
    }

    private void cargarDatosDeFirebase() {
        db.collection("Cafeterias")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaCompleta.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CafeteriaModelo cafe = document.toObject(CafeteriaModelo.class);
                            cafe.setId(document.getId());
                            listaCompleta.add(cafe);
                        }
                        // Aplicamos los filtros iniciales por defecto (Cerca y Ambas)
                        aplicarFiltrosCombinados();
                    } else {
                        Log.e("Firebase", "Error obteniendo documentos", task.getException());
                    }
                });
    }
}