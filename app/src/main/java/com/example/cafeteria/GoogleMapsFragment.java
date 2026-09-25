package com.example.cafeteria;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapsFragment extends Fragment {

    private MapView map;
    private FirebaseFirestore db;
    private MyLocationNewOverlay locationOverlay;

    // Variables para la lista de abajo
    private RecyclerView recyclerDashboard;
    private CafeteriaAdapter adapter;
    private List<CafeteriaModelo> listaTop3;

    public GoogleMapsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Context ctx = requireActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(ctx.getPackageName());

        View view = inflater.inflate(R.layout.fragment_google_maps, container, false);

        // --- 1. CONFIGURAR EL MAPA (Cuadrito superior) ---
        map = view.findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.WIKIMEDIA);
        map.setBuiltInZoomControls(false); // Ocultamos los botones de zoom para que se vea más limpio
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(-16.5000, -68.1300);
        map.getController().setZoom(14.5);
        map.getController().setCenter(startPoint);

        db = FirebaseFirestore.getInstance();
        configurarUbicacionUsuario();
        cargarPinesMapa();

        // --- 2. CONFIGURAR LA LISTA INFERIOR (Top 3) ---
        recyclerDashboard = view.findViewById(R.id.recycler_dashboard);
        recyclerDashboard.setLayoutManager(new LinearLayoutManager(getContext()));
        listaTop3 = new ArrayList<>();

        adapter = new CafeteriaAdapter(listaTop3, cafe -> {
            // Al tocar una cafetería de la lista, abre sus detalles
            Fragment detalleFragment = new DetalleCafeteriaFragment(cafe);
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor_principal, detalleFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerDashboard.setAdapter(adapter);
        cargarTop3Cafeterias();

        // --- 3. CONFIGURAR EL BOTÓN "VER TODO" ---
        TextView btnVerTodo = view.findViewById(R.id.txt_ver_todo);
        btnVerTodo.setOnClickListener(v -> {
            if (getActivity() != null) {
                // Redirige al usuario a la pantalla grande de Cafeterías
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor_principal, new CafeteriasFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void configurarUbicacionUsuario() {
        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), map);
        locationOverlay.enableMyLocation();
        map.getOverlays().add(locationOverlay);
    }

    private void cargarPinesMapa() {
        db.collection("Cafeterias").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && getContext() != null) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    CafeteriaModelo cafe = document.toObject(CafeteriaModelo.class);
                    if (cafe.getLatitud() != 0 && cafe.getLongitud() != 0) {
                        GeoPoint punto = new GeoPoint(cafe.getLatitud(), cafe.getLongitud());
                        Marker marcador = new Marker(map);
                        marcador.setPosition(punto);
                        marcador.setTitle(cafe.getNombre());
                        marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

                        marcador.setOnMarkerClickListener((marker, mapView) -> {
                            marker.showInfoWindow();
                            return true;
                        });
                        map.getOverlays().add(marcador);
                    }
                }
                map.invalidate();
            }
        });
    }

    private void cargarTop3Cafeterias() {
        // Pedimos a Firebase que nos traiga solo 3 cafeterías (.limit(3))
        db.collection("Cafeterias")
                .limit(3)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listaTop3.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CafeteriaModelo cafe = document.toObject(CafeteriaModelo.class);
                            cafe.setId(document.getId());
                            listaTop3.add(cafe);
                        }
                        adapter.notifyDataSetChanged(); // Refrescar la lista visual
                    } else {
                        Log.e("Firebase", "Error al cargar el Top 3", task.getException());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (map != null) map.onResume();
        if (locationOverlay != null) locationOverlay.enableMyLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (map != null) map.onPause();
        if (locationOverlay != null) locationOverlay.disableMyLocation();
    }
}