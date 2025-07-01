package dominio;

import interfaz.TipoVuelo;

public class Vuelo {
    private String codigo;
    private double combustible;
    private double minutos;
    private double costoEnDolares;
    private TipoVuelo tipo;

    public Vuelo(String codigo, double combustible, double minutos, double costoEnDolares, TipoVuelo tipo) {
        this.codigo = codigo;
        this.combustible = combustible;
        this.minutos = minutos;
        this.costoEnDolares = costoEnDolares;
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getCombustible() {
        return combustible;
    }

    public double getMinutos() {
        return minutos;
    }

    public double getCostoEnDolares() {
        return costoEnDolares;
    }

    public TipoVuelo getTipo() {
        return tipo;
    }

    public void setCombustible(double combustible) {
        this.combustible = combustible;
    }

    public void setMinutos(double minutos) {
        this.minutos = minutos;
    }

    public void setCostoEnDolares(double costoEnDolares) {
        this.costoEnDolares = costoEnDolares;
    }

    public void setTipo(TipoVuelo tipo){
        this.tipo = tipo;
    }
}
