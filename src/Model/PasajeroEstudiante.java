package Model;

public class PasajeroEstudiante extends Pasajero{
    public PasajeroEstudiante(String cedula, String nombre) {
        super(cedula, nombre, "Estudiante");
    }

    @Override
    public double calcularDescuento() {
        return 0.15;
    }
    @Override
    public void imprimirDetalle() {

    }

    @Override
    public String toString() {
        return "pasajeroEstudiante " + super.toString();
    }

}
