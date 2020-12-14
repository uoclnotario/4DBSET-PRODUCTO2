package com.app.console.TestingTTD;

import com.app.console.Apartados;
import dao.DaoSql;
import dao.SqlController;
import logicaEmpresarial.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;


public class SqlCreateDefaultUser {


    @Test
    public void testCrear() throws Exception{
        SqlController conexion = new SqlController("localhost","3306","4DBSET","root","1021");
        DaoSql sqlDao = new DaoSql(conexion);
        Usuario user = new Usuario();
        user.setNombre("admin");
        user.setPassword("1021");
        Boolean retoerno = sqlDao.crear(user, Apartados.USUARIOS);
        if(sqlDao.existeUnError())
            System.out.println(sqlDao.getMensajeError());

        Assert.assertThat(retoerno, CoreMatchers.is(true));
   }
}
