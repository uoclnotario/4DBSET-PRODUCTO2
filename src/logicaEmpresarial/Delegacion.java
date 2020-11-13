package logicaEmpresarial;

import java.util.List;

public class Delegacion {

    private String nombre;
    private String direccion;
    private String telefono;
    private List<Personal> Personal;
    private List<Proyecto> Proyecto;


    public Delegacion(){}

    public Delegacion(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPersonal(List<logicaEmpresarial.Personal> personal) {
        Personal = personal;
    }

    public void setProyecto(List<logicaEmpresarial.Proyecto> proyecto) {
        Proyecto = proyecto;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Personal> getPersonal() {
        return Personal;
    }

    public List<Proyecto> getProyecto() {
        return Proyecto;
    }
}