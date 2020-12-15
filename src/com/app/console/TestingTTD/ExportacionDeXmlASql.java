package com.app.console.TestingTTD;

import com.app.console.Apartados;
import dao.DaoSql;
import dao.FactoryDAO;
import dao.IDao;
import dao.SqlController;
import logicaEmpresarial.Usuario;
import org.junit.Test;

import java.util.List;


public class ExportacionDeXmlASql {


    @Test
    public boolean testCrear() throws Exception{
        //TODO DESARROLLAR ALGORITMO DE EXPORTACIÓN
        //Esta función precondicion es que ya se haya creado la base de datos desde cero, y se encuentre vacia.
        IDao daoXml = FactoryDAO.loadModel(FactoryDAO.typeDao.XML);

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
