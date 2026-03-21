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
}
