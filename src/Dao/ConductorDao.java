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

    public Conductor buscarPorCedula(String cedula) {
        for (Conductor c : lista) {
            if (c.getCedula().equalsIgnoreCase(cedula.trim())) {
                return c;
            }
        }
        return null;
    }

    public Conductor buscarPorLicencia(String numLicencia) {
        for (Conductor c : lista) {
            if (c.getNumLicencia().equalsIgnoreCase(numLicencia.trim())) {
                return c;
            }
        }
        return null;
    }

    public List<Conductor> listarPorCategoria(String categoria) {
        List<Conductor> resultado = new ArrayList<>();
        for (Conductor c : lista) {
            if (c.getCateLicencia().equalsIgnoreCase(categoria.trim())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public boolean actualizar(Conductor conductorActualizado) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCedula().equalsIgnoreCase(conductorActualizado.getCedula())) {
                lista.set(i, conductorActualizado);
                escribirArchivo();
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String cedula) {
        Conductor encontrado = buscarPorCedula(cedula);
        if (encontrado != null) {
            lista.remove(encontrado);
            escribirArchivo();
            return true;
        }
        return false;
    }

    private void escribirLinea(Conductor c) {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(aLinea(c));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("[ConductorDAO] Error al escribir línea: " + e.getMessage());
        }
    }

    private void escribirArchivo() {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Conductor c : lista) {
                bw.write(aLinea(c));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[ConductorDAO] Error al reescribir archivo: " + e.getMessage());
        }
    }

    private String aLinea(Conductor c) {
        return c.getCedula() + ";" +
                c.getNombre() + ";" +
                c.getNumLicencia() + ";" +
                c.getCateLicencia();
    }

    private Conductor parsearLinea(String linea) {
        String[] partes = linea.split(";");

        if (partes.length != CAMPOS) {
            System.err.println("[ConductorDAO] Línea con formato incorrecto ("
                    + partes.length + " campos): " + linea);
            return null;
        }

        String cedula = partes[0].trim();
        String nombre = partes[1].trim();
        String numLicencia = partes[2].trim();
        String categoriaLicencia = partes[3].trim();

        return new Conductor(cedula, nombre, numLicencia, categoriaLicencia);
    }

    @Override
    public String toString() {
        return "ConductorDAO{" +
                "archivo='" + RUTA_ARCHIVO + '\'' +
                ", totalConductores=" + lista.size() +
                '}';
    }

}
