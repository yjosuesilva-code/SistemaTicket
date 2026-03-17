package View;

/**
 * TicketView
 * Interfaz de consola para la gestión de tickets.
 * Solo interactúa con TicketService, nunca con el DAO directamente.
 *
 * Capa: View
 * Proyecto: TransCesar S.A.S.
 */

public class TicketView {
    // ─── Dependencias ─────────────────────────────────────────────────────────
    private TicketService ticketService;
    private Scanner       sc;

    // ─────────────────────────────────────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────────────────
    public TicketView(TicketService ticketService, Scanner sc) {
        this.ticketService = ticketService;
        this.sc            = sc;
    }
}
