package com.app.console.TestingTTD;

import com.app.console.Apartados;
import com.thoughtworks.xstream.core.util.Pool;
import dao.DaoSql;
import dao.FactoryDAO;
import dao.IDao;
import dao.SqlController;
import logicaEmpresarial.Contratados;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Persona;
import logicaEmpresarial.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;


public class ExportacionDeXmlASql {


    @Test
    public boolean testCrear() throws Exception{
        //TODO DESARROLLAR ALGORITMO DE EXPORTACIÓN
        //Esta función precondicion es que ya se haya creado la base de datos desde cero, y se encuentre vacia.
        IDao daoXml = FactoryDAO.lodaModel(FactoryDAO.typeDao.XML);

        if(!daoXml.descargaDatos(Apartados.NINGUNO))
            return  false;

        //Conecto al sql.
        SqlController conexion = new SqlController("localhost","3306","4DBSET","root","1021");
        DaoSql sqlDao = new DaoSql(conexion);

        //USUARIOS
        for(Usuario user: (List<Usuario>)daoXml.recogerListado(Apartados.USUARIOS)){
            sqlDao.crear(user,Apartados.USUARIOS);
        }



        //PROYECTOS
            //cuando crees los valores de delegaciones hay que setear el id.

        //DElEGACIONES
            //cuando crees los valores de delegaciones hay que setear el id.

        //PERSONAL

        return true;
    }
}
