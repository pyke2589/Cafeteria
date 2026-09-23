package com.example.cafeteria;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class CargadorDatos {
    public static void subir(Context context) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Object[][] datos = {
                {"Café Berna","Calle Linares #947",-16.497741,-68.13812,"Cerca de ti",120},
                {"CAFE CON COCA","Calle Linares 906",-16.497748,-68.138391,"Populares",240},
                {"Alexander Coffe","Av. 20 de octubre",-16.51041,-68.126195,"Abierto",360},
                {"El caldero chorreante","Calle Linares N°906",-16.49783,-68.13815,"Cerca de ti",480},
                {"Mugen coffe","Av 20 de octubre Plaza Abaroa",-16.510345,-68.126201,"Populares",600},
                {"Experiment Coffe","Av.6 de Agosto esquina, La Paz",-16.5109,-68.1268,"Abierto",720},
                {"Whit coffi","Avenida arce edificio medio centro",-16.509493,-68.125823,"Cerca de ti",840},
                {"Bolivian Cofee","Avillampu",-16.495715,-68.140004,"Populares",960},
                {"Beirut","Belisario Salinas 380",-16.509616,-68.126515,"Abierto",1080},
                {"Crazy Market","Calle Tarija",-16.498618,-68.137434,"Cerca de ti",1200},
                {"Theobroma","Calle Andrés Nuñez",-16.512948,-68.127868,"Populares",1320},
                {"Tari- Café","Calle Tarija",-16.498574,-68.137348,"Abierto",1440},
                {"Cafe Urbano","Calle fernando guachalla",-16.504505,-68.122124,"Cerca de ti",1560},
                {"The abuelas coffe bar","Calle linares",-16.497676,-68.137991,"Populares",1680},
                {"Coca y Cafe drinks and food","Calle Linares 906",-16.497609,-68.138153,"Abierto",1800},
                {"Lunas","Calle sagarnaga",-16.49849,-68.137605,"Cerca de ti",120},
                {"Almara","Calle ecuador",-16.504505,-68.122124,"Populares",240},
                {"Cafe restaurante Angelo Colonial","Calle Linares",-16.497833,-68.138111,"Abierto",360},
                {"café del mundo","Calle Sagarnaga N’ 324",-16.497462,-68.13858,"Cerca de ti",480},
                {"Click","Av. 20 octubre",-16.509079,-68.127592,"Populares",600},
                {"Era café y tiempo","Av.20 de octubre",-16.509672,-68.126987,"Abierto",720},
                {"Cafe vida","Pasaje Juan XXIII 187",-16.498856,-68.137363,"Cerca de ti",840},
                {"Sultana","Av Ecuador y Belisario Salinas",-16.512423,-68.129493,"Populares",960},
                {"Rugbyes","Calle Colombia",-16.501122,-68.13452,"Abierto",1080},
                {"Mil delicias","Calle Colombia",-16.501068,-68.134621,"Cerca de ti",1200},
                {"Kuchen stube","Rosendo Gutiérrez",-16.509243,-68.12777,"Abierto",1440},
                {"La propia empanada","Rosendo Gutiérrez",-16.509479,-68.128063,"Cerca de ti",1560},
                {"Coffeeeology","Av. 6 de agosto, edificio 2460",-16.504505,-68.140916,"Populares",1680},
                {"The Coffee.25","Av. Arce edificio santa isabel",-16.509969,-68.123459,"Cerca de ti",120},
                {"Mugen Coffee Project","Plaza Avaroa",-16.5,-68.1298,"Populares",240}
        };

        for (Object[] fila : datos) {
            Map<String, Object> cafe = new HashMap<>();
            cafe.put("nombre", fila[0]);
            cafe.put("direccion", fila[1]);
            cafe.put("latitud", fila[2]);
            cafe.put("longitud", fila[3]);
            cafe.put("categoria", fila[4]);
            cafe.put("total_likes", fila[5]);

            db.collection("Cafeterias").add(cafe);
        }

        Toast.makeText(context, "¡Cafeterías subidas a Firebase!", Toast.LENGTH_LONG).show();
    }
}