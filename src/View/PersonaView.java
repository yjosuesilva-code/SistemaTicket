package View;

import Model.Conductor;
import Model.Pasajero;
import Model.PasajeroAdultoMayor;
import Model.PasajeroEstudiante;
import Model.PasajeroRegular;
import Service.PersonaService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * PersonaView
 * Interfaz de consola para la gestión de conductores y pasajeros.
 * Solo interactúa con PersonaService, nunca con el DAO directamente.
 *
 * Capa: View
 * Proyecto: TransCesar S.A.S.
 */

public class PersonaView {
    // ─── Dependencias ─────────────────────────────────────────────────────────
    private PersonaService personaService;
    private Scanner        sc;

    // ─────────────────────────────────────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────────────────
    public PersonaView(PersonaService personaService, Scanner sc) {
        this.personaService = personaService;
        this.sc             = sc;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MENÚ PRINCIPAL DE PERSONAS
    // ─────────────────────────────────────────────────────────────────────────
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

    // ─────────────────────────────────────────────────────────────────────────
    // 1. REGISTRAR CONDUCTOR
    // ─────────────────────────────────────────────────────────────────────────
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

    // ─────────────────────────────────────────────────────────────────────────
    // 2. REGISTRAR PASAJERO
    // ─────────────────────────────────────────────────────────────────────────
    private void registrarPasajero() {
        System.out.println("\n── Registrar Pasajero ──────────────────");
        System.out.print("  Cédula: ");
        String cedula = sc.nextLine().trim();
        System.out.print("  Nombre completo: ");
        String nombre = sc.nextLine().trim();
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

    // ─────────────────────────────────────────────────────────────────────────
    // 3. LISTAR CONDUCTORES
    // ─────────────────────────────────────────────────────────────────────────
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

    // ─────────────────────────────────────────────────────────────────────────
    // 4. LISTAR PASAJEROS
    // ─────────────────────────────────────────────────────────────────────────
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

}
