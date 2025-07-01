package dominio;

import java.util.Comparator;

public class ComparadorPorCedula implements Comparator<Viajero> {

    @Override
    public int compare(Viajero v1, Viajero v2) {
        String cedula1Limpia = v1.getCedula().replaceAll("[^\\d]", "");
        String cedula2Limpia = v2.getCedula().replaceAll("[^\\d]", "");

        long cedula1 = Long.parseLong(cedula1Limpia);
        long cedula2 = Long.parseLong(cedula2Limpia);
        return Long.compare(cedula1, cedula2);
    }
}
