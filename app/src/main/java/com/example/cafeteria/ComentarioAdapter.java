package com.example.cafeteria;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {

    private List<ComentarioModelo> listaComentarios;
    private boolean esPerfil;

    public ComentarioAdapter(List<ComentarioModelo> listaComentarios, boolean esPerfil) {
        this.listaComentarios = listaComentarios;
        this.esPerfil = esPerfil;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ComentarioModelo comentario = listaComentarios.get(position);

        holder.tvAutor.setText(comentario.getAutor());
        holder.tvTexto.setText(comentario.getTexto());

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseAuth.getInstance().getCurrentUser().getUid() : "";

        if (currentUserId.equals(comentario.getIdUsuario())) {
            holder.itemView.setOnLongClickListener(v -> {
                mostrarOpciones(v.getContext(), comentario);
                return true;
            });
        } else {
            holder.itemView.setOnLongClickListener(null);
        }
    }

    private void mostrarOpciones(Context context, ComentarioModelo comentario) {
        String[] opciones = {"✏️ Editar", "🗑️ Eliminar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Opciones de comentario");
        builder.setItems(opciones, (dialog, which) -> {
            if (which == 0) {
                mostrarDialogoEditar(context, comentario);
            } else if (which == 1) {
                eliminarComentario(context, comentario);
            }
        });
        builder.show();
    }

    private void mostrarDialogoEditar(Context context, ComentarioModelo comentario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar Comentario");

        final EditText input = new EditText(context);
        input.setText(comentario.getTexto());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevoTexto = input.getText().toString().trim();
            if (!nuevoTexto.isEmpty()) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if (esPerfil) {
                    db.collection("Usuarios").document(uid).collection("MisComentarios")
                            .document(comentario.getIdComentario()).update("texto", nuevoTexto);
                } else {
                    db.collection("Cafeterias").document(comentario.getIdCafeteria())
                            .collection("Comentarios").document(comentario.getIdComentario()).update("texto", nuevoTexto);
                }
                MensajesCoffee.mostrar(context, "Comentario actualizado ✏️");
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void eliminarComentario(Context context, ComentarioModelo comentario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (esPerfil) {
            db.collection("Usuarios").document(uid).collection("MisComentarios")
                    .document(comentario.getIdComentario()).delete();
        } else {
            db.collection("Cafeterias").document(comentario.getIdCafeteria())
                    .collection("Comentarios").document(comentario.getIdComentario()).delete();
        }
        MensajesCoffee.mostrar(context, "Comentario eliminado 🗑️");
    }

    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAutor, tvTexto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAutor = itemView.findViewById(R.id.txt_autor_coment);
            tvTexto = itemView.findViewById(R.id.txt_texto_coment);
        }
    }
}