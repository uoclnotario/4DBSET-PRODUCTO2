package logicaEmpresarial;

import java.util.ArrayList;
import java.util.List;

public class Delegacion {

    private String nombre;
    private String direccion;
    private String telefono;
    private List<Proyecto> proyecto;
    private List<Personal> personal;


    public Delegacion(){
        personal = new ArrayList<Personal>();
    }

    public Delegacion(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        personal = new ArrayList<Personal>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Personal> getPersonal() {
        if(personal == null)personal = new ArrayList<Personal>();
        return personal;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPersonal(List<logicaEmpresarial.Personal> personal) {
        personal = personal;
    }

    public void setProyecto(List<logicaEmpresarial.Proyecto> proyecto) {
        proyecto = proyecto;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Proyecto> getProyecto() {
        return proyecto;
    }
}