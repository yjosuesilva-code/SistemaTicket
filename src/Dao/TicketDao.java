package Dao;

import Model.Ticket;
import Model.Pasajero;
import Model.Vehiculo;
import Model.PasajeroRegular;
import Model.PasajeroEstudiante;
import Model.PasajeroAdultoMayor;
import Model.Buseta;
import Model.MicroBus;
import Model.Bus;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {
    private static final String RUTA_ARCHIVO = "data/tickets.txt";

    private static final int CAMPOS = 12;

    private List<Ticket> lista;


    public TicketDao() {
        lista = new ArrayList<>();
        cargarTodos();
    }


    public void guardar(Ticket t) {
        lista.add(t);
        escribirLinea(t);
    }

    public List<Ticket> cargarTodos() {
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
                    Ticket t = parsearLinea(linea);
                    if (t != null) {
                        lista.add(t);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[TicketDAO] Error al leer el archivo: " + e.getMessage());
        }

        return lista;
    }

    public List<Ticket> listarTodos() {
        return new ArrayList<>(lista);
    }

    public List<Ticket> buscarPorPasajero(String cedula) {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : lista) {
            if (t.getPasajero().getCedula().equalsIgnoreCase(cedula.trim())) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public List<Ticket> buscarPorVehiculo(String placa) {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : lista) {
            if (t.getVehiculo().getPlaca().equalsIgnoreCase(placa.trim())) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public List<Ticket> buscarPorFecha(LocalDate fecha) {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : lista) {
            if (t.getFechaCompra().equals(fecha)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public boolean actualizar(Ticket ticketActualizado) {
        for (int i = 0; i < lista.size(); i++) {
            Ticket t = lista.get(i);
            if (mismoTicket(t, ticketActualizado)) {
                lista.set(i, ticketActualizado);
                escribirArchivo();
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(Ticket t) {
        for (int i = 0; i < lista.size(); i++) {
            if (mismoTicket(lista.get(i), t)) {
                lista.remove(i);
                escribirArchivo();
                return true;
            }
        }
        return false;
    }

    private boolean mismoTicket(Ticket a, Ticket b) {
        return a.getPasajero().getCedula().equalsIgnoreCase(b.getPasajero().getCedula())
                && a.getVehiculo().getPlaca().equalsIgnoreCase(b.getVehiculo().getPlaca())
                && a.getFechaCompra().equals(b.getFechaCompra())
                && a.getOrigen().equalsIgnoreCase(b.getOrigen())
                && a.getDestino().equalsIgnoreCase(b.getDestino());
    }

    private void escribirLinea(Ticket t) {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(aLinea(t));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("[TicketDAO] Error al escribir línea: " + e.getMessage());
        }
    }

    private void escribirArchivo() {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Ticket t : lista) {
                bw.write(aLinea(t));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[TicketDAO] Error al reescribir archivo: " + e.getMessage());
        }
    }

    private String aLinea(Ticket t) {
        String tipoVehiculo;
        if (t.getVehiculo() instanceof Buseta) tipoVehiculo = "BUSETA";
        else if (t.getVehiculo() instanceof MicroBus) tipoVehiculo = "MICROBUS";
        else tipoVehiculo = "BUS";

        return t.getPasajero().getCedula()              + ";" +
                t.getPasajero().getNombre()              + ";" +
                t.getPasajero().getTipoPasajero()        + ";" +
                t.getPasajero().getFechaNacimiento()     + ";" +
                t.getVehiculo().getPlaca()               + ";" +
                tipoVehiculo                             + ";" +
                t.getVehiculo().getRuta()                + ";" +
                t.getVehiculo().getTarifaBase()          + ";" +
                t.getFechaCompra().toString()            + ";" +
                t.getOrigen()                            + ";" +
                t.getDestino()                           + ";" +
                t.getValorFinal();
    }

    private Ticket parsearLinea(String linea) {
        String[] p = linea.split(";");

        if (p.length != CAMPOS) {
            System.err.println("[TicketDAO] Línea con formato incorrecto (" + p.length + " campos): " + linea);
            return null;
        }

        try {
            String cedula       = p[0].trim();
            String nombre       = p[1].trim();
            String tipoPasajero = p[2].trim().toUpperCase();

            Pasajero pasajero;
            switch (tipoPasajero) {
                case "REGULAR":
                    pasajero = new PasajeroRegular(cedula, nombre);
                    break;
                case "ESTUDIANTE":
                    pasajero = new PasajeroEstudiante(cedula, nombre);
                    break;
                case "ADULTO_MAYOR":
                    pasajero = new PasajeroAdultoMayor(cedula, nombre);
                    break;
                default:
                    System.err.println("[TicketDAO] Tipo de pasajero desconocido: " + tipoPasajero);
                    return null;
            }


            String placa        = p[3].trim();
            String tipoVehiculo = p[4].trim().toUpperCase();
            String ruta         = p[5].trim();
            double tarifaBase   = Double.parseDouble(p[6].trim());

            Vehiculo vehiculo;
            switch (tipoVehiculo) {
                case "BUSETA":
                    vehiculo = new Buseta(placa, ruta);
                    break;
                case "MICROBUS":
                    vehiculo = new MicroBus(placa, ruta);
                    break;
                case "BUS":
                    vehiculo = new Bus(placa, ruta);
                    break;
                default:
                    System.err.println("[TicketDAO] Tipo de vehículo desconocido: " + tipoVehiculo);
                    return null;
            }
            vehiculo.setTarifaBase(tarifaBase);

            LocalDate fecha      = LocalDate.parse(p[7].trim());
            String    origen     = p[8].trim();
            String    destino    = p[9].trim();
            double    valorFinal = Double.parseDouble(p[10].trim());

            Ticket ticket = new Ticket(pasajero, vehiculo, origen, destino);
            ticket.setValorFinal(valorFinal);


            return ticket;

        } catch (Exception e) {
            System.err.println("[TicketDAO] Error al parsear línea: " + linea + " → " + e.getMessage());
            return null;
        }

    }

    @Override
    public String toString() {
        return "TicketDAO{" +
                "archivo='" + RUTA_ARCHIVO + '\'' +
                ", totalTickets=" + lista.size() +
                '}';
    }

}
