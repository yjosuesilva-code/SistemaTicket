package Model;

public class MicroBus extends Vehiculo {
    protected static final int CAPACIDAD_MAX = 25;
    protected static final double TARIFA_BASE = 10000.0;

    public MicroBus(String placa, String ruta) {
        super(placa, ruta, 25, 0, true, 10000);
    }
}
