package Model;

import java.time.LocalDate;
import java.time.Period;

public abstract class Pasajero extends Persona {
    private String tipoPasajero;
    private LocalDate fechaNacimiento;

    protected Pasajero(final String cedula, final String nombre, final String tipoPasajero, final LocalDate fechaNacimiento) {
        super(cedula, nombre);
        this.tipoPasajero = tipoPasajero;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoPasajero() {
        return this.tipoPasajero;
    }

    public void setTipoPasajero(final String tipoPasajero) {
        this.tipoPasajero = tipoPasajero;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(final LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
