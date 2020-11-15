package com.app.console.TestingTTD;

import com.app.console.Apartados;
import dao.DaoXML;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Persona;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Proyecto;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class PersonalTest  {

    @Test
    public void testCrear() throws Exception{
        Personal personal = new Personal();
        Delegacion delegacion = new Delegacion();

        DaoXML dao = new DaoXML("testTTDPersonal.xml");

        //Cración de personal y delegación.
        personal.setNombre("SAD");
        delegacion.setNombre("SAD");

        personal.setDelegacion(delegacion);
        delegacion.getPersonal().add(personal);

        //Se crea en el xml los datos
        dao.crear(delegacion,Apartados.DELEGACIONES);
        dao.crear(personal, Apartados.PERSONAL);


        //Se recogen los datos de vuelta.

        //Verificamos que delegación esta bien
        Assert.assertThat(dao.getPilaDatosGenerales().getDelegaciones().get(0).getNombre(), CoreMatchers.is("SAD"));

        //verficamos que personal esta bien.
        Assert.assertThat(dao.getPilaDatosGenerales().getPersonal().get(0).getNombre(), CoreMatchers.is("SAD"));


        //verificamos que delegación de personal es la misma que delegación de indice 0


        //Eliminamos la delegacion de personal y volvemos a realizar la comprobación
    }

}
