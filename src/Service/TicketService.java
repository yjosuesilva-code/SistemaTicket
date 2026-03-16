package Service;

import Dao.PasajeroDao;
import Dao.TicketDao;
import Dao.VehiculoDao;
import Model.Pasajero;
import Model.Ticket;
import Model.Vehiculo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TicketService {
    private TicketDao   ticketDao;
    private VehiculoDao vehiculoDao;
    private PasajeroDao pasajeroDao;

    public TicketService(TicketDao ticketDao, VehiculoDao vehiculoDao, PasajeroDao pasajeroDao) {
        this.ticketDao   = ticketDao;
        this.vehiculoDao = vehiculoDao;
        this.pasajeroDao = pasajeroDao;
    }
}
