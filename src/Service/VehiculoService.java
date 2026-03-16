package Service;

import Dao.VehiculoDao;

public class VehiculoService {

    private VehiculoDao vehiculoDao;

    public VehiculoService(VehiculoDao vehiculoDao) {
        this.vehiculoDao = vehiculoDao;
    }
}
