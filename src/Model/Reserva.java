package Model;

public class Reserva {
    public enum Estado { ACTIVA, CONVERTIDA, CANCELADA }
    private String        codigo;
    private Pasajero      pasajero;
    private Vehiculo      vehiculo;
    private LocalDateTime fechaCreacion;
    private LocalDate     fechaViaje;
    private Estado        estado;
}
