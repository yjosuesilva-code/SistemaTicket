package Dao;

import Model.Bus;
import Model.Buseta;
import Model.MicroBus;
import Model.Vehiculo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDao {
    private static final String RUTA_BUSETA   = "data/buseta.txt";
    private static final String RUTA_MICROBUS = "data/microbus.txt";
    private static final String RUTA_BUS      = "data/bus.txt";
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
        cargarArchivo(RUTA_BUSETA);
        cargarArchivo(RUTA_MICROBUS);
        cargarArchivo(RUTA_BUS);
        return lista;
    }

    private void cargarArchivo(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    Vehiculo v = parsearLinea(linea);
                    if (v != null) lista.add(v);
                }
            }
        } catch (IOException e) {
            System.err.println("[VehiculoDAO] Error al leer " + ruta + ": " + e.getMessage());
        }
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
        String ruta = getRuta(v);
        File archivo = new File(ruta);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(aLinea(v));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("[VehiculoDAO] Error al escribir linea: " + e.getMessage());
        }
    }
    private String getRuta(Vehiculo v) {
        if (v instanceof Buseta)   return RUTA_BUSETA;
        if (v instanceof MicroBus) return RUTA_MICROBUS;
        return RUTA_BUS;
    }

    private void escribirArchivos() {
        reescribirArchivo(RUTA_BUSETA,   Buseta.class);
        reescribirArchivo(RUTA_MICROBUS, MicroBus.class);
        reescribirArchivo(RUTA_BUS,      Bus.class);
    }
    private void reescribirArchivo(String ruta, Class<?> tipo) {
        File archivo = new File(ruta);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Vehiculo v : lista) {
                if (tipo.isInstance(v)) {
                    bw.write(aLinea(v));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("[VehiculoDAO] Error al reescribir " + ruta + ": " + e.getMessage());
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

    private Vehiculo parsearLinea(String linea) {
        String[] partes = linea.split(";");

        if (partes.length != 7) {
            System.err.println("[VehiculoDAO] Línea con formato incorrecto: " + linea);
            return null;
        }

        try {
            String tipo             = partes[0].trim().toUpperCase();
            String placa            = partes[1].trim();
            String ruta             = partes[2].trim();
            int    capacidadMaxima  = Integer.parseInt(partes[3].trim());
            int    contadorPasajeros= Integer.parseInt(partes[4].trim());
            boolean disponible      = Boolean.parseBoolean(partes[5].trim());
            double tarifaBase       = Double.parseDouble(partes[6].trim());

            Vehiculo v;

            switch (tipo) {
                case "BUSETA":
                    v = new Buseta(placa, ruta);
                    break;
                case "MICROBUS":
                    v = new MicroBus(placa, ruta);
                    break;
                case "BUS":
                    v = new Bus(placa, ruta);
                    break;
                default:
                    System.err.println("[VehiculoDAO] Tipo de vehículo desconocido: " + tipo);
                    return null;
            }

            v.setContadorPasajeros(contadorPasajeros);
            v.setDisponible(disponible);
            v.setTarifaBase(tarifaBase);

            return v;

        } catch (NumberFormatException e) {
            System.err.println("[VehiculoDAO] Error al parsear números en línea: " + linea);
            return null;
        }
    }

    @Override
    public String toString() {
        return "VehiculoDAO{" +
                "archivo='" + RUTA_ARCHIVO + '\'' +
                ", totalVehiculos=" + lista.size() +
                '}';
    }
}
