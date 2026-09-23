package com.example.cafeteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CafeteriaAdapter extends RecyclerView.Adapter<CafeteriaAdapter.ViewHolder> {

    private List<CafeteriaModelo> listaCafeterias;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onInfoClick(CafeteriaModelo cafe);
    }

    public CafeteriaAdapter(List<CafeteriaModelo> listaCafeterias, OnItemClickListener listener) {
        this.listaCafeterias = listaCafeterias;
        this.listener = listener;
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

        // Clic en Información
        holder.btnInfo.setOnClickListener(v -> listener.onInfoClick(cafe));

        // Clic en el Corazón
        holder.btnCorazon.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            if (holder.btnCorazon.getText().toString().equals("🤍")) {
                // Dar Like: Cambiamos a rojo y guardamos en Firebase
                holder.btnCorazon.setText("❤️");
                db.collection("Usuarios").document(uid).collection("Favoritos").document(cafe.getId()).set(cafe);
                Toast.makeText(v.getContext(), "Agregado a tus favoritos", Toast.LENGTH_SHORT).show();
            } else {
                // Quitar Like: Cambiamos a blanco y borramos de Firebase
                holder.btnCorazon.setText("🤍");
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.txt_nombre_cafe);
            tvLikes = itemView.findViewById(R.id.txt_likes);
            btnInfo = itemView.findViewById(R.id.btn_informacion);
            btnCorazon = itemView.findViewById(R.id.btn_corazon); // NUEVO
        }
    }
}