package logicaEmpresarial;

import java.util.Date;

public  class Ingresos  extends  Persona {

    private int id;

    private Float importe;
    private Date fecha;


    public Date getFecha() {
        return fecha;
    }
    public Float getImporte() {
        return importe;
    }
    public int getId() {
        return id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setImporte(Float importe) { this.importe = importe;}
    public void setId(int id) {
        this.id = id;
    }

}