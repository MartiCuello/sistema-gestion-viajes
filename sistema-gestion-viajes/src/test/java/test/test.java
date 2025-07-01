package test;

import interfaz.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sistema.ImplementacionSistema;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {
    private Retorno retorno;
    private final Sistema s = new ImplementacionSistema();


    //01 INICIALIZAR SISTEMA
    @Test
    void inicializarSistemaOk() {
        //maxCiudades mayor a 4
        retorno = s.inicializarSistema(12);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.inicializarSistema(5);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.inicializarSistema(30);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());

        retorno = s.inicializarSistema(100);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void inicializarSistemaError1() {
        //maxCiudades menor igual a 4
        retorno = s.inicializarSistema(4);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.inicializarSistema(3);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.inicializarSistema(0);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.inicializarSistema(-1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @BeforeEach
    public void setUp() {
        s.inicializarSistema(5);
    }

    //02 REGISTRAR VIAJERO
    @Test
    void registrarViajeroOK(){
        retorno = s.registrarViajero("4.645.891-9" ,"Mirta", "mirta@gmail.com", 32, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void registrarViajeroError1(){
        //alguno de los parametros vacios o nulos
        retorno = s.registrarViajero(" " ,"pepe", "pp@gmail.com", 27, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarViajero(null ,"", "pp@gmail.com", 27, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarViajero("3.562.875-2" ,"  ", "pp@gmail.com", 27, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarViajero("3.562.875-2" ,"", "pepe@gmail.com", 10, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarViajero("1.627.588-4" ,"Juan", "Juan@gmail.com", 44, null );
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void registrarViajeroError2(){
        //formato de cedula invalida
        retorno = s.registrarViajero("3562.8752" ,"Pepe", "pepe@gmail.com", 10, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarViajero("1.6.27.5884" ,"Juan", "Juan@gmail.com", 44, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void registrarViajeroError3(){
        //formato de correo invalido
        retorno = s.registrarViajero("3.562.875-2" ,"Pepe", "pepe@gmailcom", 10, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());

        retorno = s.registrarViajero("1.627.588-4" ,"Juan", "Juangmail.com", 44, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    @Test
    void registrarViajeroError4(){
        //la edad no esta en el rango 0 - 139
        retorno = s.registrarViajero("3.562.875-2" ,"Pepe", "pepe@gmail.com", -1, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());

        retorno = s.registrarViajero("1.627.588-4" ,"Juan", "Juan@gmail.com", 150, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }


    @Test
    void registrarViajeroError5(){
        //si ya existe un viajero con esa cedula
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.ESTANDAR );

        retorno = s.registrarViajero("5.326.083-9" ,"Maria", "mm@gmail.com", 22, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_5, retorno.getResultado());
    }

    @Test
    void registrarViajeroError6(){
        //si ya existe un viajero con ese correo
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.ESTANDAR );

        retorno = s.registrarViajero("5.336.183-9" ,"Maria", "mc@gmail.com", 22, Categoria.ESTANDAR );
        assertEquals(Retorno.Resultado.ERROR_6, retorno.getResultado());
    }


    //03 BUSCAR VIAJERO POR CEDULA
    @Test
    void buscarViajeroPorCedulaOK(){
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.ESTANDAR);
        //ver error en las otras pruebas
        retorno = s.buscarViajeroPorCedula("5.326.083-9");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(1, retorno.getValorInteger());
        assertEquals("5.326.083-9;Martina;mc@gmail.com;22;Estándar", retorno.getValorString());
    }

    @Test
    void buscarViajeroPorCedulaError1(){
        //si la cedula es vacia o null
        retorno = s.buscarViajeroPorCedula(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.buscarViajeroPorCedula(" ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void buscarViajeroPorCedulaError2(){
        //formato de cedula invalida
        retorno = s.buscarViajeroPorCedula("5.326.083");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.buscarViajeroPorCedula("544.33");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void buscarViajeroPorCedulaError3(){
        //no existe un viajero con esa cedula
        retorno = s.buscarViajeroPorCedula("5.326.083-6");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());

        retorno = s.buscarViajeroPorCedula("5.333.083-9");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }


    //04 BUSCAR VIAJERO POR CORREO
    @Test
    void buscarViajeroPorCorreoOK(){
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.ESTANDAR );

        retorno = s.buscarViajeroPorCorreo("mc@gmail.com");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(1, retorno.getValorInteger());
        assertEquals("5.326.083-9;Martina;mc@gmail.com;22;Estándar", retorno.getValorString());
    }

    @Test
    void buscarViajeroPorCorreoError1(){
        //si el correo es vacio o null
        retorno = s.buscarViajeroPorCorreo(null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.buscarViajeroPorCorreo(" ");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void buscarViajeroPorCorreoError2(){
        //formato de correo invalido
        retorno = s.buscarViajeroPorCorreo("mc@gmail");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.buscarViajeroPorCorreo("mcgmail.com");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.buscarViajeroPorCorreo("mcgmailcom");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void buscarViajeroPorCorreoError3(){
        //no existe un viajero con ese correo
        retorno = s.buscarViajeroPorCorreo("mc@gmail.com");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    //05 LISTAR VIAJEROS POR CEDULA ASCENDENTE
    @Test
    void listarViajerosPorCedulaAscendente() {
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.ESTANDAR );
        s.registrarViajero("4.999.185-3" ,"Lucas", "lucas@gmail.com", 25, Categoria.FRECUENTE );
        s.registrarViajero("4.370.443-6" ,"Micaela", "mica@gmail.com", 31, Categoria.FRECUENTE );
        s.registrarViajero("1.708.390-4" ,"Franco", "franco@gmail.com", 21, Categoria.PLATINO );
        s.registrarViajero("608.390-4" ,"Pepe", "pepe@gmail.com", 21, Categoria.PLATINO );

        String martina = "5.326.083-9;Martina;mc@gmail.com;22;Estándar";
        String lucas = "4.999.185-3;Lucas;lucas@gmail.com;25;Frecuente";
        String micaela = "4.370.443-6;Micaela;mica@gmail.com;31;Frecuente";
        String franco = "1.708.390-4;Franco;franco@gmail.com;21;Platino";
        String pepe = "608.390-4;Pepe;pepe@gmail.com;21;Platino";

        String salidaEsperada = pepe + "|" + franco + "|" + micaela + "|" + lucas + "|" + martina;
        retorno = s.listarViajerosPorCedulaAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(salidaEsperada, retorno.getValorString());

    }

    @Test
    void listarViajerosPorCedulaAscendenteVacio() {
        retorno = s.listarViajerosPorCedulaAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    // 06 LISTAR VIAJEROS POR CEDULA DESCENDENTE
    @Test
    void listarViajerosPorCedulaDescendente() {
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.ESTANDAR );
        s.registrarViajero("4.999.185-3" ,"Lucas", "lucas@gmail.com", 25, Categoria.FRECUENTE );
        s.registrarViajero("4.370.443-6" ,"Micaela", "mica@gmail.com", 31, Categoria.FRECUENTE );
        s.registrarViajero("1.708.390-4" ,"Franco", "franco@gmail.com", 21, Categoria.PLATINO );

        String martina = "5.326.083-9;Martina;mc@gmail.com;22;Estándar";
        String lucas = "4.999.185-3;Lucas;lucas@gmail.com;25;Frecuente";
        String micaela = "4.370.443-6;Micaela;mica@gmail.com;31;Frecuente";
        String franco = "1.708.390-4;Franco;franco@gmail.com;21;Platino";

        String salidaEsperada = martina + "|" + lucas + "|" + micaela + "|" + franco;
        retorno = s.listarViajerosPorCedulaDescendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(salidaEsperada, retorno.getValorString());
    }
    @Test
    void listarViajerosPorCedulaDescendenteVacio() {
        retorno = s.listarViajerosPorCedulaDescendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    // 07 LISTAR VIAJEROS POR CORREO ASCENDENTE
    @Test
    void listarViajerosPorCorreoAscendente() {
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.ESTANDAR );
        s.registrarViajero("4.999.185-3" ,"Lucas", "lucas@gmail.com", 25, Categoria.FRECUENTE );
        s.registrarViajero("4.370.443-6" ,"Micaela", "mica@gmail.com", 31, Categoria.FRECUENTE );
        s.registrarViajero("1.708.390-4" ,"Franco", "franco@gmail.com", 21, Categoria.PLATINO );

        String martina = "5.326.083-9;Martina;mc@gmail.com;22;Estándar";
        String lucas = "4.999.185-3;Lucas;lucas@gmail.com;25;Frecuente";
        String micaela = "4.370.443-6;Micaela;mica@gmail.com;31;Frecuente";
        String franco = "1.708.390-4;Franco;franco@gmail.com;21;Platino";

        String salidaEsperada = franco + "|" + lucas + "|" + martina + "|" + micaela;
        retorno = s.listarViajerosPorCorreoAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(salidaEsperada, retorno.getValorString());
    }

    @Test
    void listarViajerosPorCorreoAscendenteVacio() {
        retorno = s.listarViajerosPorCorreoAscendente();
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    //08 LISTAR VIAJEROS POR CATEGORIA
    @Test
    void listarViajerosPorCategoriaEstandar(){
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.ESTANDAR );
        s.registrarViajero("4.999.185-3" ,"Lucas", "lucas@gmail.com", 25, Categoria.ESTANDAR );
        s.registrarViajero("4.370.443-6" ,"Micaela", "mica@gmail.com", 31, Categoria.ESTANDAR );
        s.registrarViajero("1.708.390-4" ,"Franco", "franco@gmail.com", 21, Categoria.PLATINO );

        String martina = "5.326.083-9;Martina;mc@gmail.com;22;Estándar";
        String lucas = "4.999.185-3;Lucas;lucas@gmail.com;25;Estándar";
        String micaela = "4.370.443-6;Micaela;mica@gmail.com;31;Estándar";
        String franco = "1.708.390-4;Franco;franco@gmail.com;21;Platino";

        String salidaEsperada = micaela + "|" + lucas + "|" + martina;
        retorno = s.listarViajerosPorCategoria(Categoria.ESTANDAR);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(salidaEsperada, retorno.getValorString());
    }

    @Test
    void listarViajerosPorCategoriaFrecuente(){
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.ESTANDAR );
        s.registrarViajero("4.999.185-3" ,"Lucas", "lucas@gmail.com", 25, Categoria.FRECUENTE );
        s.registrarViajero("4.370.443-6" ,"Micaela", "mica@gmail.com", 31, Categoria.FRECUENTE );
        s.registrarViajero("1.708.390-4" ,"Franco", "franco@gmail.com", 21, Categoria.FRECUENTE );

        String martina = "5.326.083-9;Martina;mc@gmail.com;22;Estándar";
        String lucas = "4.999.185-3;Lucas;lucas@gmail.com;25;Frecuente";
        String micaela = "4.370.443-6;Micaela;mica@gmail.com;31;Frecuente";
        String franco = "1.708.390-4;Franco;franco@gmail.com;21;Frecuente";

        String salidaEsperada = franco + "|" + micaela + "|" + lucas;
        retorno = s.listarViajerosPorCategoria(Categoria.FRECUENTE);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(salidaEsperada, retorno.getValorString());
    }

    @Test
    void listarViajerosPorCategoriaPlatino(){
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.PLATINO );
        s.registrarViajero("4.999.185-3" ,"Lucas", "lucas@gmail.com", 25, Categoria.FRECUENTE );
        s.registrarViajero("4.370.443-6" ,"Micaela", "mica@gmail.com", 31, Categoria.PLATINO );
        s.registrarViajero("1.708.390-4" ,"Franco", "franco@gmail.com", 21, Categoria.PLATINO );

        String martina = "5.326.083-9;Martina;mc@gmail.com;22;Platino";
        String lucas = "4.999.185-3;Lucas;lucas@gmail.com;25;Frecuente";
        String micaela = "4.370.443-6;Micaela;mica@gmail.com;31;Platino";
        String franco = "1.708.390-4;Franco;franco@gmail.com;21;Platino";

        String salidaEsperada = franco + "|" + micaela + "|" + martina;
        retorno = s.listarViajerosPorCategoria(Categoria.PLATINO);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(salidaEsperada, retorno.getValorString());
    }

    @Test
    void listarViajerosPorCategoriaVacio(){
        retorno = s.listarViajerosPorCategoria(Categoria.ESTANDAR);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("", retorno.getValorString());
    }

    // 09 LISTAR VIAJEROS POR RANGO DE EDAD
    @Test
    void listarViajerosPorRango2(){
        s.registrarViajero("5.326.083-9" ,"Martina", "mc@gmail.com", 22, Categoria.PLATINO );
        s.registrarViajero("4.999.185-3" ,"Lucas", "lucas@gmail.com", 25, Categoria.FRECUENTE );
        s.registrarViajero("4.370.443-6" ,"Micaela", "mica@gmail.com", 31, Categoria.PLATINO );
        s.registrarViajero("1.708.390-4" ,"Franco", "franco@gmail.com", 21, Categoria.PLATINO );

        String martina = "5.326.083-9;Martina;mc@gmail.com;22;Platino";
        String lucas = "4.999.185-3;Lucas;lucas@gmail.com;25;Frecuente";
        String micaela = "4.370.443-6;Micaela;mica@gmail.com;31;Platino";
        String franco = "1.708.390-4;Franco;franco@gmail.com;21;Platino";

        String salidaEsperadaRango2 = franco + "|" + lucas + "|" + martina;

        retorno = s.listarViajerosDeUnRangoAscendente(2);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(salidaEsperadaRango2, retorno.getValorString());
    }

    @Test
    void listarViajerosPorRangoVacio(){
        for (int i = 0; i < 14; i++) {
            retorno = s.listarViajerosDeUnRangoAscendente(i);
            assertEquals(Retorno.Resultado.OK, retorno.getResultado());
            assertEquals("", retorno.getValorString());
        }
    }

    @Test
    void listarViajerosPorRangoError1(){
        //el rango es menor a 0
        retorno = s.listarViajerosDeUnRangoAscendente(-1);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void listarViajerosPorRangoError2(){
        //el rango es mayor a 13
        retorno = s.listarViajerosDeUnRangoAscendente(50);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    // 10 REGISTRAR CIUDAD
    @Test
    void registrarCiudadOK(){
        retorno = s.registrarCiudad("C1" ,"Ciudad1");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }
    @Test
    void registrarCiudadError1(){
        //en el sistema ya hay registradas max ciudades
        s.registrarCiudad("C1" ,"Ciudad1");
        s.registrarCiudad("C2" ,"Ciudad2");
        s.registrarCiudad("C3" ,"Ciudad3");
        s.registrarCiudad("C4" ,"Ciudad4");
        s.registrarCiudad("C5" ,"Ciudad5");

        retorno = s.registrarCiudad("C6" ,"Ciudad6");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }

    @Test
    void registrarCiudadError2(){
        //codigo o nombre vacios o nulos
        retorno = s.registrarCiudad("" ,"Ciudad1");;
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCiudad("C1" ," ");;
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCiudad("C1" ,null);;
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarCiudad(null ,"Ciudad1");;
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }

    @Test
    void registrarCiudadError3(){
        //ya existe una ciudad con ese codigo
        s.registrarCiudad("C1" ,"Ciudad1");

        retorno = s.registrarCiudad("C1" ,"Ciudad1");;
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    //11 REGISTRAR CONEXION
    @Test
    void registrarConexionOK(){
        s.registrarCiudad("C1", "Ciudad1");
        s.registrarCiudad("C2", "Ciudad2");

        retorno = s.registrarConexion("C1", "C2");
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }

    @Test
    void registrarConexionError1(){
        //algun parametro vacio o nulo
        retorno = s.registrarConexion(" ", "C2");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarConexion("C1", "");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarConexion("C1", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarConexion(null, "C2");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }
    @Test
    void registrarConexionError2(){
        //la ciudad de origen no existe
        s.registrarCiudad("C2", "Ciudad2");

        retorno = s.registrarConexion("C1", "C2");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
    @Test
    void registrarConexionError3(){
        //la ciudad de destino no existe
        s.registrarCiudad("C1", "Ciudad1");

        retorno = s.registrarConexion("C1", "C2");
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
    @Test
    void registrarConexionError4(){
        //ya existe una conexion entre el origen y el destino
        s.registrarCiudad("C1", "Ciudad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");

        retorno = s.registrarConexion("C1", "C2");
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    //12 REGISTRAR VUELO
    @Test
    void registrarVueloOK(){
        s.registrarCiudad("C1", "Ciduad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");

        retorno = s.registrarVuelo("C1", "C2","V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }
    @Test
    void registrarVueloError1(){
        //si algun parametro double es menor o igual a 0
        s.registrarCiudad("C1", "Ciduad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");

        retorno = s.registrarVuelo("C1", "C2","V1", 0, 6000, 800, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarVuelo("C1", "C2","V1", 1000, -10, 800, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.registrarVuelo("C1", "C2","V1", 0, 6000, -1, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

    }
    @Test
    void registrarVueloError2(){
        //si algun parametro string es vacio o nulo
        s.registrarCiudad("C1", "Ciduad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");

        retorno = s.registrarVuelo(" ", "C2","V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarVuelo("C1", null,"V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.registrarVuelo("C1", "C2","  ", 1000, 6000, 800, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

    }
    @Test
    void registrarVueloError3(){
        //la ciudad de origen no existe
        s.registrarCiudad("C2", "Ciudad2");

        retorno = s.registrarVuelo("C1", "C2","V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
    @Test
    void registrarVueloError4(){
        //la ciudad de destino no existe
        s.registrarCiudad("C1", "Ciudad1");

        retorno = s.registrarVuelo("C1", "C2","V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());

    }
    @Test
    void registrarVueloError5(){
        //no existe una conexion entre origen y destino
        s.registrarCiudad("C1", "Ciudad1");
        s.registrarCiudad("C2", "Ciudad2");

        retorno = s.registrarVuelo("C1", "C2","V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);
        assertEquals(Retorno.Resultado.ERROR_5, retorno.getResultado());

    }
    @Test
    void registrarVueloError6(){
        //ya existe un vuelo con ese codigo en esa conexion
        s.registrarCiudad("C1", "Ciduad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");
        s.registrarVuelo("C1", "C2","V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);

        retorno =  s.registrarVuelo("C1", "C2","V1", 4000, 80000, 1800, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_6, retorno.getResultado());


    }

    //13 ACTUALIZAR VUELO
    @Test
    void actualizarVueloOK(){
        s.registrarCiudad("C1", "Ciduad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");
        s.registrarVuelo("C1", "C2","V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);

        retorno = s.actualizarVuelo("C1", "C2", "V1", 2000, 500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
    }
    @Test
    void actualizarVueloError1(){
        //alguno de los parametros double es menor o igual a 0
        s.registrarCiudad("C1", "Ciduad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");
        s.registrarVuelo("C1", "C2","V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);

        retorno = s.actualizarVuelo("C1", "C2", "V1", 0, 500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.actualizarVuelo("C1", "C2", "V1", 6000, -500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.actualizarVuelo("C1", "C2", "V1", 6000, 500, -1, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

    }
    @Test
    void actualizarVueloError2(){
        //alguno de los parametros string es vacio o nulo
        s.registrarCiudad("C1", "Ciduad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");
        s.registrarVuelo("C1", "C2","V1", 1000, 6000, 800, TipoVuelo.COMERCIAL);

        retorno = s.actualizarVuelo(" ", "C2", "V1", 2000, 500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
        retorno = s.actualizarVuelo("C1", null, "V1", 2000, 500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
        retorno = s.actualizarVuelo("C1", "C2", "", 2000, 500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

    }
    @Test
    void actualizarVueloError3(){
        //la ciudad de origen no existe
        s.registrarCiudad("C2", "Ciudad2");

        retorno = s.actualizarVuelo("C1", "C2", "V1", 2000, 500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());

    }
    @Test
    void actualizarVueloError4(){
        //la ciudad de destino no existe
        s.registrarCiudad("C1", "Ciudad1");

        retorno = s.actualizarVuelo("C1", "C2", "V1", 2000, 500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());

    }
    @Test
    void actualizarVueloError5(){
        //no existe una conexion entre origen y destino
        s.registrarCiudad("C1", "Ciduad1");
        s.registrarCiudad("C2", "Ciudad2");

        retorno = s.actualizarVuelo("C1", "C2", "V1", 2000, 500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_5, retorno.getResultado());

    }
    @Test
    void actualizarVueloError6(){
        //no existe un codigo con ese vuelo en esa conexion
        s.registrarCiudad("C1", "Ciduad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");

        retorno = s.actualizarVuelo("C1", "C2", "V1", 2000, 500, 200, TipoVuelo.PRIVADO);
        assertEquals(Retorno.Resultado.ERROR_6, retorno.getResultado());

    }

    //14 CIUDADES POR CANTIDAD DE ESCALAS
    @Test
    void ciudadesPorCantidadEscalasOK(){
        s.registrarCiudad("C1", "Ciudad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarCiudad("C3", "Ciudad3");

        s.registrarConexion("C1", "C2");
        s.registrarConexion("C2", "C3");
        s.registrarVuelo("C1","C2","V1", 500, 30, 150, TipoVuelo.COMERCIAL);
        s.registrarVuelo("C2","C3","V1", 500, 30, 150, TipoVuelo.COMERCIAL);

        retorno = s.listadoCiudadesCantDeEscalas("C1", 2);
        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals("C1;Ciudad1|C2;Ciudad2|C3;Ciudad3", retorno.getValorString());
    }
    @Test
    void ciudadesPorCantidadEscalasError1(){
        //la cantidad es menor que 0
        retorno = s.listadoCiudadesCantDeEscalas("C1", -10);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }
    @Test
    void ciudadesPorCantidadEscalasError2(){
        //el codigo es vacio o nulo
        retorno = s.listadoCiudadesCantDeEscalas(" ", 10);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

        retorno = s.listadoCiudadesCantDeEscalas(null, 10);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
    @Test
    void ciudadesPorCantidadEscalasError3(){
        //la ciudad no esta registrada en el sistema
        retorno = s.listadoCiudadesCantDeEscalas("C1", 10);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }

    //15 VIAJE DE COSTO MINIMO EN MINUTOS
    @Test
    void viajeCostoMinimoMinutosOk(){
        s.registrarCiudad("C1", "Ciudad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarCiudad("C3", "Ciudad3");

        s.registrarConexion("C1", "C2");
        s.registrarConexion("C2", "C3");

        s.registrarVuelo("C1", "C2", "V1", 100, 30, 250, TipoVuelo.COMERCIAL);
        s.registrarVuelo("C2", "C3", "V2", 150, 20, 200, TipoVuelo.COMERCIAL);

        retorno = s.viajeCostoMinimoMinutos("C1", "C3", TipoVueloPermitido.AMBOS);

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(50, retorno.getValorInteger());
        assertEquals("C1;Ciudad1|C2;Ciudad2|C3;Ciudad3", retorno.getValorString());
    }
    @Test
    void viajeCostoMinimoMinutosError1(){
        //alguno de los parametros es vacio o nulo
        retorno = s.viajeCostoMinimoMinutos(" ", "C2", TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.viajeCostoMinimoMinutos("C1", null, TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.viajeCostoMinimoMinutos("C1", "C2", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }
    @Test
    void viajeCostoMinimoMinutosError2(){
        //no existe la ciudad de origen
        s.registrarCiudad("C2", "Ciudad2");

        retorno = s.viajeCostoMinimoMinutos("C1", "C2", TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());

    }
    @Test
    void viajeCostoMinimoMinutosError3(){
        //no existe la ciudad de destino
        s.registrarCiudad("C1", "Ciudad1");

        retorno = s.viajeCostoMinimoMinutos("C1", "C2", TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());

    }
    @Test
    void viajeCostoMinimoMinutosError4(){
        //no hay camino enre el origen y el destino
        s.registrarCiudad("C1", "Ciudad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1","C2");

        retorno = s.viajeCostoMinimoMinutos("C1", "C2", TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }

    //16 VIAJE COSTO MINIMO EN DOLARES
    @Test
    void viajeCostoMinimoDolaresOK(){
        s.registrarCiudad("C1", "Ciudad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarCiudad("C3", "Ciudad3");

        s.registrarConexion("C1", "C2");
        s.registrarConexion("C2", "C3");

        s.registrarVuelo("C1", "C2", "V1", 100, 30, 250, TipoVuelo.COMERCIAL);
        s.registrarVuelo("C2", "C3", "V2", 150, 20, 200, TipoVuelo.COMERCIAL);

        retorno = s.viajeCostoMinimoDolares("C1", "C3", TipoVueloPermitido.AMBOS);

        assertEquals(Retorno.Resultado.OK, retorno.getResultado());
        assertEquals(450, retorno.getValorInteger());
        assertEquals("C1;Ciudad1|C2;Ciudad2|C3;Ciudad3", retorno.getValorString());
    }
    @Test
    void viajeCostoMinimoDolaresError1(){
        //alguno de los parametros es nulo o vacio
        retorno = s.viajeCostoMinimoDolares(" ", "C2", TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.viajeCostoMinimoDolares("C1", null, TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());

        retorno = s.viajeCostoMinimoDolares("C1", "C2", null);
        assertEquals(Retorno.Resultado.ERROR_1, retorno.getResultado());
    }
    @Test
    void viajeCostoMinimoDolaresError2(){
        //no existe la ciudad de origen
        s.registrarCiudad("C2", "Ciudad2");

        retorno = s.viajeCostoMinimoDolares("C1", "C2", TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_2, retorno.getResultado());
    }
    @Test
    void viajeCostoMinimoDolaresError3(){
        //no existe la ciudad de destino
        s.registrarCiudad("C1", "Ciudad1");

        retorno = s.viajeCostoMinimoDolares("C1", "C2", TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_3, retorno.getResultado());
    }
    @Test
    void viajeCostoMinimoDolaresError4(){
        //no hay camino entre el origen y el destino
        s.registrarCiudad("C1", "Ciudad1");
        s.registrarCiudad("C2", "Ciudad2");
        s.registrarConexion("C1", "C2");

        retorno = s.viajeCostoMinimoDolares("C1", "C2", TipoVueloPermitido.AMBOS);
        assertEquals(Retorno.Resultado.ERROR_4, retorno.getResultado());
    }















}

