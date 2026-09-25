package com.example.cafeteria;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PerfilFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String uid;

    private CafeteriaAdapter adapterFavoritos;
    private List<CafeteriaModelo> listaFavoritos;

    private ComentarioAdapter adapterComentarios;
    private List<ComentarioModelo> listaComentarios;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) return view;
        uid = mAuth.getCurrentUser().getUid();

        // ELEMENTOS DE LA UI
        TextView tvNombre = view.findViewById(R.id.txt_perfil_nombre);
        TextView tvCorreo = view.findViewById(R.id.txt_perfil_correo);
        TextView tvTelefono = view.findViewById(R.id.txt_perfil_telefono);

        TextView tabFavs = view.findViewById(R.id.tab_favoritos);
        TextView tabComent = view.findViewById(R.id.tab_mis_comentarios);
        RecyclerView recyclerFavs = view.findViewById(R.id.recycler_perfil_favoritos);
        RecyclerView recyclerComent = view.findViewById(R.id.recycler_perfil_comentarios);
        ImageView btnCerrarSesion = view.findViewById(R.id.btn_cerrar_sesion);
        ImageView btnVolver = view.findViewById(R.id.btn_volver_perfil);

        // 1. DATOS DEL USUARIO
        db.collection("Usuarios").document(uid).get().addOnSuccessListener(document -> {
            if (document.exists()) {
                tvNombre.setText(document.getString("nombre_completo"));
                tvCorreo.setText(document.getString("correo"));
                tvTelefono.setText("Teléfono: " + document.getString("telefono"));
            }
        });

        // 2. CONFIGURAR LISTA DE FAVORITOS
        recyclerFavs.setLayoutManager(new LinearLayoutManager(getContext()));
        listaFavoritos = new ArrayList<>();
        adapterFavoritos = new CafeteriaAdapter(listaFavoritos, cafe -> {
            Fragment detalleFragment = new DetalleCafeteriaFragment(cafe);
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor_principal, detalleFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerFavs.setAdapter(adapterFavoritos);

        // 3. CONFIGURAR LISTA DE COMENTARIOS
        recyclerComent.setLayoutManager(new LinearLayoutManager(getContext()));
        listaComentarios = new ArrayList<>();
        adapterComentarios = new ComentarioAdapter(listaComentarios);
        recyclerComent.setAdapter(adapterComentarios);

        // 4. DESCARGAR FAVORITOS EN VIVO
        db.collection("Usuarios").document(uid).collection("Favoritos")
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    if (value != null) {
                        listaFavoritos.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            CafeteriaModelo cafe = doc.toObject(CafeteriaModelo.class);
                            cafe.setId(doc.getId());
                            listaFavoritos.add(cafe);
                        }
                        adapterFavoritos.notifyDataSetChanged();
                    }
                });

        // 5. DESCARGAR COMENTARIOS EN VIVO
        db.collection("Usuarios").document(uid).collection("MisComentarios")
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    if (value != null) {
                        listaComentarios.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            ComentarioModelo coment = doc.toObject(ComentarioModelo.class);
                            listaComentarios.add(coment);
                        }
                        adapterComentarios.notifyDataSetChanged();
                    }
                });

        // 6. ALTERNAR PESTAÑAS (Visual)
        int colorActivo = Color.parseColor("#8D6E63");
        int colorInactivo = Color.parseColor("#D7CCC8");

        tabFavs.setOnClickListener(v -> {
            recyclerFavs.setVisibility(View.VISIBLE);
            recyclerComent.setVisibility(View.GONE);
            tabFavs.setBackgroundTintList(ColorStateList.valueOf(colorActivo));
            tabFavs.setTextColor(Color.WHITE);
            tabComent.setBackgroundTintList(ColorStateList.valueOf(colorInactivo));
            tabComent.setTextColor(Color.parseColor("#5D4037"));
        });

        tabComent.setOnClickListener(v -> {
            recyclerFavs.setVisibility(View.GONE);
            recyclerComent.setVisibility(View.VISIBLE);
            tabComent.setBackgroundTintList(ColorStateList.valueOf(colorActivo));
            tabComent.setTextColor(Color.WHITE);
            tabFavs.setBackgroundTintList(ColorStateList.valueOf(colorInactivo));
            tabFavs.setTextColor(Color.parseColor("#5D4037"));
        });

        // 7. CERRAR SESIÓN
        btnCerrarSesion.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            if (getActivity() != null) getActivity().finish();
        });

        // 8. BOTÓN VOLVER
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

        return view;
    }
}