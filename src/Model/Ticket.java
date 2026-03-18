package Model;

public class Ticket implements Calculable {
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private LocalDate fechaCompra;
    private String origen;
    private String destino;
    private double valorFinal;
    public Ticket(Pasajero pasajero, Vehiculo vehiculo, String origen, String destino) {
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.origen = origen;
        this.destino = destino;
        this.fechaCompra= LocalDate.now();
        this.valorFinal = calcularTotal();
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }


    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public double calcularTotal() {
        double tarifa    = vehiculo.getTarifaBase();
        double descuento = pasajero.calcularDescuento();
        return tarifa - (tarifa * descuento);
    }

    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║           DETALLE – TICKET               ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║  Pasajero       : %-20s  ║%n", pasajero.getNombre());
        System.out.printf( "║  Cédula         : %-20s  ║%n", pasajero.getCedula());
        System.out.printf( "║  Tipo pasajero  : %-20s  ║%n", pasajero.getTipoPasajero());
        System.out.printf( "║  Vehículo       : %-20s  ║%n", vehiculo.getPlaca());
        System.out.printf( "║  Ruta           : %-20s  ║%n", vehiculo.getRuta());
        System.out.printf( "║  Origen         : %-20s  ║%n", origen);
        System.out.printf( "║  Destino        : %-20s  ║%n", destino);
        System.out.printf( "║  Fecha compra   : %-20s  ║%n", fechaCompra.toString());
        System.out.printf( "║  Tarifa base    : $%-19.0f  ║%n", vehiculo.getTarifaBase());
        System.out.printf( "║  Descuento      : %-19.0f%%  ║%n", pasajero.calcularDescuento() * 100);
        System.out.printf( "║  Valor final    : $%-19.0f  ║%n", valorFinal);
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
