package Model;

public class Ruta {
    private String codigo;
    private String ciudadOrigen;
    private String ciudadDestino;
    private double distanciaKm;
    private int tiempoEstimadoMin;

    public Ruta(final String codigo, final String ciudadOrigen, final String ciudadDestino, final double distanciaKm, final int tiempoEstimadoMin) {
        this.codigo = codigo;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.distanciaKm = distanciaKm;
        this.tiempoEstimadoMin = tiempoEstimadoMin;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    public String getCiudadOrigen() {
        return this.ciudadOrigen;
    }

    public void setCiudadOrigen(final String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getCiudadDestino() {
        return this.ciudadDestino;
    }

    public void setCiudadDestino(final String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public double getDistanciaKm() {
        return this.distanciaKm;
    }

    public void setDistanciaKm(final double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public int getTiempoEstimadoMin() {
        return this.tiempoEstimadoMin;
    }

    public void setTiempoEstimadoMin(final int tiempoEstimadoMin) {
        this.tiempoEstimadoMin = tiempoEstimadoMin;
    }
}
