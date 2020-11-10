package logicaEmpresarial;

import java.sql.Struct;
import java.util.Date;

public  class Personal extends Persona {

    private Date fechaAlta;
    private Date fechaBaja;
    private boolean estado;
    private Delegacion delegacion;

    //falta añadir delegacion.
    public Personal(){
    }

    public Personal(Date fechaAlta, Date fechaBaja, boolean estado,String nif_dni, String nombre, Date fechaDeNacimiento, String domicilio, Tipo tipo) {
        super( nif_dni,  nombre,  fechaDeNacimiento,  domicilio,  tipo);
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.estado = estado;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }
    public Date getFechaBaja() {
        return fechaBaja;
    }
    public boolean getEstado(){return estado;}


    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }


    public Delegacion getDelegacion() {
        return delegacion;
    }
    public void setDelegacion(Delegacion delegacion) {
        this.delegacion = delegacion;
    }
    public String getTipoString(){
        switch(this.getClass().getName()){
            case "logicaEmpresarial.Colaboradores":return "Colaborador";
            case "logicaEmpresarial.Voluntarios":return "Voluntario";
            case "logicaEmpresarial.VoluntariosInternacionales":return "Voluntario internacional";
            case "logicaEmpresarial.Contratados":return "Contratado";
            default: return "No definido";
        }


    }


}