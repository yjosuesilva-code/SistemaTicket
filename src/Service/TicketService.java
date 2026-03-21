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
    private static final int MAX_TICKETS_POR_DIA = 3;
    private static final double RECARGO_FESTIVO  = 0.20;

    private static final Set<LocalDate> FESTIVOS = new HashSet<>(Arrays.asList(
            LocalDate.of(LocalDate.now().getYear(), 1,  1),   // Año Nuevo
            LocalDate.of(LocalDate.now().getYear(), 1,  6),   // Reyes Magos
            LocalDate.of(LocalDate.now().getYear(), 3, 24),   // San José
            LocalDate.of(LocalDate.now().getYear(), 5,  1),   // Día del Trabajo
            LocalDate.of(LocalDate.now().getYear(), 7, 20),   // Independencia
            LocalDate.of(LocalDate.now().getYear(), 8,  7),   // Batalla de Boyacá
            LocalDate.of(LocalDate.now().getYear(), 8, 18),   // Asunción de la Virgen
            LocalDate.of(LocalDate.now().getYear(),10, 13),   // Día de la Raza
            LocalDate.of(LocalDate.now().getYear(),11,  3),   // Todos los Santos
            LocalDate.of(LocalDate.now().getYear(),11, 17),   // Independencia de Cartagena
            LocalDate.of(LocalDate.now().getYear(),12,  8),   // Inmaculada Concepción
            LocalDate.of(LocalDate.now().getYear(),12, 25)    // Navidad
    ));

    private TicketDao   ticketDao;
    private VehiculoDao vehiculoDao;
    private PasajeroDao pasajeroDao;

    public TicketService(TicketDao ticketDao, VehiculoDao vehiculoDao, PasajeroDao pasajeroDao) {
        this.ticketDao   = ticketDao;
        this.vehiculoDao = vehiculoDao;
        this.pasajeroDao = pasajeroDao;
    }

    public boolean esFestivo(LocalDate fecha) {
        return FESTIVOS.contains(fecha);
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

        LocalDate hoy = LocalDate.now();
        List<Ticket> ticketsHoy = ticketDao.buscarPorFecha(hoy);
        int conteo = 0;
        for (Ticket t : ticketsHoy)
            if (t.getPasajero().getCedula().equalsIgnoreCase(cedPasajero)) conteo++;

        if (conteo >= MAX_TICKETS_POR_DIA) {
            System.out.println("[TicketService] Error: el pasajero " + pasajero.getNombre()
                    + " ya tiene " + conteo + " ticket(s) comprados hoy. Límite: " + MAX_TICKETS_POR_DIA);
            return null;
        }

        double tarifaFinal = vehiculo.getTarifaBase();
        if (esFestivo(hoy)) {
            tarifaFinal = tarifaFinal * (1 + RECARGO_FESTIVO);
            System.out.println("[TicketService] Aviso: hoy es festivo. Se aplica recargo del 20%.");
            vehiculo.setTarifaBase(tarifaFinal);
        }

        Ticket ticket = new Ticket(pasajero, vehiculo, origen, destino);

        if (esFestivo(hoy)) {
            vehiculo.setTarifaBase(vehiculo.getTarifaBase() / (1 + RECARGO_FESTIVO));
        }

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

    public boolean actualizarDestino(Ticket t, String nuevoDestino) {
        if (t == null) {
            System.out.println("[TicketService] Error: ticket nulo.");
            return false;
        }
        t.setDestino(nuevoDestino);
        boolean resultado = ticketDao.actualizar(t);
        if (resultado) {
            System.out.println("[TicketService] Destino actualizado a: " + nuevoDestino);
        }
        return resultado;
    }

    public boolean cancelarTicket(Ticket t) {
        if (t == null) {
            System.out.println("[TicketService] Error: ticket nulo.");
            return false;
        }

        boolean eliminado = ticketDao.eliminar(t);

        if (eliminado) {
            Vehiculo v = vehiculoDao.buscarPorPlaca(t.getVehiculo().getPlaca());
            if (v != null && v.getContadorPasajeros() > 0) {
                v.setContadorPasajeros(v.getContadorPasajeros() - 1);
                v.setDisponible(true);
                vehiculoDao.actualizar(v);
            }
            System.out.println("[TicketService] Ticket cancelado. Cupo devuelto al vehículo "
                    + t.getVehiculo().getPlaca());
        }

        return eliminado;
    }

    public double totalRecaudado() {
        double total = 0;
        for (Ticket t : ticketDao.listarTodos()) {
            total += t.getValorFinal();
        }
        return total;
    }

    public Map<String, Integer> pasajerosPorTipo() {
        Map<String, Integer> conteo = new HashMap<>();
        conteo.put("REGULAR", 0);
        conteo.put("ESTUDIANTE", 0);
        conteo.put("ADULTO_MAYOR", 0);

        for (Ticket t : ticketDao.listarTodos()) {
            String tipo = t.getPasajero().getTipoPasajero().toUpperCase();
            conteo.put(tipo, conteo.getOrDefault(tipo, 0) + 1);
        }
        return conteo;
    }

    public String vehiculoConMasTickets() {
        Map<String, Integer> conteo = new HashMap<>();

        for (Ticket t : ticketDao.listarTodos()) {
            String placa = t.getVehiculo().getPlaca();
            conteo.put(placa, conteo.getOrDefault(placa, 0) + 1);
        }

        if (conteo.isEmpty()) return "N/A";

        String placaMax = null;
        int max = -1;

        for (Map.Entry<String, Integer> entry : conteo.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                placaMax = entry.getKey();
            }
        }

        return placaMax != null ? placaMax : "N/A";
    }

    public double totalRecaudadoPorVehiculo(String placa) {
        double total = 0;
        for (Ticket t : ticketDao.buscarPorVehiculo(placa)) {
            total += t.getValorFinal();
        }
        return total;
    }

    public void mostrarEstadisticas() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         ESTADÍSTICAS DEL SISTEMA         ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║  Total tickets vendidos : %-14d  ║%n", listarTickets().size());
        System.out.printf("║  Total recaudado        : $%-13.0f  ║%n", totalRecaudado());
        System.out.println("╠══════════════════════════════════════════╣");

        Map<String, Integer> porTipo = pasajerosPorTipo();
        System.out.printf("║  Pasajeros regulares    : %-14d  ║%n", porTipo.get("REGULAR"));
        System.out.printf("║  Pasajeros estudiantes  : %-14d  ║%n", porTipo.get("ESTUDIANTE"));
        System.out.printf("║  Pasajeros adulto mayor : %-14d  ║%n", porTipo.get("ADULTO_MAYOR"));
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║  Vehículo más tickets   : %-14s  ║%n", vehiculoConMasTickets());
        System.out.println("╚══════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return "TicketService{" +
                "totalTickets=" + ticketDao.listarTodos().size() +
                ", totalRecaudado=" + totalRecaudado() +
                '}';
    }


}
