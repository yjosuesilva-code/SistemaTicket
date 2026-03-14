package Dao;

import Model.Vehiculo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDao {
    private static final String RUTA_ARCHIVO = "data/vehiculos.txt";
    private List<Vehiculo> lista;

    public VehiculoDao() {
        lista = new ArrayList<>();
        cargarTodos();
    }

    public void guardar(Vehiculo v) {
        lista.add(v);
        escribirLinea(v);
    }

    public List<Vehiculo> cargarTodos() {
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
                    Vehiculo v = parsearLinea(linea);
                    if (v != null) {
                        lista.add(v);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[VehiculoDAO] Error al leer el archivo: " + e.getMessage());
        }

        return lista;
    }
}
