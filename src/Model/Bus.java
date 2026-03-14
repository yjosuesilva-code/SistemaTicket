package Model;

public class Bus extends Vehiculo {
    protected static final int CAPACIDAD_MAX = 45;
    protected static final double TARIFA_BASE = 15000.0;

    public Bus(String placa, String ruta) {
        super(placa, ruta, 45, 0, true, 15000);
    }
}
