package View;

import Model.Conductor;
import Model.Pasajero;
import Model.PasajeroAdultoMayor;
import Model.PasajeroEstudiante;
import Model.PasajeroRegular;
import Service.PersonaService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * PersonaView
 * Interfaz de consola para la gestión de conductores y pasajeros.
 * Solo interactúa con PersonaService, nunca con el DAO directamente.
 *
 * Capa: View
 * Proyecto: TransCesar S.A.S.
 */

public class PersonaView {
    // ─── Dependencias ─────────────────────────────────────────────────────────
    private PersonaService personaService;
    private Scanner        sc;

    // ─────────────────────────────────────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────────────────
    public PersonaView(PersonaService personaService, Scanner sc) {
        this.personaService = personaService;
        this.sc             = sc;
    }
}
