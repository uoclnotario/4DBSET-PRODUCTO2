package logicaEmpresarial;

import java.util.ArrayList;
import java.util.List;

public  class Ong {
    private String nombre;
    private String DireccionSede;
    private String Telefono;
    private String usuarioAutentificado;


    private List<Ingresos> ingresos;
    private List<Socios> socios;
    private List<Personal> personal;
    private List<Usuario> usuarios;
    private List<Proyecto> proyectos;
    private List<Delegacion> delegaciones;

    public Ong(){
        personal = new ArrayList<>();
        proyectos = new ArrayList<>();
        delegaciones = new ArrayList<>();
        ingresos = new ArrayList<>();
        socios = new ArrayList<>();
        usuarios = new ArrayList<>();
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