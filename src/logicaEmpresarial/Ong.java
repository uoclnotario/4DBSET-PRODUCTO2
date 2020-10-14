package logicaEmpresarial;

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


    public String getNombre() {
        return nombre;
    }

    public String getDireccionSede() {
        return DireccionSede;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getUsuarioAutentificado() {
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