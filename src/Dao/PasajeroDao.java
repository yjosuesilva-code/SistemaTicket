package Dao;

import Model.Pasajero;
import Model.PasajeroRegular;
import Model.PasajeroEstudiante;
import Model.PasajeroAdultoMayor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PasajeroDao {

    private static final String RUTA_ARCHIVO = "data/pasajeros.txt";

    private static final String TIPO_REGULAR      = "REGULAR";
    private static final String TIPO_ESTUDIANTE   = "ESTUDIANTE";
    private static final String TIPO_ADULTO_MAYOR = "ADULTO_MAYOR";

    private List<Pasajero> lista;

    public PasajeroDao() {
        lista = new ArrayList<>();
        cargarTodos();
    }

    public void guardar(Pasajero p) {
        lista.add(p);
        escribirLinea(p);
    }

    public List<Pasajero> cargarTodos() {
        lista.clear();
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    Pasajero p = parsearLinea(linea);
                    if (p != null) {
                        lista.add(p);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[PasajeroDAO] Error al leer el archivo: " + e.getMessage());
        }

        return lista;
    }

    public List<Pasajero> listarTodos() {
        return new ArrayList<>(lista);
    }

}
