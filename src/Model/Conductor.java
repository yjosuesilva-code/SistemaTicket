package Model;

public class Conductor extends Persona{
    private String numLicencia;
    private String CateLicencia;

    public Conductor(String cedula, String nombre, String cateLicencia, String numLicencia) {
        super(cedula, nombre);
        CateLicencia = cateLicencia;
        this.numLicencia = numLicencia;
    }

    public String getCateLicencia() {
        return CateLicencia;
    }

    public void setCateLicencia(String cateLicencia) {
        CateLicencia = cateLicencia;
    }

    public String getNumLicencia() {
        return numLicencia;
    }

    public void setNumLicencia(String numLicencia) {
        this.numLicencia = numLicencia;
    }

    public boolean tieneLicencia(){
        return numLicencia != null && !numLicencia.trim().isEmpty();
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         DETALLE – CONDUCTOR          ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Cédula         : %-18s  ║%n", getCedula());
        System.out.printf( "║  Nombre         : %-18s  ║%n", getNombre());
        System.out.printf( "║  Núm. licencia  : %-18s  ║%n", numLicencia);
        System.out.printf( "║  Categoría      : %-18s  ║%n", CateLicencia);
        System.out.println("╚══════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return "Conductor{"+ super.toString() +
                "CateLicencia='" + CateLicencia + '\'' +
                ", numLicencia='" + numLicencia + '\'' +
                "} " ;
    }
}
