package logicaEmpresarial;


import java.util.Date;
import java.util.List;

public class Socios extends Persona {
    private int id;




    private List<Cuotas> cuotas;

    public Socios(){

    }

    public Socios(String nif_dni, String nombre, Date fechaDeNacimiento, String domicilio, Tipo tipo){
        super( nif_dni,  nombre,  fechaDeNacimiento,  domicilio,  tipo);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public Date getFechaDeNacimiento() {
        return super.getFechaDeNacimiento();
    }

    @Override
    public String getDomicilio() {
        return super.getDomicilio();
    }

    @Override
    public Tipo getTipo() {
        return super.getTipo();
    }

    @Override
    public String getNif_dni() {
        return super.getNif_dni();
    }
}
