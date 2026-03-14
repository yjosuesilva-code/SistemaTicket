package Model;

public class Buseta extends Vehiculo{
    protected static final int CAPACIDAD_MAX=19;
    protected static final double TARIFA_BASE=8000;

    public Buseta(String placa, String ruta) {
        super(placa, ruta, 19, 0, true, 8000);
    }

    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          DETALLE – BUSETA            ║");
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

    @Override
    public String toString() {
        return "Buseta{" + super.toString()+
                "CAPACIDAD_MAX=" + CAPACIDAD_MAX +
                ", TARIFA_BASE=" + TARIFA_BASE +
                "} ";
}
