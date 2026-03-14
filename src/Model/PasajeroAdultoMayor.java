package Model;

public class PasajeroAdultoMayor extends Pasajero{
    public PasajeroAdultoMayor(String cedula, String nombre) {
        super(cedula, nombre, "Adulto_Mayor");
    }
    @Override
    public double calcularDescuento() {
        return 0.30;
    }

    @Override
    public void imprimirDetalle() {
    }
    @Override
    public String toString() {
        return "PasajeroAdultoMayor " + super.toString();
    }
}
