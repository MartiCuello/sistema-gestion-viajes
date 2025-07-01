package tads.Cola;

public class NodoCola <T>{
    private T dato;
    private NodoCola<T> sig;

    public NodoCola(T dato) {
        this.dato = dato;
        this.sig = null;
    }

    public T getDato() {
        return dato;
    }

    public NodoCola<T> getSig() {
        return sig;
    }

    public void setSig(NodoCola<T> sig) {
        this.sig = sig;
    }

    @Override
    public String toString() {
        return dato.toString();
    }
}
