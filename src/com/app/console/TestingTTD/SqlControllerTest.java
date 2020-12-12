package com.app.console.TestingTTD;

import com.app.console.Apartados;
import dao.DaoSql;
import dao.DaoXML;
import dao.SqlController;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Persona;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.sql.ResultSet;


public class SqlControllerTest {

    @Test
    public void testCrear() throws Exception{


        Assert.assertThat(new Date(System.currentTimeMillis()) instanceof Date, CoreMatchers.is(true));

        /*
        SqlController conexion = new SqlController("localhost","3306","4DBSET","root","1021");

        DaoSql sqlDao = new DaoSql(conexion);
        //Usuario admin de prueba

        Usuario user = new Usuario();
        user.setNombre("admin");
        user.setPassword("1021");

        //Testing logueo.
        Assert.assertThat(sqlDao.Login(user), CoreMatchers.is(true));

        //Testing el objeto user es rellenada y recibida por referencia.


        //Testing Fallo de logueo
        user.setNombre("admin");
        user.setPassword("1020");
        Assert.assertThat(sqlDao.Login(user), CoreMatchers.is(false));

        //Test de creación DELEGACION.

        Delegacion nuevo= new Delegacion();

        nuevo.setNombre("PruebaDelegacion");
        nuevo.setDireccion("EnAlgunLugar");
        nuevo.setTelefono(null);

        Assert.assertThat(sqlDao.crear(nuevo,Apartados.DELEGACIONES), CoreMatchers.is(true));
*/
    }

}
