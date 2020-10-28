package dao;

import com.app.console.Apartados;
import logicaEmpresarial.*;


import java.beans.ExceptionListener;
import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;


import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

public class DaoXML {

    private Ong pilaDatosGenerales;
    private String mensajeError;
    private boolean error;



    public DaoXML(Ong pilaDatos) {
        pilaDatosGenerales = pilaDatos;
    }

    public boolean descargaDatos(Apartados apartados) {
        //Descarga los datos, la descarga de cada apartado tendra que descargar los
        //elementos que necesite para mostrar ejemplo:
        //Personal necesita: Delegaciones y Proyectos para la interactuar con el usuario.

        switch (apartados){

            case PERSONAL:
                pilaDatosGenerales.getPersonal().clear();
                    if(pilaDatosGenerales.getPersonal().size()< 1) {
                    //Esto es para depurar
                    pilaDatosGenerales.getPersonal().add(new Personal(null,
                            null,
                            false,
                            new Identificacion("12345", "nombre1", null, "domicilio1", Identificacion.Tipo.PERSONA)));

                    pilaDatosGenerales.getPersonal().add(new Personal(null,
                            null,
                            false,
                            new Identificacion("12345", "nombre2", null, "domicilio2", Identificacion.Tipo.PERSONA)));
                }

                break;
            default:return false;
        }

        return true;
    }

    public List recogerLIstado(Apartados apartado){
        switch (apartado){
            case NINGUNO: return null;
            case INGRESOS:return  pilaDatosGenerales.getPersonal();
            case PROYECTOS:return pilaDatosGenerales.getProyectos();
            case SOCIOS:return pilaDatosGenerales.getSocios();
            case PERSONAL:return pilaDatosGenerales.getPersonal();
            case DELEGACIONES:return pilaDatosGenerales.getDelegaciones();
            case USUARIOS:return pilaDatosGenerales.getUsuarios();
            default: return null;
        }

    }

    public boolean crear(Object item, Apartados apartado) {
        if(item == null)return false;

        switch (apartado){
            case NINGUNO: return false;
            case INGRESOS:
                    pilaDatosGenerales.getIngresos().add((Ingresos)item);
                    break;
            case PROYECTOS:
                    pilaDatosGenerales.getProyectos().add((Proyecto) item);
                    break;
            case SOCIOS:
                    pilaDatosGenerales.getSocios().add((Socios) item);
                    break;
            case PERSONAL:
                    pilaDatosGenerales.getPersonal().add((Personal) item);
                    break;
            case DELEGACIONES:
                    pilaDatosGenerales.getDelegaciones().add((Delegacion) item);
                    break;

            case USUARIOS:
                pilaDatosGenerales.getDelegaciones().add((Delegacion) item);
                break;
            default: return false;
        }


        return true;
    }

    public boolean modificar(Object item, int indice,Apartados apartado) {
        if(item == null)
            return false;

        //Descarga listado de xml
        if(!descargaDatos(apartado)){
            return false;
        }

        //Modificar el item.
        switch (apartado){
            case NINGUNO: return false;
            case INGRESOS:  pilaDatosGenerales.getIngresos().set(indice,(Ingresos)item);break;
            case PROYECTOS: pilaDatosGenerales.getProyectos().set(indice,(Proyecto) item);break;
            case SOCIOS:    pilaDatosGenerales.getSocios().set(indice,(Socios) item);break;
            case PERSONAL:  pilaDatosGenerales.getPersonal().set(indice,(Personal)item);break;
            case DELEGACIONES: pilaDatosGenerales.getDelegaciones().set(indice,(Delegacion) item);break;
            case USUARIOS: pilaDatosGenerales.getUsuarios().set(indice,(Usuario) item);break;
            default: return false;
        }

        //Guardar los cambios
        if(!guardarXML(apartado))
            return  false;

        return true;
    }

    public boolean borrar(int indice,Apartados apartado){
        //Descarga listado de xml
        if(!descargaDatos(apartado)){
            return false;
        }

        //Modificar el item.
        switch (apartado){
            case NINGUNO: return false;
            case INGRESOS:  pilaDatosGenerales.getIngresos().remove(indice);break;
            case PROYECTOS: pilaDatosGenerales.getProyectos().remove(indice);break;
            case SOCIOS:    pilaDatosGenerales.getSocios().remove(indice);break;
            case PERSONAL:  pilaDatosGenerales.getPersonal().remove(indice);break;
            case DELEGACIONES: pilaDatosGenerales.getDelegaciones().remove(indice);break;
            case USUARIOS: pilaDatosGenerales.getUsuarios().remove(indice);break;
            default: return false;
        }

        //Guardar los cambios
        if(!guardarXML(apartado))
            return  false;

        return true;
    }


    public String getMensajeError() {
        String mensaje = mensajeError;

        //Reinicializamos el error una vez leeido.
        mensajeError = null;
        error=false;

        return mensaje;
    }
    public boolean existeUnError(){
        return error;
    }
    public boolean Login(Usuario user){

        return true;
    }



    //Operaciones de acceso a xml, debera de realizarse de forma serializada, es decir hay que serializar los objetos y
    //convertirlos a xml para leerlos y guardarlos.
    private boolean guardarXML(Apartados apartado){
        try{
            return true;
        }catch (Exception er){
            mensajeError="Se ha producido un error al guardar el archivo";
            error = true;
            return false;
        }
    }

    private boolean leerXML(Apartados apartado){
        try{
            return true;
        }catch (Exception er){
            mensajeError="Se ha producido un error al guardar el archivo";
            error = true;
            return false;
        }
    }




}
