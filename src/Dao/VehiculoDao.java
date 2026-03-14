package Dao;

import Model.Vehiculo;

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
}
