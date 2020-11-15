package com.app.console.TestingTTD;

import com.app.console.Apartados;
import dao.DaoXML;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Proyecto;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class ProyectoTest {

    @Test
    public void testCrear() throws Exception{
        Proyecto proyecto = new Proyecto();
        Delegacion delegacion = new Delegacion();

        DaoXML dao = new DaoXML("testTTDProyecto.xml");

        //Cración de proyecto y delegación.
        proyecto.setNombre("proy1");
        delegacion.setNombre("dele1");

        proyecto.setDelegacion(delegacion);
        delegacion.getPersonal().add(proyecto);

        //Se crea en el xml los datos
        dao.crear(proyecto, Apartados.PROYECTOS);
        dao.crear(delegacion,Apartados.DELEGACIONES);

        //volvemos a cargar el DAO
        dao.descargaDatos(Apartados.DELEGACIONES);

        //Verificamos que delegación esta bien
        Assert.assertThat(dao.getPilaDatosGenerales().getDelegaciones().get(0).getNombre(), CoreMatchers.is("dele1"));

        //verficamos que proyectos esta bien.
        Assert.assertThat(dao.getPilaDatosGenerales().getProyectos().get(0).getNombre(), CoreMatchers.is("proy1"));

        proyecto.setProyecto(proyecto);

        dao.modificar(proyecto,0, Apartados.PROYECTOS);

        //verificamos que los proyectos de la delegación sean iguales que proyecto
        Assert.assertThat(dao.getPilaDatosGenerales().getPersonal().get(0).getDelegacion().getNombre(), CoreMatchers.is("dele1"));



    }

}
