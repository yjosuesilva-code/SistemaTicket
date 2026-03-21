package Service;

import Dao.PasajeroDao;
import Dao.ReservaDao;
import Dao.TicketDao;
import Dao.VehiculoDao;
import Model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReservaService {
    private static final double RECARGO_FESTIVO = 0.20;

    private ReservaDao  reservaDao;
    private VehiculoDao vehiculoDao;
    private PasajeroDao pasajeroDao;
    private TicketDao   ticketDao;
    private TicketService ticketService;

    public ReservaService(ReservaDao reservaDao, VehiculoDao vehiculoDao, PasajeroDao pasajeroDao, TicketDao ticketDao, TicketService ticketService) {
        this.reservaDao    = reservaDao;
        this.vehiculoDao   = vehiculoDao;
        this.pasajeroDao   = pasajeroDao;
        this.ticketDao     = ticketDao;
        this.ticketService = ticketService;
        verificarVencidas();
    }

    public Reserva crearReserva(String cedPasajero, String placaVehiculo,
                                LocalDate fechaViaje) {


        Pasajero pasajero = pasajeroDao.buscarPorCedula(cedPasajero);
        if (pasajero == null) {
            System.out.println("[ReservaService] Error: no existe pasajero con cédula " + cedPasajero);
            return null;
        }


        Vehiculo vehiculo = vehiculoDao.buscarPorPlaca(placaVehiculo);
        if (vehiculo == null) {
            System.out.println("[ReservaService] Error: no existe vehículo con placa " + placaVehiculo);
            return null;
        }

        if (!vehiculo.isDisponible()) {
            System.out.println("[ReservaService] Error: el vehículo " + placaVehiculo + " no está disponible.");
            return null;
        }

        if (fechaViaje.isBefore(LocalDate.now())) {
            System.out.println("[ReservaService] Error: la fecha de viaje no puede ser en el pasado.");
            return null;
        }

        List<Reserva> historial = reservaDao.buscarPorPasajero(cedPasajero);
        for (Reserva r : historial) {
            if (r.getEstado() == Reserva.Estado.ACTIVA
                    && r.getVehiculo().getPlaca().equalsIgnoreCase(placaVehiculo)
                    && r.getFechaViaje().equals(fechaViaje)) {
                System.out.println("[ReservaService] Error: el pasajero ya tiene una reserva activa "
                        + "para ese vehículo en esa fecha. Código: " + r.getCodigo());
                return null;
            }
        }

        int ticketsVendidos = ticketDao.buscarPorVehiculo(placaVehiculo).size();

        int reservasActivas = reservaDao.contarActivasPorVehiculoYFecha(placaVehiculo, fechaViaje);
        int ocupacion = vehiculo.getContadorPasajeros() + reservasActivas;

        if (ocupacion >= vehiculo.getCapacidadMaxima()) {
            System.out.println("[ReservaService] Error: no hay cupos disponibles para el vehículo "
                    + placaVehiculo + " en la fecha " + fechaViaje
                    + ". Ocupación: " + ocupacion + "/" + vehiculo.getCapacidadMaxima());
            return null;
        }

        String codigo = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Reserva reserva = new Reserva(codigo, pasajero, vehiculo, fechaViaje);
        reservaDao.guardar(reserva);

        System.out.println("[ReservaService] Reserva creada exitosamente. Código: " + codigo);
        return reserva;
    }


}
