package dao;

import com.app.console.Apartados;
import logicaEmpresarial.*;

import java.util.List;

public interface Crud {

    //Descarga los datos desde el origen de datos.
    public boolean descargaDatos(Apartados apartados);

    //Metodo que devuelve los datos del apartado seleccionado.
    public List recogerLIstado(Apartados apartado);

    //Crear elemento en un apartado.
    public boolean crear(Object item, Apartados apartado);
    //Modificar elemento en apartado
    public boolean modificar(Object item, int indice,Apartados apartado);
    //Eliminar Elemento en apartado
    public boolean borrar(int indice,Apartados apartado);

}
