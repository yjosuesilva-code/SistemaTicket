import java.util.Scanner;

public class Main {
    VehiculoDao vehiculoDao  = new VehiculoDao();
    ConductorDao conductorDao = new ConductorDao();
    PasajeroDao pasajeroDao  = new PasajeroDao();
    TicketDao ticketDao      = new TicketDao();


    VehiculoService vehiculoService = new VehiculoService(vehiculoDao);
    PersonaService  personaService  = new PersonaService(conductorDao, pasajeroDao);
    TicketService   ticketService   = new TicketService(ticketDao, vehiculoDao, pasajeroDao);

    Scanner sc = new Scanner(System.in);

    VehiculoView vehiculoView = new VehiculoView(vehiculoService, personaService, sc);
    PersonaView  personaView  = new PersonaView(personaService, sc);
    TicketView   ticketView   = new TicketView(ticketService, sc);

    MenuPrincipal menu = new MenuPrincipal();
        menu.ejecutar(vehiculoView, personaView, ticketView, sc);

        sc.close();
}
