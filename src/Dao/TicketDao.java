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
}
