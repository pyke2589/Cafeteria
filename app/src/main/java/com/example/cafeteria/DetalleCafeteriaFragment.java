package com.example.cafeteria;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetalleCafeteriaFragment extends Fragment {

    private CafeteriaModelo cafe;
    private FirebaseFirestore db;
    private ComentarioAdapter adapter;
    private List<ComentarioModelo> listaComentarios;

    public DetalleCafeteriaFragment(CafeteriaModelo cafe) {
        this.cafe = cafe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_cafeteria, container, false);

        db = FirebaseFirestore.getInstance();

        // ELEMENTOS UI
        ImageView btnVolver = view.findViewById(R.id.btn_volver_detalle);
        TextView tvNombre = view.findViewById(R.id.txt_detalle_nombre);
        TextView tvDireccion = view.findViewById(R.id.txt_detalle_direccion);
        TextView tvDescripcion = view.findViewById(R.id.txt_detalle_descripcion);

        TextView tabInfo = view.findViewById(R.id.tab_informacion);
        TextView tabComent = view.findViewById(R.id.tab_comentarios);
        LinearLayout layoutInfo = view.findViewById(R.id.layout_informacion);
        LinearLayout layoutComent = view.findViewById(R.id.layout_comentarios);

        RecyclerView recycler = view.findViewById(R.id.recycler_comentarios);
        EditText inputComentario = view.findViewById(R.id.input_nuevo_comentario);
        Button btnEnviar = view.findViewById(R.id.btn_enviar_comentario);

        // ELEMENTOS UI (Añadimos el banner)
        ImageView imgBanner = view.findViewById(R.id.img_banner_cafe);

        // CONFIGURAR DATOS

        tvNombre.setText(cafe.getNombre());          // ¡Añade esta línea!
        tvDireccion.setText(cafe.getDireccion());

        if (cafe.getImagen() != null && !cafe.getImagen().isEmpty()) {
            com.bumptech.glide.Glide.with(getContext())
                    .load(cafe.getImagen())
                    .centerCrop()
                    .into(imgBanner);
        }

        // Armamos la descripción con los nuevos datos del Excel
        String detallesCompletos =
                "🏪 Tipo: " + cafe.getTipo() + " (" + cafe.getCategoria() + ")\n" +
                        "🕒 Horario: " + cafe.getHorario() + "\n" +
                        "🏷️ Marcas de café: " + cafe.getMarcas() + "\n\n" +
                        "☕ Sobre el Café:\n" + cafe.getDescripcion() + "\n\n" +
                        "📍 Ubicación exacta:\nLatitud: " + cafe.getLatitud() + "\nLongitud: " + cafe.getLongitud();

        tvDescripcion.setText(detallesCompletos);

        // CONFIGURAR RECYCLER COMENTARIOS
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        listaComentarios = new ArrayList<>();
        adapter = new ComentarioAdapter(listaComentarios);
        recycler.setAdapter(adapter);

        // BOTÓN VOLVER
        btnVolver.setOnClickListener(v -> {
            if (getActivity() != null) getActivity().getSupportFragmentManager().popBackStack();
        });

        // ALTERNAR PESTAÑAS
        int colorActivo = Color.parseColor("#8D6E63");
        int colorInactivo = Color.parseColor("#D7CCC8");

        tabInfo.setOnClickListener(v -> {
            layoutInfo.setVisibility(View.VISIBLE);
            layoutComent.setVisibility(View.GONE);
            tabInfo.setBackgroundTintList(ColorStateList.valueOf(colorActivo));
            tabInfo.setTextColor(Color.WHITE);
            tabComent.setBackgroundTintList(ColorStateList.valueOf(colorInactivo));
            tabComent.setTextColor(Color.parseColor("#5D4037"));
        });

        tabComent.setOnClickListener(v -> {
            layoutInfo.setVisibility(View.GONE);
            layoutComent.setVisibility(View.VISIBLE);
            tabComent.setBackgroundTintList(ColorStateList.valueOf(colorActivo));
            tabComent.setTextColor(Color.WHITE);
            tabInfo.setBackgroundTintList(ColorStateList.valueOf(colorInactivo));
            tabInfo.setTextColor(Color.parseColor("#5D4037"));
        });
        
        // CONFIGURAR BOTÓN FAVORITO (Corazón)
        ImageView btnFavorito = view.findViewById(R.id.btn_favorito_detalle);
        String uid = FirebaseAuth.getInstance().getCurrentUser() != null ? 
                     FirebaseAuth.getInstance().getCurrentUser().getUid() : null;
                     
        if (uid != null) {
            // Verificar si ya es favorito al cargar
            db.collection("Usuarios").document(uid).collection("Favoritos").document(cafe.getId())
                .get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        btnFavorito.setColorFilter(Color.RED); // Está en favoritos
                    } else {
                        btnFavorito.setColorFilter(Color.parseColor("#BCAAA4")); // No está en favoritos
                    }
                });

            // Acción al hacer clic en el corazón
            btnFavorito.setOnClickListener(v -> {
                db.collection("Usuarios").document(uid).collection("Favoritos").document(cafe.getId())
                    .get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Ya era favorito -> Lo quitamos
                            db.collection("Usuarios").document(uid).collection("Favoritos").document(cafe.getId()).delete();
                            btnFavorito.setColorFilter(Color.parseColor("#BCAAA4"));
                            Toast.makeText(getContext(), "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                        } else {
                            // No era favorito -> Lo agregamos
                            db.collection("Usuarios").document(uid).collection("Favoritos").document(cafe.getId()).set(cafe);
                            btnFavorito.setColorFilter(Color.RED);
                            Toast.makeText(getContext(), "Agregado a favoritos", Toast.LENGTH_SHORT).show();
                        }
                    });
            });
        }

        // CARGAR COMENTARIOS
        cargarComentarios();

        // ENVIAR COMENTARIO
        btnEnviar.setOnClickListener(v -> {
            String texto = inputComentario.getText().toString().trim();
            if (texto.isEmpty()) return;

            String userId = FirebaseAuth.getInstance().getCurrentUser() != null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : null;
            if (userId == null) return;

            db.collection("Usuarios").document(userId).get().addOnSuccessListener(documentSnapshot -> {
                String nombreAutor = documentSnapshot.getString("nombre_completo");
                if (nombreAutor == null) nombreAutor = "Usuario";

                // 1. Guardamos para la Cafetería (Autor = Tu Nombre)
                ComentarioModelo comentCafe = new ComentarioModelo(nombreAutor, texto);
                db.collection("Cafeterias").document(cafe.getId()).collection("Comentarios").add(comentCafe);

                // 2. Guardamos una copia para tu Perfil (Autor = Nombre de la Cafetería)
                ComentarioModelo comentPerfil = new ComentarioModelo("☕ En: " + cafe.getNombre(), texto);
                db.collection("Usuarios").document(userId).collection("MisComentarios").add(comentPerfil);

                inputComentario.setText("");
                Toast.makeText(getContext(), "Comentario enviado", Toast.LENGTH_SHORT).show();
            });
        });

        return view;
    }

    private void cargarComentarios() {
        // addSnapshotListener actualiza la lista en vivo si alguien comenta
        db.collection("Cafeterias").document(cafe.getId()).collection("Comentarios")
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    if (value != null) {
                        listaComentarios.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            ComentarioModelo coment = doc.toObject(ComentarioModelo.class);
                            listaComentarios.add(coment);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}