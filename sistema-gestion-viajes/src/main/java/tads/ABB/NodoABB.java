package tads.ABB;

public class NodoABB<T> implements Comparable<T> {
    private T dato;
    private NodoABB<T> izq;
    private NodoABB<T> der;

    public NodoABB(T dato) {
        this.dato = dato;
    }

    public T getDato() { return dato; }
    public NodoABB<T> getIzq() {
        return izq;
    }

    public NodoABB<T> getDer() {
        return der;
    }

    public void setIzq(NodoABB<T> izq) {
        this.izq = izq;
    }

    public void setDer(NodoABB<T> der) {
        this.der = der;
    }

    @Override
    public int compareTo(T o) {
        return 0;
    }
}
