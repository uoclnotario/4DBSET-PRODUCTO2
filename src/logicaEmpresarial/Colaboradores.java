package logicaEmpresarial;

public  class Colaboradores extends Personal {
    public Colaboradores() {
        super();
    }

    public enum TiposColaboracion{ECONOMICA,SOCIAL,MANODEOBRA};
    private TiposColaboracion tipoColaboracion;

    public String getTipoString(){
        if(tipoColaboracion == null)
            return "No seleccionado";

        switch (tipoColaboracion){
            case ECONOMICA: return "ECONOMICA";
            case SOCIAL:    return "SOCIAL";
            case MANODEOBRA:return "MANO DE OBRA";
            default:        return "No Seleccionado";
        }
    }


    public TiposColaboracion getTipoColaboracion() {
        return tipoColaboracion;
    }

    public void setIntTipoColaboracion(int tipoColaboracion) {

        switch (tipoColaboracion){
            case 1: this.tipoColaboracion = TiposColaboracion.ECONOMICA;break;
            case 2: this.tipoColaboracion = TiposColaboracion.MANODEOBRA;break;
            case 3: this.tipoColaboracion = TiposColaboracion.SOCIAL;break;
        }

    }
    public  void setTipoColaboracion(TiposColaboracion tipo){
        this.tipoColaboracion = tipo;
    }




}
