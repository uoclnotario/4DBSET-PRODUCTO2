package logicaEmpresarial;

import java.util.Date;
import javax.swing.plaf.BorderUIResource;

public  class Proyecto {


    public  enum  Tipo{NACIONAL,INTERNACIONAL};

    private Date fechaAlta;
    private Date fechaBaja;

    private String id;
    private String nombre;
    private Date fechaDeInicio;
    private String delegacion;
    private Tipo tipo;
    private boolean estado;

    public Proyecto(){

    }

    public Proyecto(Date fechaAlta, Date fechaBaja,String id, String nombre, Date fechaDeInicio, String delegacion, Tipo tipo, Boolean estado){
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.id = id;
        this.nombre = nombre;
        this.fechaDeInicio = fechaDeInicio;
        this.delegacion = delegacion;
        this.tipo = tipo;
        this.estado = estado;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaDeInicio() {
        return fechaDeInicio;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public boolean getEstado() { return estado;   }



    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDelegacion(Delegacion delegacion) {
        this.delegacion = delegacion;
    }

    public void setFechaDeInicio(Date fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setEstado(boolean estado) { this.estado = estado; }


}