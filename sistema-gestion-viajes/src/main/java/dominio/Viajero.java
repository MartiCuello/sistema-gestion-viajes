package dominio;

import interfaz.Categoria;

public class Viajero {
    private String cedula;
    private String nombre;
    private String correo;
    private int edad;
    private Categoria categoria;

    public Viajero(String cedula, String nombre, String correo, int edad, Categoria categoria) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.categoria = categoria;
    }
    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public int getEdad() {
        return edad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public boolean cedulaValida() {
        //formato de cedula N.NNN.NNN-N o NN.NNN.NNN-N
        return cedula.matches("^(\\d{1,2}\\.\\d{3}\\.\\d{3}-\\d|\\d{3}\\.\\d{3}-\\d)$");
    }

    public boolean correoValido() {
        //formato de correo aa@aa.aa
        return correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}
