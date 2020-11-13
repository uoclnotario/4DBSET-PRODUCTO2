package logicaEmpresarial;

import java.util.Date;

public  class Ingresos  {

    private int indice;
    private String cifOnif;
    private String nombre;
    private String domicilo;
    private Float importe;
    private Date fecha;

    public String getNombre() {
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public Float getImporte() {
        return importe;
    }

    public int getIndice() {
        return indice;
    }

    public String getCifOnif() {
        return cifOnif;
    }

    public String getDomicilo() {
        return domicilo;
    }

    public void setCifOnif(String cifOnif) {
        this.cifOnif = cifOnif;
    }

    public void setNombreIngreso(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setDomicilioIngreso(String domicilio) { this.domicilo = domicilio;}

    public void setImporte(Float importe) { this.importe = importe;}


}