package logicaEmpresarial;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.concurrent.CopyOnWriteArraySet;

public class Usuario {


    public  enum tipoUsuarios{USUARIO,ADMINISTRADOR};
    private String nombre;
    private String hasing;
    private tipoUsuarios rol;
    private int id;

    public Usuario() {

    }
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
    public void setPassword(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update((nombre+password).getBytes("utf8"));
            hasing = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public void setRol(tipoUsuarios rol) { this.rol = rol; }
    public void setHasing(String hasing) { this.hasing = hasing;}
    public void setId(int id) { this.id = id; }

    public String getHashing() {
        if(hasing == null)
            return "";
        else
            return hasing;
    }
    public tipoUsuarios getRol() {return rol;}

    public String getRolString(){
        switch (rol){
            case ADMINISTRADOR:return "ADMINISTRADOR";
            default:return "USUARIO";
        }
    }
    public int getId() {return id;}

    public void setIntRol(int e){
        switch (e){
            case 1: this.rol = tipoUsuarios.USUARIO;break;
            case 2: this.rol = tipoUsuarios.ADMINISTRADOR;break;
        }
    }

    public int getIntRol(){
        switch (rol){
            case ADMINISTRADOR: return 2;
            default: return 1;
        }
    }

}