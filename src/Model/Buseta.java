package Model;

public class Buseta extends Vehiculo{
    protected static final int CAPACIDAD_MAX=19;
    protected static final double TARIFA_BASE=8000;

    public Buseta(String placa, String ruta) {
        super(placa, ruta, 19, 0, true, 8000);
    }
}
