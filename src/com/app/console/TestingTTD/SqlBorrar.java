package com.app.console.TestingTTD;

import com.app.console.Apartados;
import dao.DaoSql;
import dao.SqlController;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;


public class SqlBorrar {

    @Test
    public void testCrear() throws Exception{
        SqlController conexion = new SqlController("localhost","3306","4DBSET","root","1021");

        DaoSql sqlDao = new DaoSql(conexion);

        ///delegaciones
        Assert.assertThat(sqlDao.borrar(2,Apartados.DELEGACIONES), CoreMatchers.is(true));

    }

}
