package View;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        VehiculoDao vehiculoDao = new VehiculoDao();
        ConductorDao conductorDao = new ConductorDao();
        PasajeroDao pasajeroDao = new PasajeroDao();
        TicketDao ticketDao = new TicketDao();


        VehiculoService vehiculoService = new VehiculoService(vehiculoDao);
        PersonaService personaService = new PersonaService(conductorDao, pasajeroDao);
        TicketService ticketService = new TicketService(ticketDao, vehiculoDao, pasajeroDao);

        Scanner sc = new Scanner(System.in);

        VehiculoView vehiculoView = new VehiculoView(vehiculoService, personaService, sc);
        PersonaView personaView = new PersonaView(personaService, sc);
        TicketView ticketView = new TicketView(ticketService, sc);
        ReporteView  reporteView  = new ReporteView(ticketService, sc);

        MenuPrincipal menu = new MenuPrincipal();
        menu.ejecutar(vehiculoView, personaView, ticketView, reporteView, sc);

        sc.close();
    }

    public void ejecutar(VehiculoView vehiculoView, PersonaView personaView,
                         TicketView ticketView, ReporteView reporteView, Scanner sc) {
        int opcion;
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║    SISTEMA TRANSCESAR S.A.S.             ║");
        System.out.println("║    Gestión de Tickets Intermunicipales   ║");
        System.out.println("╚══════════════════════════════════════════╝");

        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║           MENÚ PRINCIPAL             ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Gestión de Vehículos             ║");
            System.out.println("║  2. Gestión de Personas              ║");
            System.out.println("║  3. Gestión de Tickets               ║");
            System.out.println("║  4. Reportes                         ║");
            System.out.println("║  0. Salir del sistema                ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("  Seleccione una opción: ");

            opcion = leerEntero(sc);

            switch (opcion) {
                case 1: vehiculoView.menuVehiculos();  break;
                case 2: personaView.menuPersonas();    break;
                case 3: ticketView.menuTickets();      break;
                case 4: reporteView.menuReportes();    break;
                case 0:
                    System.out.println("\n  Gracias por usar TransCesar S.A.S.");
                    System.out.println("  ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("  Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private int leerEntero(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
