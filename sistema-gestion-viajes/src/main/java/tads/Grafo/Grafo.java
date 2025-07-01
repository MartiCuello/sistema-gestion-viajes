package tads.Grafo;

import dominio.*;
import interfaz.TipoVueloPermitido;
import tads.Lista.Lista;
import tads.Lista.Nodo;
import tads.Cola.Cola;


public class Grafo {
    private Ciudad[] ciudades;
    private int maxCiudades;
    private int cantidadCiudades;

    public Grafo(int maxCiudades) {
        this.maxCiudades = maxCiudades;
        this.ciudades = new Ciudad[maxCiudades];
        this.cantidadCiudades = 0;
    }

    public Ciudad buscarCiudad(String codigo) {
        for (int i = 0; i < cantidadCiudades; i++) {
            if (ciudades[i].getCodigo().equals(codigo)) {
                return ciudades[i];
            }
        }
        return null;
    }

    public boolean agregarCiudad(Ciudad ciudad) {
        if (cantidadCiudades >= maxCiudades){
            return false;
        }
        ciudades[cantidadCiudades++] = ciudad;
        return true;
    }

    public boolean existeConexion(Ciudad origen, Ciudad destino) {
        return origen.tieneConexionHacia(destino);
    }

    public boolean agregarConexion(Ciudad origen, Ciudad destino) {
        if (origen == null || destino == null){
            return false;
        }
        if (origen.tieneConexionHacia(destino)){
            return false;
        }

        Conexion nuevaConexion = new Conexion(origen, destino);
        origen.getConexiones().insertar(nuevaConexion);
        return true;
    }

    public Conexion buscarConexion(Ciudad origen, Ciudad destino) {
        Lista<Conexion> conexiones = origen.getConexiones();
        Nodo<Conexion> actual = conexiones.getCabeza();
        while (actual != null) {
            Conexion c = actual.getDato();
            if (c.getDestino().equals(destino)) {
                return c;
            }
            actual = actual.getSig();
        }
        return null;
    }

    public Lista<Ciudad> bfsHastaEscalas(Ciudad origen, int cantidad) {
        Lista<Ciudad> resultado = new Lista<>(); //retorno
        Cola<Ciudad> cola = new Cola<>(); //cola para ir poniendo y sacando ciudades
        Lista<Ciudad> visitados = new Lista<>(); //lista temporal para evitar visitar dos veces la misma ciudad
        //empezamos insertando en la cola y en visitados la ciudad de origen
        cola.encolar(origen);
        visitados.insertar(origen);

        int niveles = 0; //contador de niveles recorridos (escalas)0
        int nodosEnNivel = 1; //la cantidad de nodos en el nivel 0 es 1 (origen)
        int nodosEnNivelSiguiente = 0;

        //mientras la cola no sea vacia y no se haya alcanzado la cantidad de escalas dada
        while (!cola.esVacia() && niveles <= cantidad) {
            Ciudad actual = cola.desencolar(); //la ciudad actual va a ser la primera de la cola
            resultado.insertarOrdenado(actual, new ComparadorCiudad()); //se inserta ordenado por codigo de ciudad en la lista resultante
            nodosEnNivel--; // se proceso un nodo en el nivel actual

            //se recorren todas las conexiones de la ciudad actual
            Nodo<Conexion> conexiones = actual.getConexiones().getCabeza();
            //mientras haya una conexion
            while (conexiones != null) {
                Conexion conexion = conexiones.getDato();
                Ciudad destino = conexion.getDestino(); //guarda la ciudad a la que lleva esa conexion
                //si la conexion tiene vuelos y la ciudad no fue visitada
                if (conexion.getVuelos() != null && !conexion.getVuelos().esVacia() && !visitados.pertenece(destino)) {
                    visitados.insertar(destino); //se visito destino
                    cola.encolar(destino); //agregamos la ciudad a la cola para usar en el proximo nivel
                    nodosEnNivelSiguiente++;
                }
                //avanzamos en la lista de conexiones
                conexiones = conexiones.getSig();
            }
            //si no hay ciudades en ese nivel (ya se visitaron)
            if (nodosEnNivel == 0) {
                niveles++; // avanzamos de nivel
                nodosEnNivel = nodosEnNivelSiguiente; //actualizamos los nodos de ese nivel
                nodosEnNivelSiguiente = 0;
            }
        }
        return resultado;
    }

    public Costo dijkstraViajeCostoMinimoMinutos(Ciudad origen, Ciudad destino, TipoVueloPermitido tipoPermitido) {
        int n = cantidadCiudades;
        double[] dist = new double[n]; //tiempo min desde el origen a cada ciudad
        int[] anterior = new int[n]; //guarda el indice del nodo anterior al destino en el camino ptimo hacia cada ciudad
        boolean[] visitado = new boolean[n]; //marca si ya se proceso esa ciudad


        for (int i = 0; i < n; i++) {
            dist[i] = Double.MAX_VALUE; //tiempo infinito
            anterior[i] = -1; //no hay camino previo aun
            visitado[i] = false; //ninguna ciudad fue visitada aun
        }

        int posOrigen = obtenerPos(origen); //se obtiene la posicion del origen
        dist[posOrigen] = 0; // la distancia desde el origen a si misma es 0

        //controla si aun hay ciudades por procesar
        boolean hayNodoAlcanzable = true;

        //itera mientras hayan nodos alcanzables
        while (hayNodoAlcanzable) {
            int u = -1; //guarda el nodo con menor distancia aun no procesadp
            double min = Double.MAX_VALUE;
            //busca el nodo no visitado con la menor distancia estimada
            for (int j = 0; j < n; j++) {
                if (!visitado[j] && dist[j] < min) {
                    min = dist[j];
                    u = j;
                }
            }

            //si no se encontro ningun nodo alcanzable, termina
            if (u == -1) {
                hayNodoAlcanzable = false;
            }
            else {
                visitado[u] = true; //marcamos la ciudad como visitada
                //obtenemos la ciudad
                Ciudad actual = ciudades[u];
                //recorremos sus conexiones
                Nodo<Conexion> nodoCon = actual.getConexiones().getCabeza();

                while (nodoCon != null) {
                    Conexion conexion = nodoCon.getDato();
                    Ciudad vecino = conexion.getDestino();
                    int v = obtenerPos(vecino);

                    double mejorTiempo = Double.MAX_VALUE;
                    //recorremos los vuelos
                    Nodo<Vuelo> vuelos = conexion.getVuelos().getCabeza();
                    while (vuelos != null) {
                        Vuelo vuelo = vuelos.getDato();
                        //filtramos por tipo
                        if (tipoPermitido == TipoVueloPermitido.AMBOS || vuelo.getTipo().getTexto().equals(tipoPermitido.getTexto())) {
                            //tiempo del vuelo
                            double tiempo = vuelo.getMinutos();
                            if (tiempo < mejorTiempo) {
                                mejorTiempo = tiempo;
                            }
                        }
                        vuelos = vuelos.getSig();
                    }

                    //si se encontro al menos un vuelo permitido y mejora el tiempo actual
                    if (mejorTiempo < Double.MAX_VALUE && dist[u] + mejorTiempo < dist[v]) {
                        dist[v] = dist[u] + mejorTiempo; // se actualiza la mejor distancia
                        anterior[v] = u; //se guarda el nodo anterior en el camino
                    }

                    nodoCon = nodoCon.getSig();
                }
            }
        }

        //evaluamos si el destino fue alcanzado (si su distancia sigue en infinito, no hay camino)
        int posDestino = obtenerPos(destino);
        if (dist[posDestino] == Double.MAX_VALUE){
            return null;
        }

        //se reconstruye el camino
        Lista<Ciudad> camino = construirCamino(posDestino, anterior);
        return new Costo(dist[posDestino], camino);
    }


    public Costo dijkstraViajeCostoMinimoDolares(Ciudad origen, Ciudad destino, TipoVueloPermitido tipoPermitido) {
        int n = cantidadCiudades;
        double[] dist = new double[n];
        int[] anterior = new int[n];
        boolean[] visitado = new boolean[n];

        for (int i = 0; i < n; i++) {
            dist[i] = Double.MAX_VALUE;
            anterior[i] = -1;
            visitado[i] = false;
        }

        int posOrigen = obtenerPos(origen);
        dist[posOrigen] = 0;

        boolean hayNodoAlcanzable = true;
        while (hayNodoAlcanzable) {
            int u = -1;
            double min = Double.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visitado[j] && dist[j] < min) {
                    min = dist[j];
                    u = j;
                }
            }

            if (u == -1) {
                hayNodoAlcanzable = false;
            } else {
                visitado[u] = true;

                Ciudad actual = ciudades[u];
                Nodo<Conexion> nodoCon = actual.getConexiones().getCabeza();

                while (nodoCon != null) {
                    Conexion conexion = nodoCon.getDato();
                    Ciudad vecino = conexion.getDestino();
                    int v = obtenerPos(vecino);

                    double mejorCosto = Double.MAX_VALUE;
                    Nodo<Vuelo> vuelos = conexion.getVuelos().getCabeza();
                    while (vuelos != null) {
                        Vuelo vuelo = vuelos.getDato();
                        if (tipoPermitido == TipoVueloPermitido.AMBOS || vuelo.getTipo().getTexto().equals(tipoPermitido.getTexto())) {
                            double costo = vuelo.getCostoEnDolares();
                            if (costo < mejorCosto) {
                                mejorCosto = costo;
                            }
                        }
                        vuelos = vuelos.getSig();
                    }

                    if (mejorCosto < Double.MAX_VALUE && dist[u] + mejorCosto < dist[v]) {
                        dist[v] = dist[u] + mejorCosto;
                        anterior[v] = u;
                    }

                    nodoCon = nodoCon.getSig();
                }
            }
        }

        int posDestino = obtenerPos(destino);
        if (dist[posDestino] == Double.MAX_VALUE) return null;

        Lista<Ciudad> camino = construirCamino(posDestino, anterior);
        return new Costo(dist[posDestino], camino);
    }


    private int obtenerPos(Ciudad ciudad) {
        for (int i = 0; i < cantidadCiudades; i++) {
            if (ciudades[i].equals(ciudad)) {
                return i;
            }
        }
        return -1;
    }

    private Lista<Ciudad> construirCamino(int posDestino, int[] anterior) {
        Lista<Ciudad> camino = new Lista<>();
        int actual = posDestino;

        while (actual != -1) {
            camino.insertarAlInicio(ciudades[actual]);
            actual = anterior[actual];
        }
        return camino;
    }


}

