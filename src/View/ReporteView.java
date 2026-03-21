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

}
