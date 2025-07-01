package dominio;

import tads.Lista.Lista;

public class Costo {
    public double valor;
    public Lista<Ciudad> camino;

    public Costo(double valor, Lista<Ciudad> camino) {
        this.valor = valor;
        this.camino = camino;
    }

    public double getValor(){
        return valor;
    }
    public Lista<Ciudad> getCamino(){
        return camino;
    }
}

