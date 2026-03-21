package Dao;

import Model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDao {
    private static final String RUTA_ARCHIVO = "data/reservas.txt";
    private static final int    CAMPOS       = 12;
    private List<Reserva> lista;

    public ReservaDao() {
        lista = new ArrayList<>();
        cargarTodos();
    }
    public void guardar(Reserva r) {
        lista.add(r);
        escribirLinea(r);
    }
    public List<Reserva> cargarTodos() {
        lista.clear();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    Reserva r = parsearLinea(linea);
                    if (r != null) lista.add(r);
                }
            }
        } catch (IOException e) {
            System.err.println("[ReservaDao] Error al leer archivo: " + e.getMessage());
        }
        return lista;
    }
    public List<Reserva> listarTodos() {
        return new ArrayList<>(lista);
    }
    public List<Reserva> listarActivas() {
        List<Reserva> activas = new ArrayList<>();
        for (Reserva r : lista)
            if (r.getEstado() == Reserva.Estado.ACTIVA) activas.add(r);
        return activas;
    }
    public Reserva buscarPorCodigo(String codigo) {
        for (Reserva r : lista)
            if (r.getCodigo().equalsIgnoreCase(codigo.trim())) return r;
        return null;
    }
    public List<Reserva> buscarPorPasajero(String cedula) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : lista)
            if (r.getPasajero().getCedula().equalsIgnoreCase(cedula.trim())) resultado.add(r);
        return resultado;
    }
    public int contarActivasPorVehiculoYFecha(String placa, LocalDate fechaViaje) {
        int count = 0;
        for (Reserva r : lista) {
            if (r.getEstado() == Reserva.Estado.ACTIVA
                    && r.getVehiculo().getPlaca().equalsIgnoreCase(placa)
                    && r.getFechaViaje().equals(fechaViaje)) {
                count++;
            }
        }
        return count;
    }
    public boolean actualizarEstado(String codigo, Reserva.Estado nuevoEstado) {
        for (Reserva r : lista) {
            if (r.getCodigo().equalsIgnoreCase(codigo)) {
                r.setEstado(nuevoEstado);
                escribirArchivo();
                return true;
            }
        }
        return false;
    }
    private void escribirLinea(Reserva r) {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(aLinea(r));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("[ReservaDao] Error al escribir línea: " + e.getMessage());
        }
    }
    private void escribirArchivo() {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Reserva r : lista) {
                bw.write(aLinea(r));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[ReservaDao] Error al reescribir archivo: " + e.getMessage());
        }
    }
    private String aLinea(Reserva r) {
        String tipoVehiculo;
        if      (r.getVehiculo() instanceof Buseta)   tipoVehiculo = "BUSETA";
        else if (r.getVehiculo() instanceof MicroBus) tipoVehiculo = "MICROBUS";
        else                                           tipoVehiculo = "BUS";

        return r.getCodigo()                            + ";" +
                r.getPasajero().getCedula()              + ";" +
                r.getPasajero().getNombre()              + ";" +
                r.getPasajero().getTipoPasajero()        + ";" +
                r.getPasajero().getFechaNacimiento()     + ";" +
                r.getVehiculo().getPlaca()               + ";" +
                tipoVehiculo                             + ";" +
                r.getVehiculo().getRuta()                + ";" +
                r.getVehiculo().getTarifaBase()          + ";" +
                r.getFechaCreacion().toString()          + ";" +
                r.getFechaViaje().toString()             + ";" +
                r.getEstado().toString();
    }
    private Reserva parsearLinea(String linea) {
        String[] p = linea.split(";");
        if (p.length != CAMPOS) {
            System.err.println("[ReservaDao] Línea con formato incorrecto (" + p.length + " campos): " + linea);
            return null;
        }
        try {
            String    codigo          = p[0].trim();
            String    cedula          = p[1].trim();
            String    nombre          = p[2].trim();
            String    tipoPasajero    = p[3].trim().toUpperCase();
            LocalDate fechaNacimiento = LocalDate.parse(p[4].trim());

            Pasajero pasajero;
            switch (tipoPasajero) {
                case "REGULAR":      pasajero = new PasajeroRegular(cedula, nombre, fechaNacimiento);     break;
                case "ESTUDIANTE":   pasajero = new PasajeroEstudiante(cedula, nombre, fechaNacimiento);  break;
                case "ADULTO_MAYOR": pasajero = new PasajeroAdultoMayor(cedula, nombre, fechaNacimiento); break;
                default:
                    System.err.println("[ReservaDao] Tipo pasajero desconocido: " + tipoPasajero);
                    return null;
            }

            String  placa        = p[5].trim();
            String  tipoVehiculo = p[6].trim().toUpperCase();
            String  ruta         = p[7].trim();
            double  tarifaBase   = Double.parseDouble(p[8].trim());

            Vehiculo vehiculo;
            switch (tipoVehiculo) {
                case "BUSETA":   vehiculo = new Buseta(placa, ruta);   break;
                case "MICROBUS": vehiculo = new MicroBus(placa, ruta); break;
                case "BUS":      vehiculo = new Bus(placa, ruta);      break;
                default:
                    System.err.println("[ReservaDao] Tipo vehículo desconocido: " + tipoVehiculo);
                    return null;
            }
            vehiculo.setTarifaBase(tarifaBase);

            LocalDateTime fechaCreacion = LocalDateTime.parse(p[9].trim());
            LocalDate     fechaViaje    = LocalDate.parse(p[10].trim());
            Reserva.Estado estado       = Reserva.Estado.valueOf(p[11].trim().toUpperCase());

            return new Reserva(codigo, pasajero, vehiculo, fechaCreacion, fechaViaje, estado);

        } catch (Exception e) {
            System.err.println("[ReservaDao] Error al parsear línea: " + linea + " → " + e.getMessage());
            return null;
        }
    }
    @Override
    public String toString() {
        return "ReservaDao{archivo='" + RUTA_ARCHIVO + "', total=" + lista.size() + "}";
    }
}
