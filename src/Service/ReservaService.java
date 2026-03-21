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

    public boolean cancelarReserva(String codigo) {
        Reserva reserva = reservaDao.buscarPorCodigo(codigo);
        if (reserva == null) {
            System.out.println("[ReservaService] Error: no existe reserva con código " + codigo);
            return false;
        }
        if (reserva.getEstado() != Reserva.Estado.ACTIVA) {
            System.out.println("[ReservaService] Error: la reserva " + codigo
                    + " no está activa (estado: " + reserva.getEstado() + ").");
            return false;
        }
        reservaDao.actualizarEstado(codigo, Reserva.Estado.CANCELADA);
        System.out.println("[ReservaService] Reserva " + codigo + " cancelada. Cupo liberado.");
        return true;
    }


    public List<Reserva> listarActivas() {
        return reservaDao.listarActivas();
    }


    public List<Reserva> historialPorPasajero(String cedula) {
        return reservaDao.buscarPorPasajero(cedula);
    }

    public Ticket convertirEnTicket(String codigo, String origen, String destino) {
        Reserva reserva = reservaDao.buscarPorCodigo(codigo);

        if (reserva == null) {
            System.out.println("[ReservaService] Error: no existe reserva con código " + codigo);
            return null;
        }
        if (reserva.getEstado() != Reserva.Estado.ACTIVA) {
            System.out.println("[ReservaService] Error: la reserva " + codigo
                    + " no está activa (estado: " + reserva.getEstado() + ").");
            return null;
        }

        Vehiculo vehiculo = vehiculoDao.buscarPorPlaca(reserva.getVehiculo().getPlaca());
        if (vehiculo == null) {
            System.out.println("[ReservaService] Error: vehículo no encontrado.");
            return null;
        }

        double tarifaOriginal = vehiculo.getTarifaBase();
        if (ticketService.esFestivo(reserva.getFechaViaje())) {
            vehiculo.setTarifaBase(tarifaOriginal * (1 + RECARGO_FESTIVO));
            System.out.println("[ReservaService] Aviso: la fecha de viaje es festivo. Recargo 20% aplicado.");
        }


        Ticket ticket = new Ticket(reserva.getPasajero(), vehiculo, origen, destino);

        vehiculo.setTarifaBase(tarifaOriginal);

        vehiculo.setContadorPasajeros(vehiculo.getContadorPasajeros() + 1);
        if (!vehiculo.hayCapacidad()) {
            vehiculo.setDisponible(false);
            System.out.println("[ReservaService] Aviso: vehículo " + vehiculo.getPlaca() + " ha alcanzado su capacidad máxima.");
        }
        vehiculoDao.actualizar(vehiculo);

        ticketDao.guardar(ticket);

        reservaDao.actualizarEstado(codigo, Reserva.Estado.CONVERTIDA);

        System.out.println("[ReservaService] Reserva convertida en ticket exitosamente.");
        return ticket;
    }

    public int verificarVencidas() {
        int canceladas = 0;
        for (Reserva r : reservaDao.listarActivas()) {
            if (r.estaVencida()) {
                reservaDao.actualizarEstado(r.getCodigo(), Reserva.Estado.CANCELADA);
                canceladas++;
            }
        }
        return canceladas;
    }

    @Override
    public String toString() {
        return "ReservaService{totalReservas=" + reservaDao.listarTodos().size() + "}";
    }
}
