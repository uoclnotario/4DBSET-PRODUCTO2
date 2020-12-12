package dao;

public class FactoryDAO {
    public enum typeDao{XML,SQL}

    public static IDao lodaModel(typeDao tipoPersistencia){
        switch (tipoPersistencia){
            case XML:
                    //Configurar los datos de configuración del dao.
                    DaoXML dao = new DaoXML("dataONG.xml");
                    return dao;
            case SQL:
                    DaoSql daoSql = new DaoSql(new SqlController("localhost","3306","4DBSET","root",""));
                    return  daoSql;

        }
        return null;
    }
}
