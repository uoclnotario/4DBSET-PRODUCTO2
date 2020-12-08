package com.app.console.TestingTTD;

import com.app.console.Apartados;
import dao.DaoSql;
import dao.DaoXML;
import dao.SqlController;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Personal;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;


public class SqlControllerTest {

    @Test
    public void testCrear() throws Exception{
        SqlController conexion = new SqlController("localhost","3306","4DBSET","root","1021");

        DaoSql sqlDao = new DaoSql(conexion);
        sqlDao.descargaDatos(Apartados.PERSONAL);

        sqlDao.crear(new Personal(),Apartados.PERSONAL);


        /*
        //Ejecuta una acción sin esperar recoger resultados.
        Assert.assertThat((boolean)conexion.update("SELECT 'HOLA'"),CoreMatchers.is(true));


        //Ejecuta una acción y devuelve la id.


        Assert.assertTrue((int)conexion.ejecutar("INSERT INTO personal(FechaAlta,Estado)VALUES(NOW(),1);")>-1);

        if(conexion.getErrores() != null)
            System.out.println(conexion.getErrores());


        ResultSet read = conexion.query("SELECT * FROM 4dbset.personal;");

        while(read.next())
        {
            System.out.println("id:"+read.getString("idPersonal"));
        }
        */

    }

}
