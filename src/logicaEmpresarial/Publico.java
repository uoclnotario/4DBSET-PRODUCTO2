package logicaEmpresarial;

public class Publico extends Ingresos {
    public  enum TipoPublico{ESTATAL,AUTONOMICA,LOCAL,UE};
    private TipoPublico tipoIngreso;

    public TipoPublico get() {
        return tipoIngreso;
    }
}