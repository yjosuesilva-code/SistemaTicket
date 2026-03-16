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

}
