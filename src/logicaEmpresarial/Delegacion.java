package logicaEmpresarial;

import java.util.ArrayList;
import java.util.List;

public class Delegacion {

    private String nombre;
    private String direccion;
    private String telefono;
    private List<Personal> listaPersonal;


    public Delegacion(){
        listaPersonal = new ArrayList<Personal>();
    }

    public Delegacion(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        listaPersonal = new ArrayList<Personal>();
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
        if(listaPersonal == null)listaPersonal = new ArrayList<Personal>();
        return listaPersonal;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPersonal(List<logicaEmpresarial.Personal> personal) {
        listaPersonal = personal;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }




}