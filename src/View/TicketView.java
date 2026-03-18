package View;

/**
 * TicketView
 * Interfaz de consola para la gestión de tickets.
 * Solo interactúa con TicketService, nunca con el DAO directamente.
 *
 * Capa: View
 * Proyecto: TransCesar S.A.S.
 */

public class TicketView {
    // ─── Dependencias ─────────────────────────────────────────────────────────
    private TicketService ticketService;
    private Scanner       sc;

    // ─────────────────────────────────────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────────────────
    public TicketView(TicketService ticketService, Scanner sc) {
        this.ticketService = ticketService;
        this.sc            = sc;
    }

    // ─────────────────────────────────────────────────────────────────────────
// MENÚ PRINCIPAL DE TICKETS
// ─────────────────────────────────────────────────────────────────────────
    public void menuTickets() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         GESTIÓN DE TICKETS           ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Vender ticket                    ║");
            System.out.println("║  2. Listar todos los tickets         ║");
            System.out.println("║  3. Buscar tickets por pasajero      ║");
            System.out.println("║  4. Buscar tickets por vehículo      ║");
            System.out.println("║  5. Buscar tickets por fecha         ║");
            System.out.println("║  6. Cancelar ticket                  ║");
            System.out.println("║  7. Estadísticas                     ║");
            System.out.println("║  0. Volver al menú principal         ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("  Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: venderTicket();             break;
                case 2: listarTickets();            break;
                case 3: buscarPorPasajero();        break;
                case 4: buscarPorVehiculo();        break;
                case 5: buscarPorFecha();           break;
                case 6: cancelarTicket();           break;
                case 7: mostrarEstadisticas();      break;
                case 0: System.out.println("  Volviendo al menú principal..."); break;
                default: System.out.println("  Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }



}
