package tads.ABB;

import tads.Lista.Lista;

public interface IAbb<T> {

    void insertar(T dato);
    boolean pertenece(T dato);
    T buscar(T dato);
    Lista<T> listarAscendente();
    Lista<T> listarDescendente();
}
