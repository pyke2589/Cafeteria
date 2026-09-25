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
                {"Typica", "Av 6 de agosto", -16.511184, -68.124183, "Abierto", 785, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Typica", "Av 6 de agosto esquina Gutiérrez", -16.51104, -68.124366, "Abierto", 2607, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Coffe nation", "Av 6 de agosto", -16.510674, -68.124813, "Abierto", 456, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Amaroma", "Av 6 de agosto", -16.510482, -68.124929, "Abierto", 1387, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Café chulumani Genaro Ramos", "Av. Hernando siles, Entre Calle 1 y 2", -16.523534, -68.113149, "Abierto", 780, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"El Parlamento historia café", "Calle Comercio esquina Colón 1280", -16.496559, -68.132938, "Abierto", 3439, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Otoño café y vinos", "Calle José María Zalles", -16.5448, -68.07883, "Abierto", 711, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Supra coffe", "Calle José María Zalles", -16.5448, -68.07883, "Abierto", 1895, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Cafe Alexander", "José María Aguirre Acha, casi esquina calle 25 los pinos", -16.545327, -68.069562, "Abierto", 418, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Cafetería Juan Valdez", "Calle José María Aguirre Acha,", -16.545331, -68.070382, "Abierto", 2739, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Hamacas empresa de alimentos", "Edif. Torre Galeno #78 calle Hugo estrada", -16.498174, -68.122713, "Abierto", 1757, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Sweet sensitive pasteleria", "Calle 6 entre avenida Jose Aguirre y Avenida Costanera", -16.545566, -68.072675, "Abierto", 2012, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"El parlamento", "Avenida montenegro", -16.54538, -68.07936, "Abierto", 2313, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Alexander Coffee", "Av. Montenegro", -16.543922, -68.080953, "Abierto", 1698, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Café con Pan", "Av. MCL de Montenegro", -16.540043, -68.076262, "Abierto", 1354, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Café con Pan", "entre la 22 y la 23 de calacoto", -16.540125, -68.076496, "Abierto", 1708, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Alexander coffee", "Avenida José Aguirre - hipermaxi", -16.545596, -68.070345, "Abierto", 2381, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Typica café y tostaduria", "Plaza Mall 21", -16.541631, -68.076636, "Abierto", 2065, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Tostaduría", "Un excelente lugar para compartir y disfrutar."},
                {"Alexander coffee", "Calle 21", -16.54422, -68.07702, "Abierto", 1436, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Café Esencia", "Av. Montenegro", -16.545079, -68.080591, "Abierto", 2791, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Supra Coffee", "Enrique Peñaranda", -16.54524, -68.079411, "Abierto", 521, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Madame brunch & bakery", "Enrique Peñaranda", -16.544945, -68.079532, "Abierto", 1275, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Otoño Café & Vinos", "José María Zalles", -16.544752, -68.078891, "Abierto", 1968, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Barbecue - café restaurant", "https://maps.app.goo.gl/fqgaqQSG8hFhNwLaA?g_st=aw", -16.5308, -68.0714, "Abierto", 2043, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Kangurù", "Enrique Peñaranda", -16.544813, -68.079421, "Abierto", 2730, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Jirafas", "Av. Gral José Ballivian", -16.538764, -68.079123, "Abierto", 3204, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Luva", "Gabriel René Moreno", -16.543981, -68.079977, "Abierto", 659, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"High Coffee", "Jaime Mendoza", -16.544532, -68.079779, "Abierto", 617, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Arcadia", "Calle general Inofuentes", -16.537702, -68.074878, "Abierto", 2998, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Caliente", "Calle 18 de calacoto", -16.538071, -68.081623, "Abierto", 912, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Vainilla Bistro", "FW58+V93, Calle 15, La Paz", -16.540319, -68.084055, "Abierto", 2323, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Cafecito ToGo", "Cafecito to go Calacoto C.15, 0000, La Paz", -16.539801, -68.084188, "Abierto", 3138, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Bru coffee", "Esquina Avenida hernando siles y calle 7", -16.526067, -68.108687, "Abierto", 1109, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"TOFFEE", "Calle 9 Gral. Ángel Babia entre av costanera y av los sauces # 1000", -16.544917, -68.088857, "Abierto", 1089, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Reposteria casera", "Avenida hernando siles calle 9 edificio zodiaco", -16.526228, -68.108301, "Abierto", 1785, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"ANATOMIA Café & Bistro", "Av los sauces al lado de HA resonancia magnetica 3.0T #340", -16.544537, -68.088204, "Abierto", 1120, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Mi Jhoyita", "Elizardo Pérez entre Av. Max Portugal Zamora y Agar Ferreira", -16.524087, -68.10225, "Abierto", 1072, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Cafetería Elis", "Av. 16 de julio entre Bueno y Federico Zuazo", -16.501088, -68.132811, "Abierto", 1738, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Alexander coffe", "Calle 21 de calacoto entre avenida costanera", -16.54475, -68.077374, "Abierto", 2352, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Coccos", "Federico Zuazo entre Reyes Ortiz y Bueno", -16.502891, -68.130286, "Abierto", 413, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Typica", "Av. Batallón colorados entre Av. 16 de julio y Fderico Zuazo", -16.503239, -68.130286, "Abierto", 1998, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Jawitas mi chulumani", "Calle 21 entre avenida costanera", -16.545016, -68.076629, "Abierto", 2098, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Coffee & Go", "Av. Tomasa Murillo en plena esquina de la calle 5", -16.535652, -68.079402, "Abierto", 1639, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Typica cafe", "Avenida saavedra edifico bella flor", -16.503039, -68.122088, "Abierto", 68, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Hay Cafe", "Achumani Cerca plaza de la amistad Calle 22", -16.529686, -68.070462, "Abierto", 797, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Pomas cake", "Genaro gamarra", -16.500862, -68.121499, "Abierto", 1243, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Casero by Typica Panaderia", "Av Gobles esq calle 12 (Irpavi) # 6299", -16.518766, -68.085616, "Abierto", 329, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Typica Café Tostaduria", "Achumani entre calle 16 , Calle Francisco Javier Iriarte", -16.531838, -68.072532, "Abierto", 2917, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"RetroGusto", "Av braulio vera entre calle 5 y 6 #6647", -16.524645, -68.085512, "Abierto", 2074, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"ESENCIA", "Calle 5 entre calle Pablo sanchez y altamirano #700", -16.52556, -68.086264, "Abierto", 3428, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Babilonia", "calle Miguel quenayato, calle 10", -16.534092, -68.07572, "Abierto", 3226, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"k-fe la mía aroma", "calle 12 Miguel quenallate", -16.533483, -68.075485, "Abierto", 1836, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Café en ruta", "Av García lanza", -16.531488, -68.0752, "Abierto", 3101, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Selah", "Esquina entre Belisario Salinas - Beniceto Arce", -16.507429, -68.125028, "Abierto", 1462, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Teo Cat", "Calle Montevideo - Calle Capitán Ravelo", -16.505245, -68.127379, "Abierto", 706, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Uta cafe", "Av Alexander esquina la paz", -16.535534, -68.074069, "Abierto", 966, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Vainilla coffe company", "Av Alexander", -16.534414, -68.072412, "Abierto", 486, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Alexander coffe", "Av Alexander", -16.534299, -68.07194, "Abierto", 2217, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Juan valdez", "Av the strongest", -16.519071, -68.062496, "Abierto", 3265, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Café bronze", "Av. illimani. Plaza Tomás Frías #1570", -16.497366, -68.130658, "Abierto", 2886, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Roaster", "esq plaza Isabel la católica", -16.509338, -68.124717, "Abierto", 1265, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Coffeeology", "av 6 de agosto", -16.509727, -68.125472, "Abierto", 1483, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."},
                {"Café Andre", "Final pasaje caracas", -16.508719, -68.126208, "Abierto", 1182, "https://images.unsplash.com/photo-1554118811-1e0d58224f24", "08:00 - 20:00", "Local", "Cafetería", "Un excelente lugar para compartir y disfrutar."}
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