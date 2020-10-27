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

    public boolean descargaDatos() {
        //Dao Personal llama a descarga tambien a delegaciones.

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
        return true;
    }



    public List recogerLIstado(Apartados apartado)
    {
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

    public boolean modificar(Object item, int indice) {
        if(item == null)return false;
        pilaDatosGenerales.getPersonal().set(indice,(Personal)item);
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

    private boolean guardar(){
        try{
            return true;
        }catch (Exception er){
            return false;
        }
    }

    public boolean Login(Usuario user){

        return true;
    }


}
