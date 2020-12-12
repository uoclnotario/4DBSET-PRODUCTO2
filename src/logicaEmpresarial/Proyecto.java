package logicaEmpresarial;

import java.util.Date;
import javax.swing.plaf.BorderUIResource;

public  class Proyecto extends Personal {


    public void setDelegacion(Delegacion delegacion) {
    }

    public  enum  Tipo{NACIONAL,INTERNACIONAL};

    private int id;
    private Date fechaAlta;
    private Date fechaBaja;
    private String nombre;
    private Date fechaDeInicio;
    private boolean estado;

    public Proyecto(){ }

    public Proyecto(Date fechaAlta, Date fechaBaja,String nombre, Date fechaDeInicio, String delegacion, Tipo tipo, Boolean estado){
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.nombre = nombre;
        this.fechaDeInicio = fechaDeInicio;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }
    public String getTipoString(){

        if(this.getClass().getName() == "logicaEmpresarial.Nacional")
            return "Nacional";
        else
            return "Internacional";
    }
    public Date getFechaDeInicio() {
        return fechaDeInicio;
    }
    public Date getFechaAlta() {
        return fechaAlta;
    }
    public Date getFechaBaja() {
        return fechaBaja;
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
    public void setFechaDeInicio(Date fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }
    public void setEstado(boolean estado) { this.estado = estado; }

}