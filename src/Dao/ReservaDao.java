package Dao;

import Model.Reserva;

import java.util.List;

public class ReservaDao {
    private static final String RUTA_ARCHIVO = "data/reservas.txt";
    private static final int    CAMPOS       = 12;
    private List<Reserva> lista;
}
