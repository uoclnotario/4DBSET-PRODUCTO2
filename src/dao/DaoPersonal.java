package dao;

import logicaEmpresarial.Identificacion;
import logicaEmpresarial.Personal;

import java.util.ArrayList;
import java.util.List;

public class DaoPersonal implements Dao{


    public List<Personal> RecogerLIstado()
    {
        List<Personal> retorno = new ArrayList<>();

        //(Date fechaAlta, Date fechaBaja, boolean estado, Identificacion identificacion)
        retorno.add(new Personal(null,
                                   null,
                                    false,
                                    new Identificacion("12345", "nombre1", null,"domicilio1", Identificacion.Tipo.PERSONA)));

        retorno.add(new Personal(null,
                null,
                false,
                new Identificacion("12345", "nombre2", null,"domicilio2", Identificacion.Tipo.PERSONA)));
        return  retorno;
    }

    @Override
    public boolean Crear(Object item) {
        Personal nuevoItem = (Personal)item;
        System.out.println("Se crea el personal:"+nuevoItem.getGetIdentificacion().getNombre());
        return true;
    }

}
