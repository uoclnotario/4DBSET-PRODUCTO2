package dao;

import logicaEmpresarial.Identificacion;
import logicaEmpresarial.Ong;
import logicaEmpresarial.Personal;

import java.util.ArrayList;
import java.util.List;

public class DaoPersonal implements Dao{
    private Ong pilaDatosGenerales;

    public DaoPersonal(Ong pilaDatos) {
        pilaDatosGenerales = pilaDatos;
    }

    @Override
    public List<Personal> RecogerLIstado()
    {
        return  pilaDatosGenerales.getPersonal();
    }

    @Override
    public boolean Crear(Object item) {
        if(item == null)return false;
        Personal nuevoItem = (Personal)item;
        pilaDatosGenerales.getPersonal().add(nuevoItem);
        return true;
    }

    @Override
    public boolean DescargaDatos() {
        //Dao Personal llama a descarga tambien a delegaciones.




        if(pilaDatosGenerales.getPersonal().size()< 1) {
            //Esto es para depurar
            pilaDatosGenerales.getPersonal().add(new Personal(null,
                    null,
                    false,
                    new Identificacion("12345", "nombre1", null, "domicilio1", Identificacion.Tipo.PERSONA)));

            pilaDatosGenerales.getPersonal().add(new Personal(null,
                    null,
                    false,
                    new Identificacion("12345", "nombre2", null, "domicilio2", Identificacion.Tipo.PERSONA)));
        }
        return true;
    }

    @Override
    public boolean Modificar(Object item, int indice) {
        if(item == null)return false;
        pilaDatosGenerales.getPersonal().set(indice,(Personal)item);
        return true;
    }

}
