package Dao;

import Model.Pasajero;
import Model.PasajeroRegular;
import Model.PasajeroEstudiante;
import Model.PasajeroAdultoMayor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PasajeroDao {

    private static final String RUTA_ARCHIVO = "data/pasajeros.txt";

    private static final String TIPO_REGULAR      = "REGULAR";
    private static final String TIPO_ESTUDIANTE   = "ESTUDIANTE";
    private static final String TIPO_ADULTO_MAYOR = "ADULTO_MAYOR";

    private List<Pasajero> lista;

    public PasajeroDao() {
        lista = new ArrayList<>();
        cargarTodos();
    }

    public void guardar(Pasajero p) {
        lista.add(p);
        escribirLinea(p);
    }

    public List<Pasajero> cargarTodos() {
        lista.clear();
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    Pasajero p = parsearLinea(linea);
                    if (p != null) {
                        lista.add(p);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[PasajeroDAO] Error al leer el archivo: " + e.getMessage());
        }

        return lista;
    }

    public List<Pasajero> listarTodos() {
        return new ArrayList<>(lista);
    }

    public Pasajero buscarPorCedula(String cedula) {
        for (Pasajero p : lista) {
            if (p.getCedula().equalsIgnoreCase(cedula.trim())) {
                return p;
            }
        }
        return null;
    }

    public List<Pasajero> listarPorTipo(String tipo) {
        List<Pasajero> resultado = new ArrayList<>();
        for (Pasajero p : lista) {
            if (p.getTipoPasajero().equalsIgnoreCase(tipo.trim())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public boolean actualizar(Pasajero pasajeroActualizado) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCedula().equalsIgnoreCase(pasajeroActualizado.getCedula())) {
                lista.set(i, pasajeroActualizado);
                escribirArchivo();
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String cedula) {
        Pasajero encontrado = buscarPorCedula(cedula);
        if (encontrado != null) {
            lista.remove(encontrado);
            escribirArchivo();
            return true;
        }
        return false;
    }

    private void escribirLinea(Pasajero p) {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(aLinea(p));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("[PasajeroDAO] Error al escribir línea: " + e.getMessage());
        }
    }



}
