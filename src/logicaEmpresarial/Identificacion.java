package logicaEmpresarial;

import java.util.Date;

public class Identificacion {
    public  enum  Tipo{PERSONA,EMPRESA};
    private String nif_dni;
    private String nombre;
    private Date fechaDeNacimiento;
    private String domicilio;
    private Tipo tipo;

    public Identificacion(){}

    public Identificacion(String nif_dni, String nombre, Date fechaDeNacimiento, String domicilio, Tipo tipo){
        this.nif_dni = nif_dni;
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.domicilio = domicilio;
        this.tipo = tipo;
    }


    public String getNombre() {
        return nombre;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getNif_dni() {
        return nif_dni;
    }

    public Tipo getTipo() {
        return tipo;
    }
}