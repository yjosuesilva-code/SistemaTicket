package Service;

import Dao.ConductorDao;
import Dao.PasajeroDao;
import Model.Conductor;
import Model.Pasajero;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PersonaService {
    private ConductorDao conductorDao;
    private PasajeroDao  pasajeroDao;

    public PersonaService(ConductorDao conductorDao, PasajeroDao pasajeroDao) {
        this.conductorDao = conductorDao;
        this.pasajeroDao  = pasajeroDao;
    }

    public boolean registrarConductor(Conductor c) {
        if (cedulaConductorExiste(c.getCedula())) {
            System.out.println("[PersonaService] Error: ya existe un conductor con cédula " + c.getCedula());
            return false;
        }
        if (!c.tieneLicencia()) {
            System.out.println("[PersonaService] Error: el conductor no tiene licencia registrada.");
            return false;
        }
        conductorDao.guardar(c);
        System.out.println("[PersonaService] Conductor registrado: " + c.getNombre());
        return true;
    }

    public List<Conductor> listarConductores() {
        return conductorDao.listarTodos();
    }

    public Conductor buscarConductorPorCedula(String cedula) {
        return conductorDao.buscarPorCedula(cedula);
    }

    public boolean cedulaConductorExiste(String cedula) {
        return conductorDao.buscarPorCedula(cedula) != null;
    }

    public List<Conductor> listarConductoresPorCategoria(String categoria) {
        return conductorDao.listarPorCategoria(categoria);
    }

    public boolean actualizarNombreConductor(String cedula, String nuevoNombre) {
        Conductor c = conductorDao.buscarPorCedula(cedula);
        if (c == null) {
            System.out.println("[PersonaService] Conductor no encontrado: " + cedula);
            return false;
        }
        c.setNombre(nuevoNombre);
        conductorDao.actualizar(c);
        return true;
    }

    public boolean actualizarLicencia(String cedula, String numLicencia, String categoria) {
        Conductor c = conductorDao.buscarPorCedula(cedula);
        if (c == null) {
            System.out.println("[PersonaService] Conductor no encontrado: " + cedula);
            return false;
        }
        if (numLicencia == null || numLicencia.trim().isEmpty()) {
            System.out.println("[PersonaService] Error: el número de licencia no puede estar vacío.");
            return false;
        }
        c.setNumLicencia(numLicencia);
        c.setCateLicencia(categoria);
        conductorDao.actualizar(c);
        return true;
    }

    public boolean eliminarConductor(String cedula) {
        if (!cedulaConductorExiste(cedula)) {
            System.out.println("[PersonaService] Conductor no encontrado: " + cedula);
            return false;
        }
        conductorDao.eliminar(cedula);
        System.out.println("[PersonaService] Conductor eliminado: " + cedula);
        return true;
    }

    public boolean registrarPasajero(Pasajero p) {
        if (cedulaPasajeroExiste(p.getCedula())) {
            System.out.println("[PersonaService] Error: ya existe un pasajero con cédula " + p.getCedula());
            return false;
        }
        pasajeroDao.guardar(p);
        System.out.println("[PersonaService] Pasajero registrado: " + p.getNombre()
                + " (" + p.getTipoPasajero() + ")");
        return true;
    }

    public List<Pasajero> listarPasajeros() {
        return pasajeroDao.listarTodos();
    }

    public Pasajero buscarPasajeroPorCedula(String cedula) {
        return pasajeroDao.buscarPorCedula(cedula);
    }

    public boolean cedulaPasajeroExiste(String cedula) {
        return pasajeroDao.buscarPorCedula(cedula) != null;
    }

    public List<Pasajero> listarPasajerosPorTipo(String tipo) {
        return pasajeroDao.listarPorTipo(tipo);
    }

    public boolean actualizarNombrePasajero(String cedula, String nuevoNombre) {
        Pasajero p = pasajeroDao.buscarPorCedula(cedula);
        if (p == null) {
            System.out.println("[PersonaService] Pasajero no encontrado: " + cedula);
            return false;
        }
        p.setNombre(nuevoNombre);
        pasajeroDao.actualizar(p);
        return true;
    }

    public boolean actualizarTipoPasajero(String cedula, String nuevoTipo) {
        Pasajero p = pasajeroDao.buscarPorCedula(cedula);
        if (p == null) {
            System.out.println("[PersonaService] Pasajero no encontrado: " + cedula);
            return false;
        }
        if (!tipoValido(nuevoTipo)) {
            System.out.println("[PersonaService] Tipo de pasajero inválido: " + nuevoTipo);
            return false;
        }
        p.setTipoPasajero(nuevoTipo.toUpperCase());
        pasajeroDao.actualizar(p);
        return true;
    }

    public boolean eliminarPasajero(String cedula) {
        if (!cedulaPasajeroExiste(cedula)) {
            System.out.println("[PersonaService] Pasajero no encontrado: " + cedula);
            return false;
        }
        pasajeroDao.eliminar(cedula);
        System.out.println("[PersonaService] Pasajero eliminado: " + cedula);
        return true;
    }

    public Map<String, Integer> contarPasajerosPorTipo() {
        Map<String, Integer> conteo = new HashMap<>();
        conteo.put("REGULAR",      pasajeroDao.listarPorTipo("REGULAR").size());
        conteo.put("ESTUDIANTE",   pasajeroDao.listarPorTipo("ESTUDIANTE").size());
        conteo.put("ADULTO_MAYOR", pasajeroDao.listarPorTipo("ADULTO_MAYOR").size());
        return conteo;
    }

    public double calcularDescuentoPasajero(String cedula) {
        Pasajero p = pasajeroDao.buscarPorCedula(cedula);
        if (p == null) {
            System.out.println("[PersonaService] Pasajero no encontrado: " + cedula);
            return -1;
        }
        return p.calcularDescuento();
    }

    private boolean tipoValido(String tipo) {
        return tipo != null && (
                tipo.equalsIgnoreCase("REGULAR") ||
                        tipo.equalsIgnoreCase("ESTUDIANTE") ||
                        tipo.equalsIgnoreCase("ADULTO_MAYOR")
        );
    }
    @Override
    public String toString() {
        return "PersonaService{" +
                "conductores=" + conductorDao.listarTodos().size() +
                ", pasajeros=" + pasajeroDao.listarTodos().size() +
                '}';
    }
}
