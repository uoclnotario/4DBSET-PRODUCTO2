package logicaEmpresarial;

import java.util.ArrayList;
import java.util.List;

public  class Ong {
    private String nombre;
    private String DireccionSede;
    private String Telefono;
    private String usuarioAutentificado;

    public List<Ingresos> ingresos;
    public List<Socios> socios;
    public List<Personal> personal;
    public List<Usuario> usuarios;
    public List<Proyecto> proyectos;
    public List<Delegacion> delegaciones;

    public Ong(){
        personal = new ArrayList<Personal>();
        delegaciones = new ArrayList<Delegacion>();
    }


    private String getNombre() {
        return nombre;
    }

    private String getDireccionSede() {
        return DireccionSede;
    }

    private String getTelefono() {
        return Telefono;
    }

    private String getUsuarioAutentificado() {
        return usuarioAutentificado;
    }

    public List<Ingresos> getIngresos() {
        return ingresos;
    }

    public List<Socios> getSocios() {
        return socios;
    }

    public List<Personal> getPersonal() {
        return personal;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public List<Delegacion> getDelegaciones() {
        return delegaciones;
    }


}