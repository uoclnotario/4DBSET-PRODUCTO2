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
        Delegacion delegacion2 = new Delegacion();

        DaoXML dao = new DaoXML("testTTDPersonal.xml");

        //Cración de personal y delegación.
        personal.setNombre("per1");
        delegacion.setNombre("del1");
        delegacion2.setNombre("del2");

        personal.setDelegacion(delegacion);
        delegacion.getPersonal().add(personal);

        //Se crea en el xml los datos
        dao.crear(delegacion,Apartados.DELEGACIONES);
        dao.crear(delegacion2,Apartados.DELEGACIONES);
        dao.crear(personal, Apartados.PERSONAL);

        //volvemos a cargar el dao
        dao.descargaDatos(Apartados.PERSONAL);
        //Se recogen los datos de vuelta.

        //Verificamos que delegación esta bien
        Assert.assertThat(dao.getPilaDatosGenerales().getDelegaciones().get(0).getNombre(), CoreMatchers.is("del1"));

        //verficamos que personal esta bien.
        Assert.assertThat(dao.getPilaDatosGenerales().getPersonal().get(0).getNombre(), CoreMatchers.is("per1"));

        personal.setDelegacion(delegacion2);

        dao.modificar(personal,0,Apartados.PERSONAL);

        //verificamos que personal de delegacion sea igual que personal
         Assert.assertThat(dao.getPilaDatosGenerales().getPersonal().get(0).getDelegacion().getNombre(), CoreMatchers.is("del2"));


    }

}
