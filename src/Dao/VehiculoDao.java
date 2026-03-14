package Dao;

import Model.Bus;
import Model.Buseta;
import Model.MicroBus;
import Model.Vehiculo;

import java.io.*;
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

    public List<Vehiculo> listarTodos() {
        return new ArrayList<>(lista);
    }


    public Vehiculo buscarPorPlaca(String placa) {
        for (Vehiculo v : lista) {
            if (v.getPlaca().equalsIgnoreCase(placa.trim())) {
                return v;
            }
        }
        return null;
    }

    public boolean actualizar(Vehiculo vehiculoActualizado) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getPlaca().equalsIgnoreCase(vehiculoActualizado.getPlaca())) {
                lista.set(i, vehiculoActualizado);
                escribirArchivo();
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String placa) {
        Vehiculo encontrado = buscarPorPlaca(placa);
        if (encontrado != null) {
            lista.remove(encontrado);
            escribirArchivo();
            return true;
        }
        return false;
    }

    private void escribirLinea(Vehiculo v) {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs(); // crea carpeta data/ si no existe

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(aLinea(v));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("[VehiculoDAO] Error al escribir línea: " + e.getMessage());
        }
    }

    private void escribirArchivo() {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Vehiculo v : lista) {
                bw.write(aLinea(v));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[VehiculoDAO] Error al reescribir archivo: " + e.getMessage());
        }
    }

    private String aLinea(Vehiculo v) {
        String tipo;
        if (v instanceof Buseta) {
            tipo = "BUSETA";
        } else if (v instanceof MicroBus) {
            tipo = "MICROBUS";
        } else if (v instanceof Bus) {
            tipo = "BUS";
        } else {
            tipo = "DESCONOCIDO";
        }

        return tipo + ";" +
                v.getPlaca() + ";" +
                v.getRuta() + ";" +
                v.getCapacidadMaxima() + ";" +
                v.getContadorPasajeros() + ";" +
                v.isDisponible() + ";" +
                v.getTarifaBase();
    }
}
