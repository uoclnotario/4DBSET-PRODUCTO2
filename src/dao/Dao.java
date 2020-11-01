package dao;

import logicaEmpresarial.Ong;
import logicaEmpresarial.Personal;

import java.util.List;

public interface Dao {


    public boolean descargaDatos();

    public boolean crear(Object item);
    public List recogerLIstado();
    public boolean modificar(Object item, int indice);
   // public boolean eliminar();


}
