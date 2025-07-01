package dominio;

import tads.Lista.Lista;
import tads.Lista.Nodo;

public class Ciudad {
    private String codigo;
    private String nombre;
    private Lista<Conexion> conexiones;

    public Ciudad(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.conexiones = new Lista<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre(){
        return nombre;
    }

    public Lista<Conexion> getConexiones(){
        return conexiones;
    }

    public boolean tieneConexionHacia(Ciudad destino) {
        Nodo<Conexion> actual = conexiones.getCabeza();
        while (actual != null) {
            if (actual.getDato().getDestino().equals(destino)) {
                return true;
            }
            actual = actual.getSig();
        }
        return false;
    }

}
