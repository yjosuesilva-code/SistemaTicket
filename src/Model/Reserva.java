package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {
    public enum Estado { ACTIVA, CONVERTIDA, CANCELADA }
    private String        codigo;
    private Pasajero      pasajero;
    private Vehiculo      vehiculo;
    private LocalDateTime fechaCreacion;
    private LocalDate fechaViaje;
    private Estado        estado;

    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo, LocalDate fechaViaje) {
        this.codigo        = codigo;
        this.pasajero      = pasajero;
        this.vehiculo      = vehiculo;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaViaje    = fechaViaje;
        this.estado        = Estado.ACTIVA;
    }

    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo,
                   LocalDateTime fechaCreacion, LocalDate fechaViaje, Estado estado) {
        this.codigo        = codigo;
        this.pasajero      = pasajero;
        this.vehiculo      = vehiculo;
        this.fechaCreacion = fechaCreacion;
        this.fechaViaje    = fechaViaje;
        this.estado        = estado;
    }
    public String getCodigo()               { return codigo; }
    public Pasajero getPasajero()           { return pasajero; }
    public Vehiculo getVehiculo()           { return vehiculo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDate getFechaViaje()        { return fechaViaje; }
    public Estado getEstado()               { return estado; }

    public void setEstado(Estado estado)    { this.estado = estado; }

    public boolean estaVencida() {
        return estado == Estado.ACTIVA &&
                fechaCreacion.isBefore(LocalDateTime.now().minusHours(24));
    }
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║          DETALLE – RESERVA               ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║  Código         : %-22s║%n", codigo);
        System.out.printf( "║  Pasajero       : %-22s║%n", pasajero.getNombre());
        System.out.printf( "║  Cédula         : %-22s║%n", pasajero.getCedula());
        System.out.printf( "║  Vehículo       : %-22s║%n", vehiculo.getPlaca());
        System.out.printf( "║  Ruta           : %-22s║%n", vehiculo.getRuta());
        System.out.printf( "║  Fecha creación : %-22s║%n", fechaCreacion.toString().replace("T", " ").substring(0, 16));
        System.out.printf( "║  Fecha viaje    : %-22s║%n", fechaViaje.toString());
        System.out.printf( "║  Estado         : %-22s║%n", estado.toString());
        System.out.println("╚══════════════════════════════════════════╝");
    }
    @Override
    public String toString() {
        return "Reserva{codigo='" + codigo + "', pasajero=" + pasajero.getCedula()
                + ", vehiculo=" + vehiculo.getPlaca() + ", estado=" + estado + "}";
    }
}
