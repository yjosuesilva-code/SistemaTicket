package Model;

import java.time.LocalDate;

public class PasajeroRegular extends Pasajero{

    public PasajeroRegular( String cedula,  String nombre, String tipoPasajero, LocalDate fechaNacimiento) {
        super(cedula, nombre, "Regular", fechaNacimiento);
    }

    @Override
    public double calcularDescuento(){
        return 0.0;
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       DETALLE – PASAJERO REGULAR     ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Cédula         : %-18s  ║%n", getCedula());
        System.out.printf( "║  Nombre         : %-18s  ║%n", getNombre());
        System.out.printf( "║  Tipo           : %-18s  ║%n", getTipoPasajero());
        System.out.printf( "║  Fecha nac.     : %-18s  ║%n", getFechaNacimiento());
        System.out.printf( "║  Edad           : %-18d  ║%n", getEdad());
        System.out.printf( "║  Descuento      : %-18s  ║%n", "Sin descuento");
        System.out.println("╚══════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return "PasajeroRegular " + super.toString();
    }

}
