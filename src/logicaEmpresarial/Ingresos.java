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
}