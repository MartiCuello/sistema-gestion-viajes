package dominio;

import java.util.Comparator;

public class ComparadorCiudad implements Comparator<Ciudad> {
    @Override
    public int compare(Ciudad c1, Ciudad c2) {
        return c1.getCodigo().compareTo(c2.getCodigo());
    }
}
