package Model;

public abstract class Pasajero extends Persona {
    private String tipoPasajero;

    public Pasajero(String cedula, String nombre, String tipoPasajero) {
        super(cedula, nombre);
        this.tipoPasajero = tipoPasajero;
    }

    public String getTipoPasajero() {
        return tipoPasajero;
    }

    public void setTipoPasajero(String tipoPasajero) {
        this.tipoPasajero = tipoPasajero;
    }

    @Override
    public abstract void imprimirDetalle();

    public abstract double calcularDescuento();


}
