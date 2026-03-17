package View;

import Model.Bus;
import Model.Buseta;
import Model.MicroBus;
import Model.Vehiculo;
import Service.VehiculoService;

import java.util.List;
import java.util.Scanner;

public class VehiculoView {
    private VehiculoService vehiculoService;
    private PersonaService  personaService;
    private Scanner sc;


    public VehiculoView(VehiculoService vehiculoService, PersonaService personaService, Scanner sc) {
        this.vehiculoService = vehiculoService;
        this.personaService  = personaService;
        this.sc              = sc;
    }

    public void menuVehiculos() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║        GESTIÓN DE VEHÍCULOS          ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Registrar vehículo               ║");
            System.out.println("║  2. Listar todos los vehículos       ║");
            System.out.println("║  3. Buscar vehículo por placa        ║");
            System.out.println("║  4. Listar vehículos disponibles     ║");
            System.out.println("║  5. Actualizar ruta                  ║");
            System.out.println("║  6. Cambiar disponibilidad           ║");
            System.out.println("║  7. Asignar conductor                ║");
            System.out.println("║  8. Eliminar vehículo                ║");
            System.out.println("║  0. Volver al menú principal         ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("  Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: registrarVehiculo();       break;
                case 2: listarVehiculos();         break;
                case 3: buscarPorPlaca();          break;
                case 4: listarDisponibles();       break;
                case 5: actualizarRuta();          break;
                case 6: cambiarDisponibilidad();   break;
                case 7: asignarConductor();        break;
                case 8: eliminarVehiculo();        break;
                case 0: System.out.println("  Volviendo al menú principal..."); break;
                default: System.out.println("  Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private int leerEntero() {
        try {
            String linea = sc.nextLine();
            return Integer.parseInt(linea.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void registrarVehiculo() {
        System.out.println("\n── Registrar Vehículo ──────────────────");
        System.out.println("  Tipo de vehículo:");
        System.out.println("  1. Buseta  (cap. 19 | tarifa $8.000)");
        System.out.println("  2. MicroBus (cap. 25 | tarifa $10.000)");
        System.out.println("  3. Bus     (cap. 45 | tarifa $15.000)");
        System.out.print("  Seleccione tipo: ");
        int tipo = leerEntero();

        if (tipo < 1 || tipo > 3) {
            System.out.println("  Tipo inválido.");
            return;
        }

        System.out.print("  Placa: ");
        String placa = sc.nextLine().trim().toUpperCase();

        System.out.print("  Ruta (ej. Valledupar-Bogotá): ");
        String ruta = sc.nextLine().trim();

        Vehiculo v;
        switch (tipo) {
            case 1: v = new Buseta(placa, ruta);   break;
            case 2: v = new MicroBus(placa, ruta); break;
            default: v = new Bus(placa, ruta);     break;
        }

        boolean resultado = vehiculoService.registrarVehiculo(v);
        if (resultado) {
            System.out.println("   Vehículo registrado exitosamente.");
        } else {
            System.out.println("   No se pudo registrar el vehículo.");
        }
    }

    private void listarVehiculos() {
        List<Vehiculo> lista = vehiculoService.listarVehiculos();
        System.out.println("\n── Lista de Vehículos (" + lista.size() + ") ──────────────");
        if (lista.isEmpty()) {
            System.out.println("  No hay vehículos registrados.");
            return;
        }
        for (Vehiculo v : lista) {
            v.imprimirDetalle();
        }
    }


    private void buscarPorPlaca() {
        System.out.print("\n  Ingrese la placa: ");
        String placa = sc.nextLine().trim().toUpperCase();
        Vehiculo v = vehiculoService.buscarPorPlaca(placa);
        if (v == null) {
            System.out.println("   No se encontró ningún vehículo con placa: " + placa);
        } else {
            v.imprimirDetalle();
        }
    }

    private void listarDisponibles() {
        List<Vehiculo> lista = vehiculoService.listarDisponibles();
        System.out.println("\n── Vehículos Disponibles (" + lista.size() + ") ────────────");
        if (lista.isEmpty()) {
            System.out.println("  No hay vehículos disponibles.");
            return;
        }
        for (Vehiculo v : lista) {
            v.imprimirDetalle();
        }
    }


    private void actualizarRuta() {
        System.out.print("\n  Placa del vehículo: ");
        String placa = sc.nextLine().trim().toUpperCase();
        System.out.print("  Nueva ruta: ");
        String nuevaRuta = sc.nextLine().trim();
        boolean ok = vehiculoService.actualizarRuta(placa, nuevaRuta);
        System.out.println(ok ? "  Ruta actualizada." : "   Vehículo no encontrado.");
    }
    private void cambiarDisponibilidad() {
        System.out.print("\n  Placa del vehículo: ");
        String placa = sc.nextLine().trim().toUpperCase();
        System.out.print("  ¿Disponible? (1=Sí / 0=No): ");
        int op = leerEntero();
        boolean estado = (op == 1);
        boolean ok = vehiculoService.cambiarDisponibilidad(placa, estado);
        System.out.println(ok ? "   Disponibilidad actualizada." : "   Vehículo no encontrado.");
    }

    private void asignarConductor() {
        System.out.print("\n  Placa del vehículo: ");
        String placa = sc.nextLine().trim().toUpperCase();
        System.out.print("  Cédula del conductor: ");
        String cedula = sc.nextLine().trim();

        Conductor conductor = personaService.buscarConductorPorCedula(cedula);
        if (conductor == null) {
            System.out.println("   Conductor no encontrado con cédula: " + cedula);
            return;
        }

        boolean ok = vehiculoService.asignarConductor(placa, conductor);
        System.out.println(ok ? "   Conductor asignado correctamente." : "   No se pudo asignar el conductor.");
    }

    private void eliminarVehiculo() {
        System.out.print("\n  Placa del vehículo a eliminar: ");
        String placa = sc.nextLine().trim().toUpperCase();
        System.out.print("  ¿Confirma eliminar el vehículo " + placa + "? (1=Sí / 0=No): ");
        int confirm = leerEntero();
        if (confirm != 1) {
            System.out.println("  Operación cancelada.");
            return;
        }
        boolean ok = vehiculoService.eliminarVehiculo(placa);
        System.out.println(ok ? "   Vehículo eliminado." : "   Vehículo no encontrado.");
    }
}
