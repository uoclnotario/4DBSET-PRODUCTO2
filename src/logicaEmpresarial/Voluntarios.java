package logicaEmpresarial;

import java.util.Date;

public class Voluntarios extends Personal {
    private String areaVoluntariado;

    public Voluntarios() {
        super();
    }

    @Override
    public Date getFechaAlta() {
        return super.getFechaAlta();
    }

    @Override
    public Date getFechaBaja() {
        return super.getFechaBaja();
    }


    public String getAreaVoluntariado() {
        return areaVoluntariado;
    }
}