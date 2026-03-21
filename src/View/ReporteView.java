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

    private void reportePorTipoVehiculo() {
        System.out.println("\n  Tipo de vehículo:");
        System.out.println("  1. Buseta   2. MicroBus   3. Bus");
        System.out.print("  Seleccione: ");
        int op = leerEntero();

        String tipoNombre;
        Class<?> tipoClase;
        switch (op) {
            case 1: tipoNombre = "BUSETA";   tipoClase = Buseta.class;   break;
            case 2: tipoNombre = "MICROBUS"; tipoClase = MicroBus.class; break;
            case 3: tipoNombre = "BUS";      tipoClase = Bus.class;      break;
            default:
                System.out.println("  Opción inválida.");
                return;
        }

        List<Ticket> todos = ticketService.listarTickets();
        System.out.println("\n── Tickets en " + tipoNombre + " ────────────────────");
        int conteo = 0;
        double total = 0;
        for (Ticket t : todos) {
            if (tipoClase.isInstance(t.getVehiculo())) {
                t.imprimirDetalle();
                total += t.getValorFinal();
                conteo++;
            }
        }
        if (conteo == 0) {
            System.out.println("  No hay tickets para ese tipo de vehículo.");
        } else {
            System.out.printf("  Total tickets: %d | Total recaudado: $%.0f%n", conteo, total);
        }
    }

    private void reportePorTipoPasajero() {
        System.out.println("\n  Tipo de pasajero:");
        System.out.println("  1. Regular   2. Estudiante   3. Adulto Mayor");
        System.out.print("  Seleccione: ");
        int op = leerEntero();

        String tipo;
        switch (op) {
            case 1: tipo = "Regular";      break;
            case 2: tipo = "Estudiante";   break;
            case 3: tipo = "Adulto_Mayor"; break;
            default:
                System.out.println("  Opción inválida.");
                return;
        }

        List<Ticket> todos = ticketService.listarTickets();
        System.out.println("\n── Tickets de pasajeros tipo " + tipo + " ──────────");
        int conteo = 0;
        double total = 0;
        for (Ticket t : todos) {
            if (t.getPasajero().getTipoPasajero().equalsIgnoreCase(tipo)) {
                t.imprimirDetalle();
                total += t.getValorFinal();
                conteo++;
            }
        }
        if (conteo == 0) {
            System.out.println("  No hay tickets para ese tipo de pasajero.");
        } else {
            System.out.printf("  Total tickets: %d | Total recaudado: $%.0f%n", conteo, total);
        }
    }

    private void resumenDiaActual() {
        LocalDate hoy = LocalDate.now();
        List<Ticket> ticketsHoy = ticketService.buscarTicketsPorFecha(hoy);

        double total = 0;
        int regulares = 0, estudiantes = 0, adultos = 0;
        for (Ticket t : ticketsHoy) {
            total += t.getValorFinal();
            switch (t.getPasajero().getTipoPasajero().toUpperCase()) {
                case "REGULAR":      regulares++; break;
                case "ESTUDIANTE":   estudiantes++; break;
                case "ADULTO_MAYOR": adultos++; break;
            }
        }

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║          RESUMEN DEL DÍA ACTUAL          ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║  Fecha              : %-18s  ║%n", hoy.toString());
        System.out.printf( "║  Festivo            : %-18s  ║%n", ticketService.esFestivo(hoy) ? "Sí (+20%)" : "No");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║  Total tickets      : %-18d  ║%n", ticketsHoy.size());
        System.out.printf( "║  Total recaudado    : $%-17.0f  ║%n", total);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║  Pasajeros regulares: %-18d  ║%n", regulares);
        System.out.printf( "║  Pasajeros estud.   : %-18d  ║%n", estudiantes);
        System.out.printf( "║  Adultos mayores    : %-18d  ║%n", adultos);
        System.out.println("╚══════════════════════════════════════════╝");

        if (!ticketsHoy.isEmpty()) {
            System.out.println("\n  Detalle de tickets de hoy:");
            for (Ticket t : ticketsHoy) t.imprimirDetalle();
        }
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
