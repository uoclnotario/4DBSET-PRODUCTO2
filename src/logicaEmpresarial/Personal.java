package logicaEmpresarial;

import java.util.Date;

public  class Personal{

    private Date fechaAlta;
    private Date fechaBaja;
    private boolean estado;
    private Identificacion identificacion;
    private Delegacion delegacion;

    //falta añadir delegacion.
    public Personal(){
        identificacion = new Identificacion();
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


    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }

    public Delegacion getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(Delegacion delegacion) {
        this.delegacion = delegacion;
    }


}