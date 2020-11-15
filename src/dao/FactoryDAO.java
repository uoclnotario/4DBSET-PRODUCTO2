package dao;

public class FactoryDAO {
    public enum typeDao{XML,SQL}

    public static IDao lodaModel(typeDao tipoPersistencia){
        switch (tipoPersistencia){
            case XML:
                    //Configurar los datos de configuración del dao.
                    DaoXML dao = new DaoXML();
                    dao.setFilePath("dataONG.xml");
                    return dao;
            case SQL:
                    return  null;

        }
        return null;
    }
}
