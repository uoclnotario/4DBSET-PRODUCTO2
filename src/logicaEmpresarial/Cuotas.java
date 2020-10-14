package logicaEmpresarial;


import java.util.Date;

public class Cuotas {
    public enum tipoMensualidad{MENSUAL,TRIMESTRAL,ANUAL};
    private tipoMensualidad tipo;
    private Date fechaInicio;
    private Float importe;
}