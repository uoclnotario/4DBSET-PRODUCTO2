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
import java.util.Date;


public class SqlControllerTest {


    @Test
    public void testCrear() throws Exception{


      //  Assert.assertThat(new Date(System.currentTimeMillis()) instanceof Date, CoreMatchers.is(true));


        SqlController conexion = new SqlController("localhost","3306","4DBSET","root","1021");
        DaoSql sqlDao = new DaoSql(conexion);

  /*       //Usuario admin de prueba

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
/*        Delegacion nuevoDelegacion = new Delegacion();
        nuevoDelegacion.setNombre("PruebaDelegacion");
        nuevoDelegacion.setDireccion("EnAlgunLugar");
        nuevoDelegacion.setTelefono(null);
        Assert.assertThat(sqlDao.crear(nuevoDelegacion,Apartados.DELEGACIONES), CoreMatchers.is(true));
*/
        // Parametros
        Date fecha = new Date(System.currentTimeMillis());
        Delegacion delegacion = new Delegacion();

        //Test de creación PERSONAL.
        Personal nuevoPersonal = new Personal(fecha, fecha, true,"12312312A", "NombreDePersona", fecha, "c/Calle nº1", Persona.Tipo.FISICA);

        Assert.assertThat(sqlDao.crear(nuevoPersonal,Apartados.PERSONAL), CoreMatchers.is(true));

        



        //Test de creación PROYECTO.
/*        Delegacion nuevoDelegacion = new Delegacion();
        nuevoDelegacion.setNombre("PruebaDelegacion");
        nuevoDelegacion.setDireccion("EnAlgunLugar");
        nuevoDelegacion.setTelefono(null);
        Assert.assertThat(sqlDao.crear(nuevoDelegacion,Apartados.DELEGACIONES), CoreMatchers.is(true));
*/

        //Test de creación USUARIO.
/*        Delegacion nuevoDelegacion = new Delegacion();
        nuevoDelegacion.setNombre("PruebaDelegacion");
        nuevoDelegacion.setDireccion("EnAlgunLugar");
        nuevoDelegacion.setTelefono(null);
        Assert.assertThat(sqlDao.crear(nuevoDelegacion,Apartados.DELEGACIONES), CoreMatchers.is(true));
*/    }
}
