package dao;

import com.app.console.Apartados;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import logicaEmpresarial.*;


import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;


import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DaoXML {
    XStream xstream = new XStream(new StaxDriver());


    private  String SERIALIZED_FILE_NAME = "dataONG.xml";
    private Ong pilaDatosGenerales;
    private String mensajeError;
    private boolean error;

    public DaoXML() {
        xstream.setMode(XStream.ID_REFERENCES);
        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.allowTypeHierarchy(Collection.class);
        xstream.allowTypesByWildcard(new String[] {
                "logicaEmpresarial.**"
        });

        pilaDatosGenerales = new Ong();
    }

    public Ong getPilaDatosGenerales() {
        return pilaDatosGenerales;
    }

    public boolean descargaDatos(Apartados apartados) {
        leerXML();
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

        return guardarXML();
    }
    public boolean modificar(Object item, int indice,Apartados apartado) {
        if(item == null)
            return false;


        //Modificar el item.
        switch (apartado){
            case NINGUNO: return false;
            case INGRESOS:  pilaDatosGenerales.getIngresos().set(indice,(Ingresos)item);break;
            case PROYECTOS: pilaDatosGenerales.getProyectos().set(indice,(Proyecto) item);break;
            case SOCIOS:    pilaDatosGenerales.getSocios().set(indice,(Socios) item);break;
            case PERSONAL:
                pilaDatosGenerales.getPersonal().get(indice).setDelegacion(null);//Ponemos a null la delegación del personal asignado, para quitarlo de la memoria
                pilaDatosGenerales.getPersonal().set(indice,(Personal)item);
                break;
            case DELEGACIONES: pilaDatosGenerales.getDelegaciones().set(indice,(Delegacion) item);break;
            case USUARIOS: pilaDatosGenerales.getUsuarios().set(indice,(Usuario) item);break;
            default: return false;
        }

        //Guardar los cambios
        return guardarXML();
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
            case PERSONAL:
                    pilaDatosGenerales.getPersonal().get(indice).setDelegacion(null);
                    pilaDatosGenerales.getPersonal().remove(indice);
                break;
            case DELEGACIONES:
                    for(int i = 0; i<pilaDatosGenerales.getPersonal().size();i++) {
                        if(pilaDatosGenerales.getPersonal().get(i).getDelegacion()!= null)
                            if (pilaDatosGenerales.getPersonal().get(i).getDelegacion().equals(pilaDatosGenerales.getDelegaciones().get(indice)))
                                pilaDatosGenerales.getPersonal().get(i).setDelegacion(null);
                    }

                    pilaDatosGenerales.getDelegaciones().remove(indice);
                break;
            case USUARIOS: pilaDatosGenerales.getUsuarios().remove(indice);break;
            default: return false;
        }

        //Guardar los cambios
        return guardarXML();
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
    private boolean guardarXML(){
            String xml = xstream.toXML(pilaDatosGenerales);

            FileOutputStream fos = null;
            try{
                BufferedReader reader = new BufferedReader(new StringReader(xml));
                BufferedWriter writer = new BufferedWriter(new FileWriter(SERIALIZED_FILE_NAME,false));

                while ((xml = reader.readLine()) != null) {

                    writer.write(xml + System.getProperty("line.separator"));

                }

                writer.close();
            }catch (Exception e){
                System.err.println("Error in XML Write: " + e.getMessage());
            }
            finally{
                if(fos != null){
                    try{
                        fos.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        return true;
    }

    private boolean leerXML()  {
        try{
            Path fileName = Path.of(SERIALIZED_FILE_NAME);
            String actual = Files.readString(fileName);

            pilaDatosGenerales = new Ong();
            pilaDatosGenerales = (Ong) xstream.fromXML(actual);

        }catch(Exception e){
            System.out.println(e.getMessage());
            mensajeError="Se ha producido un error al guardar el archivo";
            error = true;
            return false;
        }

        return true;
    }

}
