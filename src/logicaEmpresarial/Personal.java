package logicaEmpresarial;

import java.sql.Struct;
import java.util.Date;

public  class Personal extends Persona {
    private int id;
    private Date fechaAlta;
    private Date fechaBaja;
    private boolean estado;
    private Delegacion delegacion;
    private Proyecto proyecto;

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

    public int getId() {
        return id;
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


    public Delegacion getDelegacion() {
        return delegacion;
    }
    public Proyecto getProyecto(){return proyecto;}

    public void setDelegacion(Delegacion nuevaDelegacion) {

        //Si la delegación que se pretende añadir es la misma que la que tiene asignada el personal no se ejecuta.
       //if(nuevaDelegacion.equals(delegacion)) return;

        //Elimina de la lista de personal de la delegación que va a ser modificada a este personal.
        if(delegacion != null)
            this.delegacion.getPersonal().remove((Personal) this);

        //Si la nueva delegación no es null, se añade el personal a la lista.
        if(nuevaDelegacion != null){
            nuevaDelegacion.getPersonal().add((Personal)this);
        }

        this.delegacion = nuevaDelegacion;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
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