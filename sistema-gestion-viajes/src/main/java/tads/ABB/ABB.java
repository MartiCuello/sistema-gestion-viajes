package tads.ABB;
import tads.Lista.Lista;

import java.util.Comparator;

public class ABB<T> implements IAbb<T>{

    private NodoABB<T> raiz;
    private Comparator<T> comparador;

    private int nodosRecorridos; //para contar los nodos cuando hace el metodo buscar

    public int getNodosRecorridos(){
        return nodosRecorridos;
    }

    public ABB(Comparator<T> comparador) {
        this.comparador = comparador;
    }

    public void insertar(T dato) {
        raiz = insertarRec(raiz, dato);
    }

    private NodoABB<T> insertarRec(NodoABB<T> nodo, T dato) {
        if (nodo == null){
            return new NodoABB<>(dato);
        }
        int comp = comparador.compare(dato, nodo.getDato());
        if (comp < 0) {
            nodo.setIzq(insertarRec(nodo.getIzq(), dato));
        } else if (comp > 0) {
            nodo.setDer(insertarRec(nodo.getDer(), dato));
        }
        return nodo;
    }

    public boolean pertenece(T dato) {
        return perteneceRec(raiz, dato);
    }

    private boolean perteneceRec(NodoABB<T> nodo, T dato) {
        if (nodo == null){
            return false;
        }
        int comp = comparador.compare(dato, nodo.getDato());
        if (comp == 0) return true;
        return comp < 0 ? perteneceRec(nodo.getIzq(), dato) : perteneceRec(nodo.getDer(), dato);
    }

    public T buscar(T dato) {
        nodosRecorridos = 0;
        return buscarRec(raiz, dato);
    }

    private T buscarRec(NodoABB<T> nodo, T dato) {
        if (nodo == null){
            return null;
        }
        nodosRecorridos++;
        int comp = comparador.compare(dato, nodo.getDato());
        if (comp == 0) {
            return nodo.getDato();
        } else if (comp < 0) {
            return buscarRec(nodo.getIzq(), dato);
        } else {
            return buscarRec(nodo.getDer(), dato);
        }
    }

    //inOrder
    public Lista<T> listarAscendente() {
        Lista<T> lista = new Lista<>();
        listarAscendenteRec(raiz, lista);
        return lista;
    }

    private void listarAscendenteRec(NodoABB<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            listarAscendenteRec(nodo.getIzq(), lista);
            lista.insertar(nodo.getDato());
            listarAscendenteRec(nodo.getDer(), lista);
        }
    }

    public Lista<T> listarDescendente() {
        Lista<T> lista = new Lista<>();
        listarDescendenteRec(raiz, lista);
        return lista;
    }

    private void listarDescendenteRec(NodoABB<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            listarDescendenteRec(nodo.getDer(), lista);
            lista.insertar(nodo.getDato());
            listarDescendenteRec(nodo.getIzq(), lista);
        }
    }
}
