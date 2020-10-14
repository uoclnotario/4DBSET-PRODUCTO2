package logicaEmpresarial;


public class Usuario {
    public  enum tipoUsuarios{USUARIO,ADMINISTRADOR};

    private tipoUsuarios Rol;
    private String nombre;
    private String hasing;


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String nombre){
        //Hace el hassing.
    }

    public String getHasing() {
        return hasing;
    }
    public void setHasing(String hasing) {
        this.hasing = hasing;
    }

    public tipoUsuarios getRol() {
        return Rol;
    }



}