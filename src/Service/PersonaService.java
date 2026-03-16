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


}
