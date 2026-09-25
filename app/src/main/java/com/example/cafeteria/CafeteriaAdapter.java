package com.example.cafeteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CafeteriaAdapter extends RecyclerView.Adapter<CafeteriaAdapter.ViewHolder> {

    private List<CafeteriaModelo> listaCafeterias;
    private OnItemClickListener listener;

    // ¡NUEVO! Una lista interna que recordará qué IDs son tus favoritos
    private List<String> idFavoritos = new ArrayList<>();

    public interface OnItemClickListener {
        void onInfoClick(CafeteriaModelo cafe);
    }

    public CafeteriaAdapter(List<CafeteriaModelo> listaCafeterias, OnItemClickListener listener) {
        this.listaCafeterias = listaCafeterias;
        this.listener = listener;

        // ¡EL TRUCO! Escuchamos los favoritos del usuario en vivo
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore.getInstance().collection("Usuarios").document(uid).collection("Favoritos")
                    .addSnapshotListener((value, error) -> {
                        if (error != null) return;
                        if (value != null) {
                            idFavoritos.clear();
                            // Guardamos solo los IDs de las cafeterías que te gustan
                            for (DocumentSnapshot doc : value.getDocuments()) {
                                idFavoritos.add(doc.getId());
                            }
                            // Le decimos a la lista que se repinte sola para actualizar los corazones
                            notifyDataSetChanged();
                        }
                    });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cafeteria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CafeteriaModelo cafe = listaCafeterias.get(position);

        holder.tvNombre.setText(cafe.getNombre());
        holder.tvLikes.setText(String.valueOf(cafe.getTotal_likes()));

        // Cargar imagen con Glide
        if (cafe.getImagen() != null && !cafe.getImagen().isEmpty()) {
            com.bumptech.glide.Glide.with(holder.itemView.getContext())
                    .load(cafe.getImagen())
                    .centerCrop()
                    .into(holder.imgCafe);
        }

        // ¿Está este ID en nuestra memoria de favoritos? Pintamos el corazón acorde
        if (idFavoritos.contains(cafe.getId())) {
            holder.btnCorazon.setText("❤️");
        } else {
            holder.btnCorazon.setText("🤍");
        }

        holder.btnInfo.setOnClickListener(v -> listener.onInfoClick(cafe));

        // Clic en el Corazón
        holder.btnCorazon.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Si NO está en favoritos, lo agregamos. Si ya está, lo borramos.
            // No necesitamos cambiar el texto manualmente aquí porque el SnapshotListener de arriba lo hará.
            if (!idFavoritos.contains(cafe.getId())) {
                db.collection("Usuarios").document(uid).collection("Favoritos").document(cafe.getId()).set(cafe);
                Toast.makeText(v.getContext(), "Agregado a tus favoritos", Toast.LENGTH_SHORT).show();
            } else {
                db.collection("Usuarios").document(uid).collection("Favoritos").document(cafe.getId()).delete();
                Toast.makeText(v.getContext(), "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCafeterias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvLikes, btnInfo, btnCorazon;
        ImageView imgCafe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.txt_nombre_cafe);
            tvLikes = itemView.findViewById(R.id.txt_likes);
            btnInfo = itemView.findViewById(R.id.btn_informacion);
            btnCorazon = itemView.findViewById(R.id.btn_corazon);
            imgCafe = itemView.findViewById(R.id.img_cafeteria);
        }
    }
}