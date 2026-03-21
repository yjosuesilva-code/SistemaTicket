package View;

import Model.Reserva;
import Model.Ticket;
import Service.ReservaService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
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
    private void crearReserva() {
        System.out.println("\n── Crear Nueva Reserva ─────────────────");
        System.out.print("  Cédula del pasajero: ");
        String cedula = sc.nextLine().trim();

        System.out.print("  Placa del vehículo: ");
        String placa = sc.nextLine().trim().toUpperCase();

        LocalDate fechaViaje = leerFecha("  Fecha del viaje (yyyy-MM-dd): ");
        if (fechaViaje == null) return;

        Reserva reserva = reservaService.crearReserva(cedula, placa, fechaViaje);
        if (reserva != null) {
            System.out.println("\n   Reserva creada exitosamente:");
            reserva.imprimirDetalle();
        } else {
            System.out.println("   No se pudo crear la reserva. Revise los datos ingresados.");
        }
    }

    private void cancelarReserva() {
        System.out.println("\n── Cancelar Reserva ────────────────────");
        System.out.print("  Código de la reserva: ");
        String codigo = sc.nextLine().trim().toUpperCase();

        System.out.print("  ¿Confirma cancelar la reserva " + codigo + "? (1=Sí / 0=No): ");
        int confirm = leerEntero();
        if (confirm != 1) {
            System.out.println("  Operación cancelada.");
            return;
        }

        boolean ok = reservaService.cancelarReserva(codigo);
        System.out.println(ok
                ? "   Reserva cancelada. El cupo fue liberado."
                : "   No se pudo cancelar la reserva.");
    }

    private void historialPorPasajero() {
        System.out.println("\n── Historial de Reservas por Pasajero ──");
        System.out.print("  Cédula del pasajero: ");
        String cedula = sc.nextLine().trim();

        List<Reserva> historial = reservaService.historialPorPasajero(cedula);
        System.out.println("  Reservas encontradas: " + historial.size());
        if (historial.isEmpty()) {
            System.out.println("  No se encontraron reservas para la cédula: " + cedula);
            return;
        }
        for (Reserva r : historial) {
            r.imprimirDetalle();
        }
    }


}
