package Dao;

import Model.Reserva;

import java.util.List;

public class ReservaDao {
    private static final String RUTA_ARCHIVO = "data/reservas.txt";
    private static final int    CAMPOS       = 12;
    private List<Reserva> lista;

    public ReservaDao() {
        lista = new ArrayList<>();
        cargarTodos();
    }
    public void guardar(Reserva r) {
        lista.add(r);
        escribirLinea(r);
    }
    public List<Reserva> cargarTodos() {
        lista.clear();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    Reserva r = parsearLinea(linea);
                    if (r != null) lista.add(r);
                }
            }
        } catch (IOException e) {
            System.err.println("[ReservaDao] Error al leer archivo: " + e.getMessage());
        }
        return lista;
    }
    public List<Reserva> listarTodos() {
        return new ArrayList<>(lista);
    }
    public List<Reserva> listarActivas() {
        List<Reserva> activas = new ArrayList<>();
        for (Reserva r : lista)
            if (r.getEstado() == Reserva.Estado.ACTIVA) activas.add(r);
        return activas;
    }
    public Reserva buscarPorCodigo(String codigo) {
        for (Reserva r : lista)
            if (r.getCodigo().equalsIgnoreCase(codigo.trim())) return r;
        return null;
    }
    public List<Reserva> buscarPorPasajero(String cedula) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : lista)
            if (r.getPasajero().getCedula().equalsIgnoreCase(cedula.trim())) resultado.add(r);
        return resultado;
    }
    public int contarActivasPorVehiculoYFecha(String placa, LocalDate fechaViaje) {
        int count = 0;
        for (Reserva r : lista) {
            if (r.getEstado() == Reserva.Estado.ACTIVA
                    && r.getVehiculo().getPlaca().equalsIgnoreCase(placa)
                    && r.getFechaViaje().equals(fechaViaje)) {
                count++;
            }
        }
        return count;
    }
}
