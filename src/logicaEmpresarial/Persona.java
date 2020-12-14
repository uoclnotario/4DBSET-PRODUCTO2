package logicaEmpresarial;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.plaf.BorderUIResource;
import java.util.Date;

public abstract class  Persona {
    public  enum  Tipo{FISICA,JURIDICA};
    private String nif_dni;
    private String nombre;
    private Date fechaDeNacimiento;
    private String domicilio;
    private Tipo tipo;
    private Integer id;


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
        if(tipo == null)
            return Tipo.FISICA;

        return tipo;
    }

    public Integer getIntTipo(){
        if(tipo == null)return 0;
        switch (tipo){
            case FISICA:return  0;
            default: return 1;
        }
    }

    public Integer getPersonaId(){
        return id;
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
    public void setIntTipo(int tipo){
        if(tipo == 0){
            this.tipo = Tipo.FISICA;
        }else{
            this.tipo = Tipo.JURIDICA;
        }
    }

    public void setPersonaId(Integer id){
        this.id= id;
    }


}