package tads.Lista;

import java.util.Comparator;

public class Lista<T> implements ILista<T> {

    private Nodo<T> cabeza;
    private Nodo<T> fin;
    private int longitud;

    public Nodo<T> getCabeza() {
        return cabeza;
    }

    public Nodo<T> getFin() {
        return fin;
    }

    public int getLongitud() {
        return longitud;
    }

    @Override
    public void insertar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (this.esVacia()) {
            this.cabeza = nuevo;
        } else {
            this.fin.setSig(nuevo);
        }
        this.fin = nuevo;
        longitud++;
    }

    @Override
    public void insertarAlInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSig(this.cabeza);
        this.cabeza = nuevo;
    }

    @Override
    public void insertarOrdenado(T dato, Comparator<T> comp) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null || comp.compare(dato, cabeza.getDato()) < 0) {
            nuevo.setSig(cabeza);
            cabeza = nuevo;
            if (fin == null) fin = nuevo;
            longitud++;
            return;
        }

        Nodo<T> actual = cabeza;
        while (actual.getSig() != null && comp.compare(dato, actual.getSig().getDato()) >= 0) {
            actual = actual.getSig();
        }

        nuevo.setSig(actual.getSig());
        actual.setSig(nuevo);

        if (nuevo.getSig() == null) fin = nuevo;
        longitud++;
    }


    @Override
    public boolean pertenece(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSig();
        }
        return false;
    }

    @Override
    public boolean esVacia() {
        return this.cabeza == null;
    }
}

