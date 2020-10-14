package logicaEmpresarial;

public  class Privado extends Ingresos {
    public enum tipoPrivados{PARTICULAR,EMPRESA,HERENCIA,LEGADO,INSTITUCION,EXTAORDINARIO,SOCIO};
    private tipoPrivados tipo;

    public Boolean IsSocio(){
        return true;
    }

}
