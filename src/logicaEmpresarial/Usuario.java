package logicaEmpresarial;


public class Usuario {
    public  enum tipoUsuarios{USUARIO,ADMINISTRADOR};
    private String nombre;
    private String hasing;
    private tipoUsuarios rol;


    public Usuario(String nombre,String pass, tipoUsuarios tipo){
        this.nombre = nombre;
        this.rol = tipo;
        this.setPassword(pass);
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPassword(String nombre){ }



    public String getHasing() {
        return hasing;
    }


    public tipoUsuarios getRol() {
        return rol;
    }



}