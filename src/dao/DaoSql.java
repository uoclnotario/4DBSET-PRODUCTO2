package dao;

import com.app.console.Apartados;
import logicaEmpresarial.*;
import dao.SqlController.*;

import javax.print.DocFlavor;
import java.lang.invoke.SwitchPoint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DaoSql implements IDao {
    private  enum Operacion{SELECT, UPDATE,DELETE, INSERT}
    private SqlController controlerSql;
    private Exception mensajeError;
    private boolean existeError;
    private Ong pilaDatosGenerales;

    public DaoSql(SqlController controller) {
        this.controlerSql = controller;
        pilaDatosGenerales = new Ong();
    }

    public void XMLaSQL(){
        
        // accede a la ruta sql
        // si no se encuentra BBDD SQL...
        if (true){
            // la importa desde el XML
        }
    }

    @Override
    public boolean descargaDatos(Apartados apartados) {


        return recoger(apartados);
    /*

        if (recoger(Apartados.DELEGACIONES) &&
                recoger(Apartados.PROYECTOS) &&
                recoger(Apartados.PERSONAL) &&
                recoger(Apartados.USUARIOS)) {
            //TODO RECORRER PERSONAL Y ANIDAR RELACIONES.
            return true;
        }

     */

    }

    @Override
    public List recogerListado(Apartados apartado) {
        switch (apartado){
            case NINGUNO: return null;
            case PROYECTOS:return pilaDatosGenerales.getProyectos();
            case PERSONAL:return pilaDatosGenerales.getPersonal();
            case DELEGACIONES:return pilaDatosGenerales.getDelegaciones();
            case USUARIOS:return pilaDatosGenerales.getUsuarios();
            default: return null;
        }
    }

    @Override
    public boolean crear(Object item, Apartados apartado) {
        //Asi es como se deberia de hacer para escribir en limpio:
        //estas variables son constantes y queda mejor que nos la llevemos a otra parte del codigo auqnue de momento las dejo por aqui

        final String SQL_INSERT_PERSONAL = "INSERT INTO personal (fechaAlta,fechaBaja,estado)VALUES(?,?,?)";
        final String SQL_INSERT_PERSONA = "INSERT INTO persona (TipoPersona,NIF_DNI,Nombre,FechaNacimiento,Domicilio)VALUES(?,?,?,?,?)";
        //*a La tabla Personal le falta un id Delegación.
        final String SQL_INSERT_DELEGACION = "INSERT INTO delegacion(nombre,direccion,telefono)VALUES(?,?,?)";
        final String SQL_INSERT_PROYECTOS = "INSERT INTO proyecto(fechaAlta,fechaBaja,nombre,fechaInicio,estado)VALUES(?,?,?,?,?)";
        final String SQL_INSERT_USUARIO = "INSERT INTO usuario(tipoUsuario,nombre,hashing)VALUES(?,?,?)";

        //Si os fijais le he quitado el nombre de la base de datos a las consultas por que es redundante, ya que en la conexión
        //Ya especificamos el nombre de la base de datos a la que nos hemos conectados y la utilidad de conexión ya esta conectada a la base de datos.

        //Creación de variables dependiendo del apartado:
        String cadenaSql;

        String cadenaSql2 = "";

        switch (apartado) {
            case PERSONAL:
                cadenaSql = SQL_INSERT_PERSONAL;
                cadenaSql2 = SQL_INSERT_PERSONA;
                break;
            case DELEGACIONES:
                cadenaSql = SQL_INSERT_DELEGACION;
                break;
            case PROYECTOS:
                cadenaSql = SQL_INSERT_PROYECTOS;
                break;
            case USUARIOS:
                cadenaSql = SQL_INSERT_USUARIO;
                break;
            default:
                return false;
        }

        //Seteamos los valores
        ArrayList<Object> valores = new ArrayList<>();
        ArrayList<Object> valores2 = new ArrayList<>(); // PERSONA

        switch (apartado) {
            case PERSONAL:
                valores.add(((Personal)item).getFechaAlta());
                valores.add(((Personal)item).getFechaBaja());
                valores.add(((Personal)item).getEstado());
                // PERSONA
                valores2.add(((Persona)item).getNif_dni());
                valores2.add(((Persona)item).getNombre());
                valores2.add(((Persona)item).getFechaDeNacimiento());
                valores2.add(((Persona)item).getDomicilio());
                valores2.add(((Persona)item).getTipo());
                break;
            case PROYECTOS:
                //TODO
                valores.add(((Proyecto)item).getFechaAlta());
                valores.add(((Proyecto)item).getFechaBaja());
                valores.add(((Proyecto)item).getNombre());
                valores.add(((Proyecto)item).getFechaDeInicio());
                valores.add(((Proyecto)item).getEstado());
                break;
            case DELEGACIONES:
                //TODO
                valores.add(((Delegacion)item).getNombre());//Nombre
                valores.add(((Delegacion)item).getDireccion());//Dirección
                valores.add(((Delegacion)item).getTelefono());//Telefono
                break;
            case USUARIOS:
                //TODO
                valores.add(((Usuario)item).getIntRol());
                valores.add(((Usuario)item).getNombre());
                valores.add(((Usuario)item).getHashing());

                break;
            default:
                return false;
        }

        if(valores.size()>0) {
            int ejecucion;
            if(apartado == Apartados.PERSONAL){
                ejecucion =  controlerSql.ejecutar(cadenaSql, valores);
//                ejecucion =  controlerSql.ejecutar(cadenaSql2, valores2);
            }
            else{
                ejecucion =  controlerSql.ejecutar(cadenaSql, valores);
            }

            //Ejecugamos la sentencia
            if ( ejecucion > 0)
                return true;
            else{
                if(ejecucion == -1){
                    existeError = true;
                    mensajeError = controlerSql.getErrores();
                    System.out.println(mensajeError);
                }

                return false;
            }
        }else{
            return false;
        }

    }

    @Override
    public boolean modificar(Object item, int indice, Apartados apartado) {
/*
        String ident = String.valueOf(indice);

        final String SQL_UPDATE_PERSONAL = "UPDATE `persona` WHERE idPersona = '" + ident + "';";
        final String SQL_UPDATE_DELEGACION = "UPDATE `delegacion` WHERE id = '" + ident + "';";
        final String SQL_UPDATE_PROYECTO = "UPDATE `proyecto WHERE id = '" + ident + "';";
        final String SQL_UPDATE_USUARIO = "UPDATE `usuario WHERE id = '" + ident + "';";

        //Si os fijais le he quitado el nombre de la base de datos a las consultas por que es redundante, ya que en la conexión
        //Ya especificamos el nombre de la base de datos a la que nos hemos conectados y la utilidad de conexión ya esta conectada a la base de datos.

        //Creación de variables dependiendo del apartado:
        String cadenaSql;

        switch (apartado) {
            case PERSONAL:
                cadenaSql = SQL_UPDATE_PERSONAL;
                break;
            case PROYECTOS:
                cadenaSql = SQL_UPDATE_DELEGACION;
                break;
            case DELEGACIONES:
                cadenaSql = SQL_UPDATE_PROYECTO;
                break;
            case USUARIOS:
                cadenaSql = SQL_UPDATE_USUARIO;
                break;
            default:
                return false;
        }

        //Ahora se crea el statment
        PreparedStatement cadenaCreacion = controlerSql.getPrepare(cadenaSql);

        try {
            if (cadenaCreacion != null) {

                //Seteamos los valores
                switch (apartado) {
                    case PERSONAL:
                        //TODO
                        cadenaCreacion.setInt(1, ((Personal) item).getId());
                        break;
                    case PROYECTOS:
                        //TODO
                        cadenaCreacion.setInt(1, ((Proyecto) item).getId());
                        break;
                    case DELEGACIONES:
                        //TODO
                        cadenaCreacion.setInt(1, ((Delegacion) item).getId());
                        break;
                    case USUARIOS:
                        //TODO
                        cadenaCreacion.setInt(1, ((Usuario) item).getId());
                        break;
                    default:
                        return false;
                }

                //Ejecugamos la sentencia
                if (controlerSql.ejecutar(cadenaCreacion) > 0)
                    return true;
                else
                    return false;

            }
            else {
                System.out.println("ERROR: prepare vacio");
                //Muestra el error del prepare vacio
            }

        } catch (Exception ex) {
            return false;
        }*/
        return false;
    }


    @Override
    public boolean borrar(int indice, Apartados apartado) {

        String ident = String.valueOf(indice);
        final String SQL_DELETE_PERSONAL    = "DELETE FROM persona WHERE idPersona = " + ident ;
        final String SQL_DELETE_DELEGACION  = "DELETE FROM delegacion WHERE idDelegacion = " + ident ;
        final String SQL_DELETE_PROYECTO    = "DELETE FROM proyecto WHERE id = " + ident ;
        final String SQL_DELETE_USUARIO     = "DELETE FROM usuario WHERE id = " + ident ;


        System.out.println(SQL_DELETE_DELEGACION);

        //Creación de variables dependiendo del apartado:
        String cadenaSql;

        switch (apartado) {
            case PERSONAL:
                cadenaSql = SQL_DELETE_PERSONAL;
                break;
            case DELEGACIONES:
                cadenaSql = SQL_DELETE_DELEGACION;
                break;
            case  PROYECTOS:
                cadenaSql = SQL_DELETE_PROYECTO;
                break;
            case USUARIOS:
                cadenaSql = SQL_DELETE_USUARIO;
                break;
            default:
                return false;
        }

        if(controlerSql.update(cadenaSql)){
            return  true;
        }else{
            mensajeError = controlerSql.getErrores();
            System.out.println(mensajeError);
            existeError = true;
            return false;
        }
    }


    @Override
    public boolean Login(Usuario user) {

       PreparedStatement ps = null;
       ResultSet rs = null;
       String sql = "SELECT * FROM usuario WHERE hashing = ?";

       try{
           ps = controlerSql.getConecction().prepareStatement(sql);
           ps.setString(1, user.getHashing());
           rs = ps.executeQuery();
           if(rs.next()) {
               Usuario readSqlUser = (Usuario) create(rs, Apartados.USUARIOS);
               user.setId(readSqlUser.getId());
               user.setRol(readSqlUser.getRol());
               return true;
           }
           return false;
       }
       catch (SQLException ex){
           mensajeError = controlerSql.getErrores();
           System.out.println(mensajeError);
           existeError = true;
           return false;
       }
    }

    @Override
    public boolean existeUnError() {
        return existeError;
    }

    @Override
    public String getMensajeError() {
        return mensajeError.getMessage();
    }

    @Override
    public Ong getPilaDatosGenerales() {
//        getPilaDatosGenerales();
        return pilaDatosGenerales;
    }

    private boolean recoger(Apartados apartados) {
        String sql;

        switch (apartados) {
            case PERSONAL:
                sql = "SELECT * FROM 4dbset.personal";
                break;
            case DELEGACIONES:
                sql = "SELECT * FROM delegacion";
                break;
            case PROYECTOS:
                sql = "SELECT * FROM 4dbset.proyectos;";
                break;
            case USUARIOS:
                sql = "SELECT * FROM 4dbset.usuarios;";
                break;
            default:
                return false;
        }

        try {
            PreparedStatement sqlConection = controlerSql.getConecction().prepareStatement(sql);

            if (sqlConection == null) {
                mensajeError = controlerSql.getErrores();
                existeError = true;
                return false;
            }

            ResultSet rs = sqlConection.executeQuery();

            while (rs.next())
                if (create(rs, apartados) != null)
                    switch (apartados) {
                        case PERSONAL:
                            pilaDatosGenerales.getPersonal().add((Personal) create(rs, apartados));
                            break;
                        case DELEGACIONES:
                            pilaDatosGenerales.getDelegaciones().add((Delegacion) create(rs, apartados));
                            break;
                        case PROYECTOS:
                            pilaDatosGenerales.getProyectos().add((Proyecto) create(rs, apartados));
                            break;
                        case USUARIOS:
                            pilaDatosGenerales.getUsuarios().add((Usuario) create(rs, apartados));
                            break;
                        default:
                            return false;
                    }
            return true;

        } catch (Exception e) {
            System.out.println("Error"+e.getMessage());
            mensajeError = e;
            existeError = true;
            return false;
        } finally {
            controlerSql.close();
        }
    }

    //Crea un objeto partiendo de los datos recogidos de la BD.
    private Object create(ResultSet rs, Apartados apartado) throws SQLException {
        Object element;

        switch (apartado) {
            case PERSONAL:
                element = new Personal();
                ((Personal) element).setEstado(rs.getBoolean("estado"));
                ((Personal) element).setId(rs.getInt("idPersonal"));
                break;

            case DELEGACIONES:
                element = new Delegacion();
                ((Delegacion) element).setId(rs.getInt("idDelegacion"));
                ((Delegacion) element).setNombre(rs.getString("nombre"));
                ((Delegacion) element).setDireccion(rs.getString("direccion"));
                ((Delegacion) element).setTelefono(rs.getString("telefono"));
                break;

            case PROYECTOS:
                element = new Proyecto();
                ((Proyecto) element).setId(rs.getInt("idProyectos"));
                ((Proyecto) element).setNombre(rs.getString("Nombre"));
                break;

            case USUARIOS:
                element = new Usuario();
                ((Usuario) element).setId(rs.getInt("idUsuario"));
                ((Usuario) element).setIntRol(rs.getInt("tipoUsuario"));
                ((Usuario) element).setNombre(rs.getString("nombre"));
                ((Usuario) element).setHasing(rs.getString("hashing"));
                break;

            default:
                return null;
        }

        return element;
    }



/*  ESTO PARA CUANDO VEAMOS QUE TODO FUNCIONA
    private String factorySqlQuery(Apartados apartado, Operacion op){

        switch (apartado){
            case PERSONAL:
                switch (op){
                    case SELECT:
                        return "";
                    break;
                    case INSERT:
                        return "INSERT INTO persona (TipoPersona,NIF_DNI,Nombre,FechaNacimiento,Domicilio)VALUES(?,?,?,?,?,?)";
                    break;
                    case UPDATE:
                        return "";
                    break;
                    case DELETE:
                        return "DELETE `persona` WHERE idPersona = ?";
                    break;
                }
            break;
            case DELEGACIONES:
                switch (op){
                    case SELECT:
                        return "";
                    break;
                    case INSERT:
                        return "INSERT INTO persona (TipoPersona,NIF_DNI,Nombre,FechaNacimiento,Domicilio)VALUES(?,?,?,?,?,?)";
                    break;
                    case UPDATE:
                        return "";
                    break;
                    case DELETE:
                        return "DELETE `persona` WHERE idPersona = ?";
                    break;
                }
                break;
            case PROYECTOS:
                switch (op){
                    case SELECT:
                        return "";
                    break;
                    case INSERT:
                        return "INSERT INTO persona (TipoPersona,NIF_DNI,Nombre,FechaNacimiento,Domicilio)VALUES(?,?,?,?,?,?)";
                    break;
                    case UPDATE:
                        return "";
                    break;
                    case DELETE:
                        return "DELETE `persona` WHERE idPersona = ?";
                    break;
                }
                break;

        }
    }


 */
}
