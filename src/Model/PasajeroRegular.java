package Model;

public class PasajeroRegular extends Pasajero{

    public PasajeroRegular(String cedula, String nombre) {
        super(cedula, nombre, "Regular");
    }
    @Override
    public double calcularDescuento(){
        return 0.0;
    }

    @Override
    public void imprimirDetalle() {

    }

    @Override
    public String toString() {
        return "PasajeroRegular " + super.toString();
    }

}
