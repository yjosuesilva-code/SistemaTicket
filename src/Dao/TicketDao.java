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

    private static final int CAMPOS = 11;

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

}
