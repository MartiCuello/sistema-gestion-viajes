package sistema;

import dominio.*;
import interfaz.*;
import tads.ABB.ABB;
import tads.Grafo.Grafo;
import tads.Lista.Lista;
import tads.Lista.Nodo;

//MARTINA CUELLO 318177

public class ImplementacionSistema implements Sistema {
    private int maxCiudades;
    private Grafo grafo;

    private ABB<Viajero> ViajerosPorCedula;
    private ABB<Viajero> ViajerosPorCorreo;

    private ABB<Viajero> viajerosEstandar;
    private ABB<Viajero> viajerosFrecuente;
    private ABB<Viajero> viajerosPlatino;

    private ABB<Viajero>[] viajerosPorEdad = new ABB[14];

    @Override
    public Retorno inicializarSistema(int maxCiudades) {
        if (maxCiudades <= 4) {
            return Retorno.error1("El maximo de ciudades debe ser menor a 4");
        }
        this.maxCiudades = maxCiudades;
        this.grafo = new Grafo(maxCiudades);

        ViajerosPorCedula = new ABB<>(new ComparadorPorCedula());
        ViajerosPorCorreo = new ABB<>(new ComparadorPorCorreo());

        viajerosEstandar = new ABB<>(new ComparadorPorCedula());
        viajerosFrecuente = new ABB<>(new ComparadorPorCedula());
        viajerosPlatino = new ABB<>(new ComparadorPorCedula());

        for (int i = 0; i <= 13; i++) {
            viajerosPorEdad[i] = new ABB<>(new ComparadorPorCedula());
        }

        return Retorno.ok();
    }

    @Override
    public Retorno registrarViajero(String cedula, String nombre, String correo, int edad, Categoria categoria) {
        Viajero v = new Viajero(cedula, nombre, correo, edad, categoria);
        if (cedula == null || nombre == null || correo == null || categoria == null ||
                cedula.trim().isEmpty() || nombre.trim().isEmpty() || correo.trim().isEmpty()) {
            return Retorno.error1("Los campos no pueden ser vacios");
        }
        if (!v.cedulaValida()) {
            return Retorno.error2("Formato de cedula invalido");
        }
        if (!v.correoValido()) {
            return Retorno.error3("Formato de correo invalido");
        }
        if (edad < 0 || edad > 139) {
            return Retorno.error4("La edad debe estar entre 0 y 139");
        }

        if (ViajerosPorCedula.pertenece(v)) {
            return Retorno.error5("Ya existe un viajero con esa cedula");
        }
        if (ViajerosPorCorreo.pertenece(v)) {
            return Retorno.error6("Ya existe un viajero con ese correo");
        }
        ViajerosPorCorreo.insertar(v);
        ViajerosPorCedula.insertar(v);

        switch (categoria) {
            case ESTANDAR:
                viajerosEstandar.insertar(v);
                break;
            case FRECUENTE:
                viajerosFrecuente.insertar(v);
                break;
            case PLATINO:
                viajerosPlatino.insertar(v);
                break;
        }

        int rango = edad / 10;
        if (rango >= 0 && rango <= 13) {
            viajerosPorEdad[rango].insertar(v);
        }

        return Retorno.ok();
    }

    @Override
    public Retorno buscarViajeroPorCedula(String cedula) {
        //dummy obj para buscar
        Viajero v = new Viajero(cedula, null, null, -1, null);
        if (cedula == null || cedula.trim().isEmpty()) {
            return Retorno.error1("La cedula no puede ser vacia");
        }
        if (!v.cedulaValida()) {
            return Retorno.error2("Formato de cedula invalido");
        }
        Viajero encontrado = ViajerosPorCedula.buscar(v);
        if (encontrado == null) {
            return Retorno.error3("No hay ningun viajero con esa cedula");
        }
        int nodosRecorridos = ViajerosPorCedula.getNodosRecorridos();
        String valorString = encontrado.getCedula() + ";" + encontrado.getNombre() + ";" +
                encontrado.getCorreo() + ";" + encontrado.getEdad() + ";" + encontrado.getCategoria().getTexto();
        return Retorno.ok(nodosRecorridos, valorString);
    }

    @Override
    public Retorno buscarViajeroPorCorreo(String correo) {
        Viajero v = new Viajero(null, correo, correo, -1, null);
        if (correo == null || correo.trim().isEmpty()) {
            return Retorno.error1("El correo no puede ser vacia");
        }
        if (!v.correoValido()) {
            return Retorno.error2("Formato de correo invalido");
        }
        Viajero encontrado = ViajerosPorCorreo.buscar(v);
        if (encontrado == null) {
            return Retorno.error3("No hay ningun viajero con ese correo");
        }
        int nodosRecorridos = ViajerosPorCorreo.getNodosRecorridos();
        String valorString = encontrado.getCedula() + ";" + encontrado.getNombre() + ";" + encontrado.getCorreo() +
                ";" + encontrado.getEdad() + ";" + encontrado.getCategoria().getTexto();
        return Retorno.ok(nodosRecorridos, valorString);
    }

    @Override
    public Retorno listarViajerosPorCedulaAscendente() {
        Lista<Viajero> lista = ViajerosPorCedula.listarAscendente();
        if (lista.esVacia()) {
            return Retorno.ok("");
        }
        String valorString = "";
        Nodo<Viajero> actual = lista.getCabeza();

        while (actual != null) {
            Viajero v = actual.getDato();
            valorString += v.getCedula() + ";" + v.getNombre() + ";" + v.getCorreo() + ";" +
                    v.getEdad() + ";" + v.getCategoria().getTexto();

            if (actual.getSig() != null) {
                valorString += "|";
            }
            actual = actual.getSig();
        }

        return Retorno.ok(valorString);
    }

    @Override
    public Retorno listarViajerosPorCedulaDescendente() {
        Lista<Viajero> lista = ViajerosPorCedula.listarDescendente();
        if (lista.esVacia()) {
            return Retorno.ok("");
        }
        String valorString = "";
        Nodo<Viajero> actual = lista.getCabeza();

        while (actual != null) {
            Viajero v = actual.getDato();
            valorString += v.getCedula() + ";" + v.getNombre() + ";" + v.getCorreo() + ";" +
                    v.getEdad() + ";" + v.getCategoria().getTexto();

            if (actual.getSig() != null) {
                valorString += "|";
            }
            actual = actual.getSig();
        }

        return Retorno.ok(valorString);
    }

    @Override
    public Retorno listarViajerosPorCorreoAscendente() {
        Lista<Viajero> lista = ViajerosPorCorreo.listarAscendente();
        if (lista.esVacia()) {
            return Retorno.ok("");
        }
        String valorString = "";
        Nodo<Viajero> actual = lista.getCabeza();

        while (actual != null) {
            Viajero v = actual.getDato();
            valorString += v.getCedula() + ";" + v.getNombre() + ";" + v.getCorreo() + ";" +
                    v.getEdad() + ";" + v.getCategoria().getTexto();

            if (actual.getSig() != null) {
                valorString += "|";
            }
            actual = actual.getSig();
        }

        return Retorno.ok(valorString);
    }


    @Override
    public Retorno listarViajerosPorCategoria(Categoria unaCategoria) {
        Lista<Viajero> lista = new Lista<>();
        if (unaCategoria == Categoria.ESTANDAR) {
            lista = viajerosEstandar.listarAscendente();
        } else if (unaCategoria == Categoria.FRECUENTE) {
            lista = viajerosFrecuente.listarAscendente();
        } else if (unaCategoria == Categoria.PLATINO) {
            lista = viajerosPlatino.listarAscendente();
        }

        if (lista.esVacia()) {
            return Retorno.ok("");
        }
        String valorString = "";
        Nodo<Viajero> actual = lista.getCabeza();

        while (actual != null) {
            Viajero v = actual.getDato();
            valorString += v.getCedula() + ";" + v.getNombre() + ";" + v.getCorreo() + ";" +
                    v.getEdad() + ";" + v.getCategoria().getTexto();

            if (actual.getSig() != null) {
                valorString += "|";
            }
            actual = actual.getSig();
        }
        return Retorno.ok(valorString);
    }

    @Override
    public Retorno listarViajerosDeUnRangoAscendente(int rango) {
        if (rango < 0) {
            return Retorno.error1("Rango menor a 0");
        }
        if (rango > 13) {
            return Retorno.error2("Rango mayor a 13");
        }

        ABB<Viajero> arbol = viajerosPorEdad[rango];
        Lista<Viajero> lista = arbol.listarAscendente();

        if (lista.esVacia()) {
            return Retorno.ok("");
        }
        String valorString = "";
        Nodo<Viajero> actual = lista.getCabeza();

        while (actual != null) {
            Viajero v = actual.getDato();
            valorString += v.getCedula() + ";" + v.getNombre() + ";" + v.getCorreo() + ";" +
                    v.getEdad() + ";" + v.getCategoria().getTexto();

            if (actual.getSig() != null) {
                valorString += "|";
            }
            actual = actual.getSig();
        }
        return Retorno.ok(valorString);
    }

    @Override
    public Retorno registrarCiudad(String codigo, String nombre) {
        if (codigo == null || nombre == null || codigo.trim().isEmpty() || nombre.trim().isEmpty()) {
            return Retorno.error2("No puede haber parametros vacios o nulos");
        }
        Ciudad c = grafo.buscarCiudad(codigo);
        if (c != null) {
            return Retorno.error3("Ya existe una ciudad con ese codigo");
        }
        Ciudad nueva = new Ciudad(codigo, nombre);
        boolean exito = grafo.agregarCiudad(nueva);
        if (!exito) {
            return Retorno.error1("Se llego al tope de ciudades registradas");
        }
        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoCiudadOrigen, String codigoCiudadDestino) {
        if (codigoCiudadOrigen == null || codigoCiudadDestino == null ||
                codigoCiudadOrigen.trim().isEmpty() || codigoCiudadDestino.trim().isEmpty()) {
            return Retorno.error1("No puede haber parametros vacíos o nulos");
        }
        Ciudad o = grafo.buscarCiudad(codigoCiudadOrigen);
        Ciudad d = grafo.buscarCiudad(codigoCiudadDestino);
        if (o == null) {
            return Retorno.error2("La ciudad de origen no existe");
        }
        if (d == null) {
            return Retorno.error3("La ciudad de destino no existe");
        }
        if (grafo.existeConexion(o, d)) {
            return Retorno.error4("Ya existe una conexion entre el origen y el destino");
        }
        boolean exito = grafo.agregarConexion(o, d);
        if (!exito) {
            return Retorno.error4("No se pudo registrar la conexión");
        }
        return Retorno.ok();
    }

    @Override
    public Retorno registrarVuelo(String codigoCiudadOrigen, String codigoCiudadDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, TipoVuelo tipoDeVuelo) {
        if (combustible <= 0 || minutos <= 0 || costoEnDolares <= 0) {
            return Retorno.error1("Parametros numericos invalidos");
        }
        if (codigoCiudadOrigen == null || codigoCiudadDestino == null || tipoDeVuelo == null || codigoDeVuelo == null || codigoDeVuelo.trim().isEmpty() ||
                codigoCiudadDestino.trim().isEmpty() || codigoCiudadOrigen.trim().isEmpty() || tipoDeVuelo.getTexto().trim().isEmpty()) {
            return Retorno.error2("No puede haber paramentros vacios o nulos");
        }
        Ciudad o = grafo.buscarCiudad(codigoCiudadOrigen);
        Ciudad d = grafo.buscarCiudad(codigoCiudadDestino);
        if (o == null) {
            return Retorno.error3("La ciduad de origen no existe");
        }
        if (d == null) {
            return Retorno.error4("La ciudad de destino no existe");
        }
        Conexion conexion = grafo.buscarConexion(o, d);
        if (conexion == null) {
            return Retorno.error5("No existe una conexion entre ese origen y destino");
        }
        if (conexion.tieneVueloConCodigo(codigoDeVuelo)) {
            return Retorno.error6("Ya existe un vuelo con ese codigo en esa conexion");
        }

        Vuelo v = new Vuelo(codigoDeVuelo, combustible, minutos, costoEnDolares, tipoDeVuelo);
        conexion.getVuelos().insertar(v);
        return Retorno.ok();
    }

    @Override
    public Retorno actualizarVuelo(String codigoCiudadOrigen, String codigoCiudadDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, TipoVuelo tipoDeVuelo) {
        if (combustible <= 0 || minutos <= 0 || costoEnDolares <= 0) {
            return Retorno.error1("Parametros numericos invalidos");
        }
        if (codigoCiudadOrigen == null || codigoCiudadDestino == null || tipoDeVuelo == null || codigoDeVuelo == null || codigoDeVuelo.trim().isEmpty() ||
                codigoCiudadDestino.trim().isEmpty() || codigoCiudadOrigen.trim().isEmpty() || tipoDeVuelo.getTexto().trim().isEmpty()) {
            return Retorno.error2("No puede haber paramentros vacios o nulos");
        }
        Ciudad o = grafo.buscarCiudad(codigoCiudadOrigen);
        Ciudad d = grafo.buscarCiudad(codigoCiudadDestino);
        if (o == null) {
            return Retorno.error3("La ciduad de origen no existe");
        }
        if (d == null) {
            return Retorno.error4("La ciudad de destino no existe");
        }
        Conexion conexion = grafo.buscarConexion(o, d);
        if (conexion == null) {
            return Retorno.error5("No existe una conexion entre ese origen y destino");
        }
        if (!conexion.tieneVueloConCodigo(codigoDeVuelo)) {
            return Retorno.error6("No existe un vuelo con ese codigo en esa conexion");
        }

        Vuelo v = conexion.buscarVueloPorCodigo(codigoDeVuelo);

        v.setCombustible(combustible);
        v.setMinutos(minutos);
        v.setCostoEnDolares(costoEnDolares);
        v.setTipo(tipoDeVuelo);

        return Retorno.ok();
    }

    @Override
    public Retorno listadoCiudadesCantDeEscalas(String codigoCiudadOrigen, int cantidad) {
        if (cantidad < 0) {
            return Retorno.error1("La cantidad de escalas no puede ser menor a 0");
        }
        if (codigoCiudadOrigen == null || codigoCiudadOrigen.trim().isEmpty()) {
            return Retorno.error2("No puede haber parametros vacios o nulos");
        }
        Ciudad c = grafo.buscarCiudad(codigoCiudadOrigen);
        if (c == null) {
            return Retorno.error3("La ciudad no existe");
        }

        Lista<Ciudad> lista = grafo.bfsHastaEscalas(c, cantidad);
        if (lista.esVacia()) {
            return Retorno.ok("");
        }
        String valorString = "";
        Nodo<Ciudad> actual = lista.getCabeza();

        while (actual != null) {
            Ciudad ciudad = actual.getDato();
            valorString += ciudad.getCodigo() + ";" + ciudad.getNombre();

            if (actual.getSig() != null) {
                valorString += "|";
            }
            actual = actual.getSig();
        }
        return Retorno.ok(valorString);
    }

    @Override
    public Retorno viajeCostoMinimoMinutos(String codigoCiudadOrigen, String codigoCiudadDestino, TipoVueloPermitido tipoVueloPermitido) {
        if (codigoCiudadOrigen == null || codigoCiudadDestino == null || tipoVueloPermitido == null ||
                codigoCiudadDestino.trim().isEmpty() || codigoCiudadOrigen.trim().isEmpty() || tipoVueloPermitido.getTexto().trim().isEmpty()) {
            return Retorno.error1("Ningun parametro puede ser vacio o nulo");
        }
        Ciudad o = grafo.buscarCiudad(codigoCiudadOrigen);
        Ciudad d = grafo.buscarCiudad(codigoCiudadDestino);
        if (o == null) {
            return Retorno.error2("La ciudad de origen no existe");
        }
        if (d == null) {
            return Retorno.error3("La ciudad de destino no existe");
        }

        Costo costoMinimo = grafo.dijkstraViajeCostoMinimoMinutos(o, d, tipoVueloPermitido);

        if (costoMinimo == null) {
            return Retorno.error4("No hay camino entre el origen y el destino");
        }

        String valorString = "";
        Lista<Ciudad> camino = costoMinimo.getCamino();
        Nodo<Ciudad> actual = camino.getCabeza();

        while (actual != null) {
            Ciudad ciudad = actual.getDato();
            valorString += ciudad.getCodigo() + ";" + ciudad.getNombre();

            if (actual.getSig() != null) {
                valorString += "|";
            }
            actual = actual.getSig();
        }

        int valorInt = (int) Math.round(costoMinimo.getValor());
        return Retorno.ok(valorInt, valorString);
    }

    @Override
    public Retorno viajeCostoMinimoDolares(String codigoCiudadOrigen, String codigoCiudadDestino, TipoVueloPermitido tipoVueloPermitido) {
        if (codigoCiudadOrigen == null || codigoCiudadDestino == null || tipoVueloPermitido == null ||
                codigoCiudadDestino.trim().isEmpty() || codigoCiudadOrigen.trim().isEmpty() || tipoVueloPermitido.getTexto().trim().isEmpty()) {
            return Retorno.error1("Ningun parametro puede ser vacio o nulo");
        }
        Ciudad o = grafo.buscarCiudad(codigoCiudadOrigen);
        Ciudad d = grafo.buscarCiudad(codigoCiudadDestino);
        if (o == null) {
            return Retorno.error2("La ciudad de origen no existe");
        }
        if (d == null) {
            return Retorno.error3("La ciudad de destino no existe");
        }
        Costo costoMinimo = grafo.dijkstraViajeCostoMinimoDolares(o, d, tipoVueloPermitido);

        if (costoMinimo == null) {
            return Retorno.error4("No hay camino entre el origen y el destino");
        }

        String valorString = "";
        Lista<Ciudad> camino = costoMinimo.getCamino();
        Nodo<Ciudad> actual = camino.getCabeza();

        while (actual != null) {
            Ciudad ciudad = actual.getDato();
            valorString += ciudad.getCodigo() + ";" + ciudad.getNombre();

            if (actual.getSig() != null) {
                valorString += "|";
            }
            actual = actual.getSig();
        }

        int valorInt = (int) Math.round(costoMinimo.getValor());
        return Retorno.ok(valorInt, valorString);
    }
}





