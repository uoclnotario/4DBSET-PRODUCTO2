package logicaEmpresarial;

public class Publico extends Ingresos {
    public  enum TipoPublico{ESTATAL,AUTONOMICA,LOCAL,UE};
    private TipoPublico Tipo;

    public TipoPublico getTipo() {
        return Tipo;
    }
}