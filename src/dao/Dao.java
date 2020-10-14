package dao;

import logicaEmpresarial.Personal;

import java.util.List;

public interface Dao {
    public List RecogerLIstado();
    public boolean Crear(Object item);
}
