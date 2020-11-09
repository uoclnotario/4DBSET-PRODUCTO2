package logicaEmpresarial;

import javax.swing.plaf.BorderUIResource;
import java.util.Date;

public class Persona {
    public  enum  Tipo{FISICA,JURIDICA};
    private String nif_dni;
    private String nombre;
    private Date fechaDeNacimiento;
    private String domicilio;
    private Tipo tipo;

    public Persona(){

    }

    public Persona(String nif_dni, String nombre, Date fechaDeNacimiento, String domicilio, Tipo tipo){
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


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setNif_dni(String nif_dni) {
        this.nif_dni = nif_dni;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}