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
}
