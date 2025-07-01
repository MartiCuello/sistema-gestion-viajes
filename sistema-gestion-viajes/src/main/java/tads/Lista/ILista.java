package tads.Lista;

import java.util.Comparator;

public interface ILista<T> {
    void insertar(T dato);
    void insertarAlInicio(T dato);
    void insertarOrdenado(T dato, Comparator<T> comp);
    boolean pertenece(T dato);
    boolean esVacia();
}
