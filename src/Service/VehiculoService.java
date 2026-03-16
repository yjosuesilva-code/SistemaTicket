package Service;

import Dao.VehiculoDao;
import Model.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class VehiculoService {

    private VehiculoDao vehiculoDao;

    public VehiculoService(VehiculoDao vehiculoDao) {
        this.vehiculoDao = vehiculoDao;
    }
    public boolean registrarVehiculo(Vehiculo v) {
        if (placaExiste(v.getPlaca())) {
            System.out.println("[VehiculoService] Error: ya existe un vehículo con placa " + v.getPlaca());
            return false;
        }
        vehiculoDao.guardar(v);
        System.out.println("[VehiculoService] Vehículo registrado: " + v.getPlaca()
                + " | Ruta: " + v.getRuta());
        return true;
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculoDao.listarTodos();
    }


    public Vehiculo buscarPorPlaca(String placa) {
        return vehiculoDao.buscarPorPlaca(placa);
    }

    public boolean placaExiste(String placa) {
        return vehiculoDao.buscarPorPlaca(placa) != null;
    }


    public List<Vehiculo> listarDisponibles() {
        List<Vehiculo> disponibles = new ArrayList<>();
        for (Vehiculo v : vehiculoDao.listarTodos()) {
            if (v.isDisponible()) {
                disponibles.add(v);
            }
        }
        return disponibles;
    }

    public List<Vehiculo> listarConCupos() {
        List<Vehiculo> conCupos = new ArrayList<>();
        for (Vehiculo v : vehiculoDao.listarTodos()) {
            if (v.hayCapacidad()) {
                conCupos.add(v);
            }
        }
        return conCupos;
    }


    public boolean actualizarRuta(String placa, String nuevaRuta) {
        Vehiculo v = vehiculoDao.buscarPorPlaca(placa);
        if (v == null) {
            System.out.println("[VehiculoService] Vehículo no encontrado: " + placa);
            return false;
        }
        v.setRuta(nuevaRuta);
        vehiculoDao.actualizar(v);
        System.out.println("[VehiculoService] Ruta actualizada para: " + placa);
        return true;
    }

    public boolean cambiarDisponibilidad(String placa, boolean estado) {
        Vehiculo v = vehiculoDao.buscarPorPlaca(placa);
        if (v == null) {
            System.out.println("[VehiculoService] Vehículo no encontrado: " + placa);
            return false;
        }
        v.setDisponible(estado);
        vehiculoDao.actualizar(v);
        System.out.println("[VehiculoService] Disponibilidad de " + placa
                + " cambiada a: " + (estado ? "disponible" : "no disponible"));
        return true;
    }
}
