package dao;

import logicaEmpresarial.Ong;
import logicaEmpresarial.Personal;

import java.util.List;

public interface Dao {
    public List RecogerLIstado();
    public boolean Crear(Object item);
    public boolean DescargaDatos();
    public boolean Modificar(Object item, int indice);
}
