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

    public Ticket venderTicket(String cedPasajero, String placaVehiculo,
                               String origen, String destino) {

        Pasajero pasajero = pasajeroDao.buscarPorCedula(cedPasajero);
        if (pasajero == null) {
            System.out.println("[TicketService] Error: no existe un pasajero con cédula " + cedPasajero);
            return null;
        }

        Vehiculo vehiculo = vehiculoDao.buscarPorPlaca(placaVehiculo);
        if (vehiculo == null) {
            System.out.println("[TicketService] Error: no existe un vehículo con placa " + placaVehiculo);
            return null;
        }

        if (!vehiculo.isDisponible()) {
            System.out.println("[TicketService] Error: el vehículo " + placaVehiculo + " no está disponible.");
            return null;
        }

        if (!vehiculo.hayCapacidad()) {
            System.out.println("[TicketService] Error: el vehículo " + placaVehiculo
                    + " está lleno. Capacidad máxima: " + vehiculo.getCapacidadMaxima());
            return null;
        }

        Ticket ticket = new Ticket(pasajero, vehiculo, origen, destino);

        vehiculo.setContadorPasajeros(vehiculo.getContadorPasajeros() + 1);

        if (!vehiculo.hayCapacidad()) {
            vehiculo.setDisponible(false);
            System.out.println("[TicketService] Aviso: el vehículo " + placaVehiculo + " ha alcanzado su capacidad máxima.");
        }

        vehiculoDao.actualizar(vehiculo);

        ticketDao.guardar(ticket);

        System.out.println("[TicketService] Ticket vendido exitosamente.");
        System.out.printf(
                "[TicketService] Pasajero: %s | Vehículo: %s | Valor: $%.0f%n",
                pasajero.getNombre(), placaVehiculo, ticket.getValorFinal());

        return ticket;
    }

    public List<Ticket> listarTickets() {
        return ticketDao.listarTodos();
    }

    public List<Ticket> buscarTicketsPorPasajero(String cedula) {
        return ticketDao.buscarPorPasajero(cedula);
    }

    public List<Ticket> buscarTicketsPorVehiculo(String placa) {
        return ticketDao.buscarPorVehiculo(placa);
    }

    public List<Ticket> buscarTicketsPorFecha(LocalDate fecha) {
        return ticketDao.buscarPorFecha(fecha);
    }

}
