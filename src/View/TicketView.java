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

    // ─────────────────────────────────────────────────────────────────────────
// 1. VENDER TICKET
// ─────────────────────────────────────────────────────────────────────────
    private void venderTicket() {
        System.out.println("\n── Vender Ticket ───────────────────────");
        System.out.print("  Cédula del pasajero: ");
        String cedula = sc.nextLine().trim();
        System.out.print("  Placa del vehículo: ");
        String placa = sc.nextLine().trim().toUpperCase();
        System.out.print("  Ciudad de origen: ");
        String origen = sc.nextLine().trim();
        System.out.print("  Ciudad de destino: ");
        String destino = sc.nextLine().trim();

        Ticket ticket = ticketService.venderTicket(cedula, placa, origen, destino);
        if (ticket != null) {
            System.out.println("\n  ✔ Ticket generado exitosamente:");
            ticket.imprimirDetalle();
        } else {
            System.out.println("  ✘ No se pudo generar el ticket.");
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
// 2. LISTAR TODOS LOS TICKETS
// ─────────────────────────────────────────────────────────────────────────
    private void listarTickets() {
        List<Ticket> lista = ticketService.listarTickets();
        System.out.println("\n── Todos los Tickets (" + lista.size() + ") ──────────────");
        if (lista.isEmpty()) {
            System.out.println("  No hay tickets registrados.");
            return;
        }
        for (Ticket t : lista) {
            t.imprimirDetalle();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
// 3. BUSCAR POR PASAJERO
// ─────────────────────────────────────────────────────────────────────────
    private void buscarPorPasajero() {
        System.out.print("\n  Cédula del pasajero: ");
        String cedula = sc.nextLine().trim();
        List<Ticket> lista = ticketService.buscarTicketsPorPasajero(cedula);
        System.out.println("  Tickets encontrados: " + lista.size());
        for (Ticket t : lista) {
            t.imprimirDetalle();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
// 4. BUSCAR POR VEHÍCULO
// ─────────────────────────────────────────────────────────────────────────
    private void buscarPorVehiculo() {
        System.out.print("\n  Placa del vehículo: ");
        String placa = sc.nextLine().trim().toUpperCase();
        List<Ticket> lista = ticketService.buscarTicketsPorVehiculo(placa);
        System.out.println("  Tickets encontrados: " + lista.size());
        for (Ticket t : lista) {
            t.imprimirDetalle();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
// 5. BUSCAR POR FECHA
// ─────────────────────────────────────────────────────────────────────────
    private void buscarPorFecha() {
        System.out.print("\n  Fecha (formato yyyy-MM-dd, ej. 2025-03-10): ");
        String fechaStr = sc.nextLine().trim();
        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            List<Ticket> lista = ticketService.buscarTicketsPorFecha(fecha);
            System.out.println("  Tickets encontrados: " + lista.size());
            for (Ticket t : lista) {
                t.imprimirDetalle();
            }
        } catch (DateTimeParseException e) {
            System.out.println("  ✘ Formato de fecha inválido. Use yyyy-MM-dd.");
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 6. CANCELAR TICKET
    // ─────────────────────────────────────────────────────────────────────────
    private void cancelarTicket() {
        System.out.println("\n── Cancelar Ticket ─────────────────────");
        System.out.print("  Cédula del pasajero: ");
        String cedula = sc.nextLine().trim();

        List<Ticket> tickets = ticketService.buscarTicketsPorPasajero(cedula);
        if (tickets.isEmpty()) {
            System.out.println("  ✘ No se encontraron tickets para la cédula: " + cedula);
            return;
        }

        // Mostrar tickets del pasajero para que elija
        System.out.println("  Tickets del pasajero:");
        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            System.out.printf("  [%d] %s → %s | Vehículo: %s | Fecha: %s | Valor: $%.0f%n",
                    i + 1,
                    t.getOrigen(),
                    t.getDestino(),
                    t.getVehiculo().getPlaca(),
                    t.getFechaCompra(),
                    t.getValorFinal());
        }

        System.out.print("  Seleccione el número del ticket a cancelar (0 para salir): ");
        int seleccion = leerEntero();

        if (seleccion == 0) {
            System.out.println("  Operación cancelada.");
            return;
        }
        if (seleccion < 1 || seleccion > tickets.size()) {
            System.out.println("  Selección inválida.");
            return;
        }

        Ticket ticketACancelar = tickets.get(seleccion - 1);
        System.out.print("  ¿Confirma cancelar este ticket? (1=Sí / 0=No): ");
        int confirm = leerEntero();
        if (confirm != 1) {
            System.out.println("  Operación cancelada.");
            return;
        }

        boolean ok = ticketService.cancelarTicket(ticketACancelar);
        System.out.println(ok
                ? "  ✔ Ticket cancelado. El cupo fue devuelto al vehículo."
                : "  ✘ No se pudo cancelar el ticket.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 7. ESTADÍSTICAS
    // ─────────────────────────────────────────────────────────────────────────
    private void mostrarEstadisticas() {
        System.out.println();
        ticketService.mostrarEstadisticas();

        // Desglose por tipo de pasajero
        Map<String, Integer> porTipo = ticketService.pasajerosPorTipo();
        System.out.println("\n── Desglose por tipo de pasajero ───────");
        System.out.printf("  Regular      : %d tickets%n", porTipo.getOrDefault("REGULAR",      0));
        System.out.printf("  Estudiante   : %d tickets%n", porTipo.getOrDefault("ESTUDIANTE",   0));
        System.out.printf("  Adulto Mayor : %d tickets%n", porTipo.getOrDefault("ADULTO_MAYOR", 0));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HELPER — leer entero con manejo de error
    // ─────────────────────────────────────────────────────────────────────────
    private int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
