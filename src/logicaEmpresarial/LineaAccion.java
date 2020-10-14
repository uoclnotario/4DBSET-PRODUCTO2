package logicaEmpresarial;


import java.util.List;

public  class LineaAccion {
    private String nombre;
    private String descripcion;
    private List<LineaAccion> subLineaAccion;

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<LineaAccion> getSubLineaAccion() {
        return subLineaAccion;
    }
}