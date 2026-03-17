package View;

import Service.VehiculoService;

import java.util.Scanner;

public class VehiculoView {
    private VehiculoService vehiculoService;
    private PersonaService  personaService;
    private Scanner sc;


    public VehiculoView(VehiculoService vehiculoService, PersonaService personaService, Scanner sc) {
        this.vehiculoService = vehiculoService;
        this.personaService  = personaService;
        this.sc              = sc;
    }


}
