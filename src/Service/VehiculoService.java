package Service;

import Dao.VehiculoDao;
import Model.Vehiculo;

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
}
