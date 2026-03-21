package Model;

import java.time.LocalDate;

public class PasajeroEstudiante extends Pasajero{
    public PasajeroEstudiante(String cedula, String nombre, LocalDate fechaNacimiento) {
        super(cedula, nombre, "Estudiante",fechaNacimiento);
    }

    @Override
    public double calcularDescuento() {
        return 0.15;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     DETALLE – PASAJERO ESTUDIANTE    ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Cédula         : %-18s  ║%n", getCedula());
        System.out.printf( "║  Nombre         : %-18s  ║%n", getNombre());
        System.out.printf( "║  Tipo           : %-18s  ║%n", getTipoPasajero());
        System.out.printf( "║  Fecha nac.     : %-18s  ║%n", getFechaNacimiento());
        System.out.printf( "║  Edad           : %-18d  ║%n", getEdad());
        System.out.printf( "║  Descuento      : %-18s  ║%n", "15%");
        System.out.println("╚══════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return "pasajeroEstudiante " + super.toString();
    }

}
