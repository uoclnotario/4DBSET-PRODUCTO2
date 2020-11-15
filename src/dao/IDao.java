package dao;

import com.app.console.Apartados;
import logicaEmpresarial.*;

import java.util.List;

public interface IDao {

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

    //Realiza opreación de login
    public boolean Login(Usuario user);

    //Resuelve si el dao ha entrado en estado de error.
    public boolean existeUnError();

    //Devuelve el mensaje del error.
    public String getMensajeError();

    //Devuelve todos los datos
    public Ong getPilaDatosGenerales();
}
