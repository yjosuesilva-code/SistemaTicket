package View;

import Service.TicketService;

import java.util.Scanner;

public class ReporteView {
    private TicketService ticketService;
    private Scanner sc;

    public ReporteView(TicketService ticketService, Scanner sc) {
        this.ticketService = ticketService;
        this.sc            = sc;
    }

    public void menuReportes() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         MÓDULO DE REPORTES           ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Tickets por fecha específica     ║");
            System.out.println("║  2. Tickets por tipo de vehículo     ║");
            System.out.println("║  3. Tickets por tipo de pasajero     ║");
            System.out.println("║  4. Resumen del día actual           ║");
            System.out.println("║  0. Volver al menú principal         ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("  Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: reportePorFecha();          break;
                case 2: reportePorTipoVehiculo();   break;
                case 3: reportePorTipoPasajero();   break;
                case 4: resumenDiaActual();         break;
                case 0: System.out.println("  Volviendo al menú principal..."); break;
                default: System.out.println("  Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void reportePorFecha() {
        System.out.print("\n  Fecha (yyyy-MM-dd): ");
        String fechaStr = sc.nextLine().trim();
        try {
            LocalDate fecha = LocalDate.parse(fechaStr);
            List<Ticket> lista = ticketService.buscarTicketsPorFecha(fecha);
            System.out.println("\n── Tickets del " + fecha + " (" + lista.size() + ") ──────────");
            if (lista.isEmpty()) {
                System.out.println("  No hay tickets para esa fecha.");
                return;
            }
            double total = 0;
            for (Ticket t : lista) {
                t.imprimirDetalle();
                total += t.getValorFinal();
            }
            System.out.printf("  Total recaudado ese día: $%.0f%n", total);
        } catch (DateTimeParseException e) {
            System.out.println("  ✘ Formato inválido. Use yyyy-MM-dd.");
        }
    }

}
