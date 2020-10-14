package dao;

import java.util.List;

public class DaoUsuarios implements Dao{

    @Override
    public List RecogerLIstado() {
        return null;
    }

    @Override
    public boolean Crear(Object item) {
        return false;
    }

    //Busca en el xml la cadena de sha1.
    public boolean Login(String Sha1){

        return true;
    }
}
