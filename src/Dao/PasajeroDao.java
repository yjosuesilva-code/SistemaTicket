package Dao;



public class PasajeroDao {

    private static final String RUTA_ARCHIVO = "data/pasajeros.txt";

    private static final String TIPO_REGULAR      = "REGULAR";
    private static final String TIPO_ESTUDIANTE   = "ESTUDIANTE";
    private static final String TIPO_ADULTO_MAYOR = "ADULTO_MAYOR";

    private List<Pasajero> lista;

    public PasajeroDao() {
        lista = new ArrayList<>();
        cargarTodos();
    }
}
