package View;

import java.util.Scanner;

public class ReservaView {
    private ReservaService reservaService;
    private Scanner sc;

    public ReservaView(ReservaService reservaService, Scanner sc) {
        this.reservaService = reservaService;
        this.sc             = sc;
    }

    public void menuReservas() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         GESTIÓN DE RESERVAS          ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Crear nueva reserva              ║");
            System.out.println("║  2. Cancelar reserva                 ║");
            System.out.println("║  3. Listar reservas activas          ║");
            System.out.println("║  4. Historial de reservas (pasajero) ║");
            System.out.println("║  5. Convertir reserva en ticket      ║");
            System.out.println("║  6. Verificar reservas vencidas      ║");
            System.out.println("║  0. Volver al menú principal         ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("  Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: crearReserva();             break;
                case 2: cancelarReserva();          break;
                case 3: listarActivas();            break;
                case 4: historialPorPasajero();     break;
                case 5: convertirEnTicket();        break;
                case 6: verificarVencidas();        break;
                case 0: System.out.println("  Volviendo al menú principal..."); break;
                default: System.out.println("  Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
