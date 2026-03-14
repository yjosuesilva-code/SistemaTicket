package Model;

public abstract class Vehiculo {
    private String placa;
    private String ruta;
    private int capacidadMaxima;
    private int contadorPasajeros;
    private boolean disponible;
    private double tarifaBase;

    public Vehiculo(String placa, String ruta, int capacidadMaxima, int contadorPasajeros, boolean disponible, double tarifaBase) {
        this.placa = placa;
        this.ruta = ruta;
        this.capacidadMaxima = capacidadMaxima;
        this.contadorPasajeros = contadorPasajeros;
        this.disponible = true;
        this.tarifaBase = 0;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {

        this.capacidadMaxima = capacidadMaxima;
    }

    public int getContadorPasajeros() {
        return contadorPasajeros;
    }

    public void setContadorPasajeros(int contadorPasajeros) {
        this.contadorPasajeros = contadorPasajeros;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public boolean hayCapacidad(){
        return contadorPasajeros < capacidadMaxima;
    }

    public abstract void imprimirDetalle();

    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", ruta='" + ruta + '\'' +
                ", capacidadMaxima=" + capacidadMaxima +
                ", contadorPasajeros=" + contadorPasajeros +
                ", disponible=" + disponible +
                ", tarifaBase=" + tarifaBase +
                '}';
    }
}