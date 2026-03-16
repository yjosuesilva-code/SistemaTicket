package Dao;

import Model.Conductor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConductorDao {
    private static final String RUTA_ARCHIVO = "data/conductores.txt";
    private static final int CAMPOS = 4;
    private List<Conductor> lista;

    public ConductorDao() {
        lista = new ArrayList<>();
        cargarTodos();
    }

    public void guardar(Conductor c) {
        lista.add(c);
        escribirLinea(c);
    }

    public List<Conductor> cargarTodos() {
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
                    Conductor c = parsearLinea(linea);
                    if (c != null) {
                        lista.add(c);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[ConductorDAO] Error al leer el archivo: " + e.getMessage());
        }

        return lista;
    }

    public List<Conductor> listarTodos() {
        return new ArrayList<>(lista);
    }

}
