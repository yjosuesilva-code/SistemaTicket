package View;

import Model.Conductor;
import Model.Pasajero;
import Model.PasajeroAdultoMayor;
import Model.PasajeroEstudiante;
import Model.PasajeroRegular;
import Service.PersonaService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class PersonaView {
    private PersonaService personaService;
    private Scanner        sc;


    public PersonaView(PersonaService personaService, Scanner sc) {
        this.personaService = personaService;
        this.sc             = sc;
    }


    public void menuPersonas() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         GESTIÓN DE PERSONAS          ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Registrar conductor              ║");
            System.out.println("║  2. Registrar pasajero               ║");
            System.out.println("║  3. Listar conductores               ║");
            System.out.println("║  4. Listar pasajeros                 ║");
            System.out.println("║  5. Buscar conductor por cédula      ║");
            System.out.println("║  6. Buscar pasajero por cédula       ║");
            System.out.println("║  7. Actualizar datos de conductor    ║");
            System.out.println("║  8. Actualizar datos de pasajero     ║");
            System.out.println("║  9. Eliminar conductor               ║");
            System.out.println("║ 10. Eliminar pasajero                ║");
            System.out.println("║  0. Volver al menú principal         ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("  Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1:  registrarConductor();          break;
                case 2:  registrarPasajero();           break;
                case 3:  listarConductores();           break;
                case 4:  listarPasajeros();             break;
                case 5:  buscarConductorPorCedula();    break;
                case 6:  buscarPasajeroPorCedula();     break;
                case 7:  actualizarConductor();         break;
                case 8:  actualizarPasajero();          break;
                case 9:  eliminarConductor();           break;
                case 10: eliminarPasajero();            break;
                case 0:  System.out.println("  Volviendo al menú principal..."); break;
                default: System.out.println("  Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }


    private void registrarConductor() {
        System.out.println("\n── Registrar Conductor ─────────────────");
        System.out.print("  Cédula: ");
        String cedula = sc.nextLine().trim();
        System.out.print("  Nombre completo: ");
        String nombre = sc.nextLine().trim();
        System.out.print("  Número de licencia: ");
        String numLicencia = sc.nextLine().trim();
        System.out.println("  Categoría de licencia:");
        System.out.println("  1. B1   2. B2   3. C1   4. C2");
        System.out.print("  Seleccione: ");
        int catOp = leerEntero();
        String[] categorias = {"B1", "B2", "C1", "C2"};
        if (catOp < 1 || catOp > 4) {
            System.out.println("  Categoría inválida.");
            return;
        }
        String categoria = categorias[catOp - 1];

        Conductor c = new Conductor(cedula, nombre, numLicencia, categoria);
        boolean ok = personaService.registrarConductor(c);
        System.out.println(ok ? "  ✔ Conductor registrado exitosamente." : "  ✘ No se pudo registrar el conductor.");
    }


    private void registrarPasajero() {
        System.out.println("\n── Registrar Pasajero ──────────────────");
        System.out.print("  Cédula: ");
        String cedula = sc.nextLine().trim();
        System.out.print("  Nombre completo: ");
        String nombre = sc.nextLine().trim();
        System.out.println("  Fecha de nacimiento (yyyy-MM-dd): ");
        String fechaStr= sc.nextLine().trim();

        LocalDate fechaNacimiento;
        try {
            fechaNacimiento = LocalDate.parse(fechaStr);
        } catch (DateTimeParseException e) {
            System.out.println("  Formato de fecha inválido. Use yyyy-MM-dd.");
            return;
        }

        System.out.println("  Tipo de pasajero:");
        System.out.println("  1. Regular      (sin descuento)");
        System.out.println("  2. Estudiante   (15% descuento)");
        System.out.println("  3. Adulto Mayor (30% descuento)");
        System.out.print("  Seleccione: ");
        int tipo = leerEntero();

        Pasajero p;
        switch (tipo) {
            case 1: p = new PasajeroRegular(cedula, nombre);      break;
            case 2: p = new PasajeroEstudiante(cedula, nombre);   break;
            case 3: p = new PasajeroAdultoMayor(cedula, nombre);  break;
            default:
                System.out.println("  Tipo inválido.");
                return;
        }

        boolean ok = personaService.registrarPasajero(p);
        System.out.println(ok ? "  ✔ Pasajero registrado exitosamente." : "  ✘ No se pudo registrar el pasajero.");
    }


    private void listarConductores() {
        List<Conductor> lista = personaService.listarConductores();
        System.out.println("\n── Conductores registrados (" + lista.size() + ") ──────────");
        if (lista.isEmpty()) {
            System.out.println("  No hay conductores registrados.");
            return;
        }
        for (Conductor c : lista) {
            c.imprimirDetalle();
        }
    }


    private void listarPasajeros() {
        List<Pasajero> lista = personaService.listarPasajeros();
        System.out.println("\n── Pasajeros registrados (" + lista.size() + ") ───────────");
        if (lista.isEmpty()) {
            System.out.println("  No hay pasajeros registrados.");
            return;
        }
        for (Pasajero p : lista) {
            p.imprimirDetalle();
        }
    }


    private void buscarConductorPorCedula() {
        System.out.print("\n  Cédula del conductor: ");
        String cedula = sc.nextLine().trim();
        Conductor c = personaService.buscarConductorPorCedula(cedula);
        if (c == null) {
            System.out.println("  ✘ No se encontró conductor con cédula: " + cedula);
        } else {
            c.imprimirDetalle();
        }
    }


    private void buscarPasajeroPorCedula() {
        System.out.print("\n  Cédula del pasajero: ");
        String cedula = sc.nextLine().trim();
        Pasajero p = personaService.buscarPasajeroPorCedula(cedula);
        if (p == null) {
            System.out.println("  ✘ No se encontró pasajero con cédula: " + cedula);
        } else {
            p.imprimirDetalle();
        }
    }


    private void actualizarConductor() {
        System.out.println("\n── Actualizar Conductor ────────────────");
        System.out.print("  Cédula del conductor: ");
        String cedula = sc.nextLine().trim();

        Conductor c = personaService.buscarConductorPorCedula(cedula);
        if (c == null) {
            System.out.println("  ✘ Conductor no encontrado.");
            return;
        }
        c.imprimirDetalle();

        System.out.println("  ¿Qué desea actualizar?");
        System.out.println("  1. Nombre   2. Licencia");
        System.out.print("  Opción: ");
        int op = leerEntero();

        if (op == 1) {
            System.out.print("  Nuevo nombre: ");
            String nombre = sc.nextLine().trim();
            personaService.actualizarNombreConductor(cedula, nombre);
            System.out.println("  ✔ Nombre actualizado.");
        } else if (op == 2) {
            System.out.print("  Nuevo número de licencia: ");
            String numLic = sc.nextLine().trim();
            System.out.println("  Categoría: 1.B1  2.B2  3.C1  4.C2");
            System.out.print("  Seleccione: ");
            int catOp = leerEntero();
            String[] categorias = {"B1", "B2", "C1", "C2"};
            if (catOp < 1 || catOp > 4) {
                System.out.println("  Categoría inválida.");
                return;
            }
            personaService.actualizarLicencia(cedula, numLic, categorias[catOp - 1]);
            System.out.println("  ✔ Licencia actualizada.");
        } else {
            System.out.println("  Opción inválida.");
        }
    }

    private void actualizarPasajero() {
        System.out.println("\n── Actualizar Pasajero ─────────────────");
        System.out.print("  Cédula del pasajero: ");
        String cedula = sc.nextLine().trim();

        Pasajero p = personaService.buscarPasajeroPorCedula(cedula);
        if (p == null) {
            System.out.println("  ✘ Pasajero no encontrado.");
            return;
        }
        p.imprimirDetalle();

        System.out.println("  ¿Qué desea actualizar?");
        System.out.println("  1. Nombre   2. Tipo");
        System.out.print("  Opción: ");
        int op = leerEntero();

        if (op == 1) {
            System.out.print("  Nuevo nombre: ");
            String nombre = sc.nextLine().trim();
            personaService.actualizarNombrePasajero(cedula, nombre);
            System.out.println("  ✔ Nombre actualizado.");
        } else if (op == 2) {
            System.out.println("  Nuevo tipo: 1.Regular  2.Estudiante  3.Adulto Mayor");
            System.out.print("  Seleccione: ");
            int tipoOp = leerEntero();
            String[] tipos = {"REGULAR", "ESTUDIANTE", "ADULTO_MAYOR"};
            if (tipoOp < 1 || tipoOp > 3) {
                System.out.println("  Tipo inválido.");
                return;
            }
            personaService.actualizarTipoPasajero(cedula, tipos[tipoOp - 1]);
            System.out.println("  ✔ Tipo actualizado.");
        } else {
            System.out.println("  Opción inválida.");
        }
    }


    private void eliminarConductor() {
        System.out.print("\n  Cédula del conductor a eliminar: ");
        String cedula = sc.nextLine().trim();
        System.out.print("  ¿Confirma eliminar? (1=Sí / 0=No): ");
        int confirm = leerEntero();
        if (confirm != 1) {
            System.out.println("  Operación cancelada.");
            return;
        }
        boolean ok = personaService.eliminarConductor(cedula);
        System.out.println(ok ? "  ✔ Conductor eliminado." : "  ✘ Conductor no encontrado.");
    }


    private void eliminarPasajero() {
        System.out.print("\n  Cédula del pasajero a eliminar: ");
        String cedula = sc.nextLine().trim();
        System.out.print("  ¿Confirma eliminar? (1=Sí / 0=No): ");
        int confirm = leerEntero();
        if (confirm != 1) {
            System.out.println("  Operación cancelada.");
            return;
        }
        boolean ok = personaService.eliminarPasajero(cedula);
        System.out.println(ok ? "  ✔ Pasajero eliminado." : "  ✘ Pasajero no encontrado.");
    }


    private int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
