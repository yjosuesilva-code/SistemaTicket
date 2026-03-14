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
        return false;
    }

    @Override
    public void imprimirDetalle() {

    }
}
