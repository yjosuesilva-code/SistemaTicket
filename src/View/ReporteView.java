package View;

import Service.TicketService;

import java.util.Scanner;

public class ReporteView {
    private TicketService ticketService;
    private Scanner sc;

    public ReporteView(TicketService ticketService, Scanner sc) {
        this.ticketService = ticketService;
        this.sc            = sc;
    }

}
