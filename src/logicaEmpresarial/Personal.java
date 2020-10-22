package logicaEmpresarial;

import java.util.Date;

public  class Personal{

    private Date fechaAlta;
    private Date fechaBaja;
    private boolean estado;
    private Identificacion identificacion;


    //falta añadir delegacion.
    public Personal(){

    }

    public Personal(Date fechaAlta, Date fechaBaja, boolean estado, Identificacion identificacion) {
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.estado = estado;
        this.identificacion = identificacion;
    }

    public Identificacion getGetIdentificacion() {
        return identificacion;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }
}