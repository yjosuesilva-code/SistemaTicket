package Model;

import java.time.LocalDate;

public abstract class Pasajero extends Persona {
    private String tipoPasajero;
    private LocalDate fechaNacimiento;

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

    @Override
    public String toString() {
        return "Pasajero " +"\n"+ super.toString()+
                "\ntipoPasajero='" + tipoPasajero;
    }
}
