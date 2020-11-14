package logicaEmpresarial;

public  class Colaboradores extends Personal {
    public Colaboradores() {
        super();
    }

    public enum tipos{ECONOMICA,SOCIAL,MANODEOBRA};
    private tipos tipoColaboracion;

    public String getTipoString(){
        switch (tipoColaboracion){
            case ECONOMICA: return "ECONOMICA";
            case SOCIAL:    return "SOCIAL";
            case MANODEOBRA:return "MANODEOBRA";
            default:        return "No Seleccionado";
        }
    }

}
