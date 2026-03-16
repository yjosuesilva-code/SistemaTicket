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

}
