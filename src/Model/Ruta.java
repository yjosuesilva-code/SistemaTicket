package Model;

public class Ruta implements Imprimible {
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

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           DETALLE – RUTA             ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Código         : %-18s  ║%n", codigo);
        System.out.printf( "║  Origen         : %-18s  ║%n", ciudadOrigen);
        System.out.printf( "║  Destino        : %-18s  ║%n", ciudadDestino);
        System.out.printf( "║  Distancia      : %-15.1f km  ║%n", distanciaKm);
        System.out.printf( "║  Tiempo estimado: %-13d min  ║%n", tiempoEstimadoMin);
        System.out.println("╚══════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "codigo='" + codigo + '\'' +
                ", ciudadOrigen='" + ciudadOrigen + '\'' +
                ", ciudadDestino='" + ciudadDestino + '\'' +
                ", distanciaKm=" + distanciaKm +
                ", tiempoEstimadoMin=" + tiempoEstimadoMin +
                '}';
    }
}
