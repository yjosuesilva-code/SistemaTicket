package Service;

import Dao.VehiculoDao;
import Model.Vehiculo;
import Model.Coductor;
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

    public boolean asignarConductor(String placa, Conductor conductor) {
        if (!conductor.tieneLicencia()) {
            System.out.println("[VehiculoService] Error: el conductor "
                    + conductor.getNombre() + " no tiene licencia registrada.");
            return false;
        }
        Vehiculo v = vehiculoDao.buscarPorPlaca(placa);
        if (v == null) {
            System.out.println("[VehiculoService] Vehículo no encontrado: " + placa);
            return false;
        }
        v.setDisponible(true);
        vehiculoDao.actualizar(v);
        System.out.println("[VehiculoService] Conductor " + conductor.getNombre()
                + " asignado al vehículo " + placa);
        return true;
    }

    public boolean eliminarVehiculo(String placa) {
        if (!placaExiste(placa)) {
            System.out.println("[VehiculoService] Vehículo no encontrado: " + placa);
            return false;
        }
        vehiculoDao.eliminar(placa);
        System.out.println("[VehiculoService] Vehículo eliminado: " + placa);
        return true;
    }


    public int contarDisponibles() {
        return listarDisponibles().size();
    }

    public String vehiculoConMasTickets(java.util.Map<String, Integer> conteoTickets) {
        if (conteoTickets == null || conteoTickets.isEmpty()) {
            return "N/A";
        }
        String placaMax = null;
        int    max      = -1;
        for (java.util.Map.Entry<String, Integer> entry : conteoTickets.entrySet()) {
            if (entry.getValue() > max) {
                max      = entry.getValue();
                placaMax = entry.getKey();
            }
        }
        return placaMax != null ? placaMax : "N/A";
    }

    @Override
    public String toString() {
        return "VehiculoService{" +
                "totalVehiculos=" + vehiculoDao.listarTodos().size() +
                '}';
    }
}
