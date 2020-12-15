package dao;

import com.app.console.Apartados;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Proyecto;
import logicaEmpresarial.Usuario;

import java.util.List;

public class FactoryDAO {
    public enum typeDao{XML,SQL}

    public static IDao loadModel(typeDao tipoPersistencia){
        switch (tipoPersistencia){
            case XML:
                    //Configurar los datos de configuración del dao.
                    DaoXML dao = new DaoXML("dataONG.xml");
                    return dao;
            case SQL:
                    SqlController utilConexionSql= new SqlController("localhost","3306","4DBSET","root","1021");

                    //Verificar si la base de datos se creo.
                    if(utilConexionSql.getBdRecienCreada()){
                        System.out.println("Exportando datos locales a la base de datos...");

                        IDao daoXml = loadModel(FactoryDAO.typeDao.XML);

                        //Conecto al sql.
                        SqlController conexion = new SqlController("localhost","3306","4DBSET","root","1021");
                        DaoSql sqlDao = new DaoSql(conexion);

                        if(!daoXml.descargaDatos(Apartados.NINGUNO))
                            return  sqlDao;


                        //USUARIOS
                        for(int i = 0; i < daoXml.getPilaDatosGenerales().getUsuarios().size();i++){
                            sqlDao.crear(daoXml.getPilaDatosGenerales().getUsuarios().get(i),Apartados.USUARIOS);
                        }

                        //DELEGACION
                        for(int i = 0; i < daoXml.getPilaDatosGenerales().getDelegaciones().size();i++){
                            sqlDao.crear(daoXml.getPilaDatosGenerales().getDelegaciones().get(i),Apartados.DELEGACIONES);
                        }

                        //PROYECTOS
                        for(int i = 0; i < daoXml.getPilaDatosGenerales().getProyectos().size();i++){
                            sqlDao.crear(daoXml.getPilaDatosGenerales().getProyectos().get(i),Apartados.PROYECTOS);
                        }

                        //PERSONAL
                        for(int i = 0; i < daoXml.getPilaDatosGenerales().getPersonal().size();i++){
                            sqlDao.crear(daoXml.getPilaDatosGenerales().getPersonal().get(i),Apartados.PERSONAL);
                        }

                        return sqlDao;
                    }else{
                        DaoSql sqlDao = new DaoSql(utilConexionSql);
                        return sqlDao;
                    }
        }
        return null;
    }
}
