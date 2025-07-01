package dominio;

import tads.Lista.Lista;
import tads.Lista.Nodo;

public class Conexion {
    private Ciudad origen;
    private Ciudad destino;
    private Lista<Vuelo> vuelos;

    public Conexion(Ciudad origen,Ciudad destino) {
        this.origen = origen;
        this.destino = destino;
        this.vuelos = new Lista<>();
    }

    public Ciudad getOrigen(){
        return origen;
    }

    public Ciudad getDestino() {
        return destino;
    }

    public Lista<Vuelo> getVuelos() {
        return vuelos;
    }

    public boolean tieneVueloConCodigo(String codigo) {
        Nodo<Vuelo> actual = vuelos.getCabeza();
        while (actual != null) {
            if (actual.getDato().getCodigo().equals(codigo)) {
                return true;
            }
            actual = actual.getSig();
        }
        return false;
    }

    public Vuelo buscarVueloPorCodigo(String codigoVuelo) {
        Nodo<Vuelo> actual = vuelos.getCabeza();
        while (actual != null) {
            if (actual.getDato().getCodigo().equals(codigoVuelo)) {
                return actual.getDato();
            }
            actual = actual.getSig();
        }
        return null;
    }

}
