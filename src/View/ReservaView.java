package View;

import java.util.Scanner;

public class ReservaView {
    private ReservaService reservaService;
    private Scanner sc;

    public ReservaView(ReservaService reservaService, Scanner sc) {
        this.reservaService = reservaService;
        this.sc             = sc;
    }

}
