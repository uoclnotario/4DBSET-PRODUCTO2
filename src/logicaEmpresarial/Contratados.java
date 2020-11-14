package logicaEmpresarial;

public  class Contratados extends Personal {
    private String tipoContrato;
    private Float salario;

    public String getTipoContrato() {
        return tipoContrato;
    }

    public Float getSalario() {
        return salario;
    }


    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }

    public Contratados() {
        super();
    }
}