package Model;

public class Bus extends Vehiculo {
    protected static final int CAPACIDAD_MAX = 45;
    protected static final double TARIFA_BASE = 15000.0;

    public Bus(String placa, String ruta) {
        super(placa, ruta, 45, 0, true, 15000);
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║            DETALLE – BUS             ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Placa          : %-18s  ║%n", getPlaca());
        System.out.printf( "║  Ruta           : %-18s  ║%n", getRuta());
        System.out.printf( "║  Capacidad max  : %-18d  ║%n", getCapacidadMaxima());
        System.out.printf( "║  Pasajeros      : %-18d  ║%n", getContadorPasajeros());
        System.out.printf( "║  Cupos libres   : %-18d  ║%n", getCapacidadMaxima() - getContadorPasajeros());
        System.out.printf( "║  Disponible     : %-18s  ║%n", isDisponible() ? "Sí" : "No");
        System.out.printf( "║  Tarifa base    : $%-17.0f  ║%n", getTarifaBase());
        System.out.println("╚══════════════════════════════════════╝");
    }
}
