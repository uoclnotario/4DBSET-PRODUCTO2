package logicaEmpresarial;

import java.util.List;

public class Delegacion {

    private String nombre;
    private String direccion;
    private String telefono;
    private List<Personal> Personal;

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
        return Personal;
    }
}