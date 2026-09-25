package com.example.cafeteria;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class CargadorDatos {
    public static void subir(Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Formato: Nombre, Direccion, Lat, Lng, Categoria, Likes, Imagen, Horario, Marcas, Tipo, Descripcion
        Object[][] datos = {
                {"Café Berna", "Calle Linares #947", -16.497741, -68.138120, "Abierto", 1540, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 22:00", "Lavazza, Copacabana", "Cafetería", "Un espacio acogedor ideal para desayunos clásicos y repostería artesanal. Su ambiente tranquilo lo hace perfecto para reuniones de trabajo o disfrutar de un buen libro."},
                {"Café con Coca", "Calle Linares 906", -16.497748, -68.138391, "Populares", 980, "https://images.unsplash.com/photo-1497935586351-b67a49e012bf", "09:00 - 21:00", "Buena Vista", "Cafetería", "Especialistas en la fusión de ingredientes andinos con café de altura. Destacan por sus bebidas innovadoras y un ambiente cultural que atrae a turistas y locales."},
                {"Alexander Coffee", "Av. 20 de octubre", -16.510410, -68.126195, "Abierto", 3450, "https://images.unsplash.com/photo-1509042239860-f550ce710b93", "07:30 - 23:00", "Starbucks, Juan Valdez", "Cafetería", "Una de las cadenas más reconocidas de la ciudad. Ofrece un servicio rápido, wifi veloz y un menú extenso que va desde frappés hasta platos fuertes."},
                {"El Caldero Chorreante", "Calle Linares N°906", -16.497830, -68.138150, "Populares", 1250, "https://images.unsplash.com/photo-1511920170033-f8396924c348", "10:00 - 22:00", "Café de los Yungas", "Cafetería", "Cafetería temática con decoración mágica. Famosa por sus bebidas creativas de colores y postres que parecen salidos de un cuento de fantasía."},
                {"Mugen Coffee", "Plaza Abaroa", -16.510345, -68.126201, "Abierto", 890, "https://images.unsplash.com/photo-1447933601403-0c6688de566e", "08:00 - 20:00", "Granos de Altura, Illy", "Tostaduría", "Un laboratorio de café para puristas. Tuestan sus propios granos a la vista del cliente y utilizan métodos de filtrado manuales como V60 y Chemex."},
                {"Experiment Coffee", "Av. 6 de Agosto esq. La Paz", -16.510900, -68.126800, "Populares", 1320, "https://images.unsplash.com/photo-1495474472201-3ce36f42cce8", "09:00 - 21:30", "Tostado de la casa", "Cafetería", "Lugar moderno de estética industrial. Famosos por su 'Cold Brew' extraído durante 24 horas y sus métodos experimentales de preparación."},
                {"Bolivian Coffee", "Av. Illampu", -16.495715, -68.140004, "Abierto", 2100, "https://images.unsplash.com/photo-1501339817309-158cd9e2d38e", "07:00 - 19:00", "Copacabana", "Tostaduría", "Tostaduría y tienda especializada que distribuye granos de Caranavi. El aroma inunda la calle; perfecto para comprar café en grano para llevar a casa."},
                {"Beirut", "Belisario Salinas 380", -16.509616, -68.126515, "Populares", 750, "https://images.unsplash.com/photo-1498804103079-a6351b050096", "10:00 - 23:00", "Café Arábica Libanés", "Cafetería", "Ofrece una exótica mezcla de cultura de Medio Oriente y calidez boliviana. Acompaña tu café tradicional con dulces árabes como el Baklava."},
                {"Theobroma", "Calle Andrés Nuñez", -16.512948, -68.127868, "Abierto", 1950, "https://images.unsplash.com/photo-1559925393-8be0ec4767c8", "08:30 - 20:30", "Grano Cacao & Café", "Tostaduría", "Maestros en la combinación de cacao y café. Sus bebidas de moca con chocolate de origen y granos recién tostados son una experiencia inolvidable."},
                {"Café Urbano", "Calle Fernando Guachalla", -16.504505, -68.122124, "Abierto", 1100, "https://images.unsplash.com/photo-1525610553991-2bede1a236e2", "08:00 - 22:00", "Lavazza, Nespresso", "Cafetería", "Ubicación céntrica con terrazas al aire libre. Ideal para el 'after-office' y para disfrutar de un Espresso Martini o un capuchino bien logrado."},
                {"The Abuelas Coffee Bar", "Calle Linares", -16.497676, -68.137991, "Populares", 3200, "https://images.unsplash.com/photo-1521017431206-ba753eb050b0", "11:00 - 21:00", "Typica, Caranavi", "Cafetería", "Cafetería boutique con diseño vintage y ambiente nostálgico. Destaca por su pastelería artesanal hecha con recetas familiares y tazas de cerámica antigua."},
                {"Tari- Café", "Calle Tarija", -16.498574, -68.137348, "Abierto", 1450, "https://images.unsplash.com/photo-1600093463592-8e36ae95ef56", "08:00 - 20:00", "Marcas Locales Tarijeñas", "Cafetería", "Un rincón pintoresco que trae lo mejor de la tradición del sur al centro de la ciudad. Destaca por acompañar sus bebidas calientes con panadería típica."}
        };

        for (Object[] fila : datos) {
            Map<String, Object> cafe = new HashMap<>();
            cafe.put("nombre", fila[0]);
            cafe.put("direccion", fila[1]);
            cafe.put("latitud", fila[2]);
            cafe.put("longitud", fila[3]);
            cafe.put("categoria", fila[4]);
            cafe.put("total_likes", fila[5]);
            cafe.put("imagen", fila[6]);
            cafe.put("horario", fila[7]);
            cafe.put("marcas", fila[8]);
            cafe.put("tipo", fila[9]);
            cafe.put("descripcion", fila[10]);

            db.collection("Cafeterias").add(cafe);
        }
        Toast.makeText(context, "Súper listado de cafeterías subido", Toast.LENGTH_LONG).show();
    }
}