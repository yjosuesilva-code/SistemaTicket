package Service;

public class ReservaService {
    private static final double RECARGO_FESTIVO = 0.20;

    private ReservaDao  reservaDao;
    private VehiculoDao vehiculoDao;
    private PasajeroDao pasajeroDao;
    private TicketDao   ticketDao;
    private TicketService ticketService;

    public ReservaService(ReservaDao reservaDao, VehiculoDao vehiculoDao, PasajeroDao pasajeroDao, TicketDao ticketDao, TicketService ticketService) {
        this.reservaDao    = reservaDao;
        this.vehiculoDao   = vehiculoDao;
        this.pasajeroDao   = pasajeroDao;
        this.ticketDao     = ticketDao;
        this.ticketService = ticketService;
        verificarVencidas();
    }

}
