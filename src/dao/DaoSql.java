package dao;

import com.app.console.Apartados;
import logicaEmpresarial.*;

import javax.print.DocFlavor;
import java.sql.*;
import java.util.List;

public class DaoSql implements IDao{
    private SqlController controlerSql;
    private Exception mensajeError;
    private boolean existeError;
    private Ong pilaDatosGenerales;

    public DaoSql(SqlController controller){
        this.controlerSql = controller;
    }

    @Override
    public boolean descargaDatos(Apartados apartados) {

        if( recoger(Apartados.DELEGACIONES) &&
            recoger(Apartados.PROYECTOS) &&
            recoger(Apartados.PERSONAL) &&
            recoger(Apartados.USUARIOS))
        {

            //TODO RECORRER PERSONAL Y ANIDAR RELACIONES.

            return true;
        }

        return false;
    }

    @Override
    public List recogerListado(Apartados apartado) {
        return null;
    }

    @Override
    public boolean crear(Object item, Apartados apartado) {
        //Asi es como se deberia de hacer para escribir en limpio:

        //estas variables son constantes y queda mejor que nos la llevemos a otra parte del codigo auqnue de momento las dejo por aqui


        final String  SQL_INSERT_PERSONAL = "INSERT INTO `persona` (`idPersona`,`TipoPersona`,`NIF_DNI`,`Nombre`,`FechaNacimiento``Domicilio`)VALUES(?,?,?,?,?,?)";
                                            //*a La tabla Personal le falta un id Delegación.
        final String  SQL_INSERT_DELEGACION = "INSERT INTO `delegacion`(`id`,`nombre`,`direccion`,`telefono`)VALUES(?,?,?,?);";
        final String  SQL_INSERT_PROYECTO = "INSERT INTO `proyecto(`id`,`fechaAlta`,`fechaBaja`,`nombre`,`fechaInicio`,`estado`)VALUES(?,?,?,?,?,?);";
        final String  SQL_INSERT_USUARIO = "INSERT INTO `usuario(`tipoUsuarios`,`nombre`,`hasing`,`rol`,`id`)VALUES(?,?,?,?,?);";


        //Si os fijais le he quitado el nombre de la base de datos a las consultas por que es redundante, ya que en la conexión
        //Ya especificamos el nombre de la base de datos a la que nos hemos conectados y la utilidad de conexión ya esta conectada a la base de datos.


        //Creación de variables dependiendo del apartado:
        String cadenaSql;

        switch (apartado) {
            case PERSONAL:      cadenaSql=SQL_INSERT_PERSONAL;break;
            case PROYECTOS:     cadenaSql=SQL_INSERT_DELEGACION;break;
            case DELEGACIONES:  cadenaSql=SQL_INSERT_PROYECTO;break;
            case USUARIOS:      cadenaSql=SQL_INSERT_USUARIO;break;
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
                            cadenaCreacion.setInt(1, ((Personal)item).getId());
                        break;
                    case PROYECTOS:
                            //TODO
                            cadenaCreacion.setInt(1, ((Proyecto)item).getId());
                        break;
                    case DELEGACIONES:
                            //TODO
                            cadenaCreacion.setInt(1, ((Delegacion)item).getId());
                        break;
                    case USUARIOS:
                            //TODO
                            cadenaCreacion.setInt(1, ((Usuario)item).getId());
                        break;
                    default:
                        return false;
                }

                //Ejecugamos la sentencia
                if(controlerSql.ejecutar(cadenaCreacion) > 0)
                    return true;
                else
                    return false;

            } else {
                //Muestra el error del prepare vacio
            }

        } catch (Exception ex) {
            return false;
        }


        /* PARTE VUESTRAS.
        switch (apartado) {
            case PERSONAL:
            Personal nuevoItem = (Personal) item;

            PreparedStatement cadenaCreacion = controlerSql.getPrepare("INSERT INTO `4dbset`.`persona`\n" +
                    "(`idPersona`,\n" +
                    "`TipoPersona`,\n" +
                    "`NIF_DNI`,\n" +
                    "`Nombre`,\n" +
                    "`FechaNacimiento`,\n" +
                    "`Domicilio`)\n" +
                    "VALUES\n" +
                    "(?,\n" +
                    "?,\n" +
                    "?,\n" +
                    "?,\n" +
                    "?,\n" +
                    "?);");
            try {
                if (cadenaCreacion != null) {
                    cadenaCreacion.setInt(1, nuevoItem.getId());

                    controlerSql.ejecutar(cadenaCreacion);
                } else {
                    //Muestra el error del prepare vacio
                }

            } catch (Exception ex) {
                return false;
            }
            case DELEGACIONES:
                Delegacion nuevoItem = (Delegacion) item;

                PreparedStatement cadenaCreacion = controlerSql.getPrepare("INSERT INTO `4dbset`.`delegacion`\n" +
                        "(`id`,\n" +
                        "`nombre`,\n" +
                        "`direccion`,\n" +
                        "`telefono`,\n" +
                        "VALUES\n" +
                        "(?,\n" +
                        "?,\n" +
                        "?,\n" +
                        "?,\n" +
                        "?,\n" +
                        "?);");
                try {
                    if (cadenaCreacion != null) {
                        cadenaCreacion.setInt(1, nuevoItem.getId());

                        controlerSql.ejecutar(cadenaCreacion);
                    } else {
                        //Muestra el error del prepare vacio
                    }

                } catch (Exception ex) {
                    return false;
                }
            case PROYECTOS:
                Proyecto nuevoItem = (Proyecto) item;

                PreparedStatement cadenaCreacion = controlerSql.getPrepare("INSERT INTO `4dbset`.`proyecto`\n" +
                        "(`id`,\n" +
                        "`fechaAlta`,\n" +
                        "`fechaBaja`,\n" +
                        "`nombre`,\n" +
                        "`fechaInicio`,\n" +
                        "`estado`)\n" +
                        "VALUES\n" +
                        "(?,\n" +
                        "?,\n" +
                        "?,\n" +
                        "?,\n" +
                        "?,\n" +
                        "?);");
                try {
                    if (cadenaCreacion != null) {
                        cadenaCreacion.setInt(1, nuevoItem.getId());

                        controlerSql.ejecutar(cadenaCreacion);
                    } else {
                        //Muestra el error del prepare vacio
                    }

                } catch (Exception ex) {
                    return false;
                }
        }*/

        return false;
    }

    @Override
    public boolean modificar(Object item, int indice, Apartados apartado){

        String ident = String.valueOf(indice);

        final String  SQL_UPDATE_PERSONAL = "UPDATE `persona` WHERE idPersona = '" + ident + "';";
        //*a La tabla Personal le falta un id Delegación.
        final String  SQL_UPDATE_DELEGACION = "UPDATE `delegacion` WHERE id = '" + ident + "';";
        final String  SQL_UPDATE_PROYECTO = "UPDATE `proyecto WHERE id = '" + ident + "';";
        final String  SQL_UPDATE_USUARIO = "UPDATE `usuario WHERE id = '" + ident + "';";

        //Si os fijais le he quitado el nombre de la base de datos a las consultas por que es redundante, ya que en la conexión
        //Ya especificamos el nombre de la base de datos a la que nos hemos conectados y la utilidad de conexión ya esta conectada a la base de datos.

        //Creación de variables dependiendo del apartado:
        String cadenaSql;

        switch (apartado) {
            case PERSONAL:      cadenaSql=SQL_UPDATE_PERSONAL;break;
            case PROYECTOS:     cadenaSql=SQL_UPDATE_DELEGACION;break;
            case DELEGACIONES:  cadenaSql=SQL_UPDATE_PROYECTO;break;
            case USUARIOS:      cadenaSql=SQL_UPDATE_USUARIO;break;
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
                        cadenaCreacion.setInt(1, ((Personal)item).getId());
                        break;
                    case PROYECTOS:
                        //TODO
                        cadenaCreacion.setInt(1, ((Proyecto)item).getId());
                        break;
                    case DELEGACIONES:
                        //TODO
                        cadenaCreacion.setInt(1, ((Delegacion)item).getId());
                        break;
                    case USUARIOS:
                        //TODO
                        cadenaCreacion.setInt(1, ((Usuario)item).getId());
                        break;
                    default:
                        return false;
                }

                //Ejecugamos la sentencia
                if(controlerSql.ejecutar(cadenaCreacion) > 0)
                    return true;
                else
                    return false;

            } else {
                //Muestra el error del prepare vacio
            }

        } catch (Exception ex) {
            return false;
        }

        return false;
    }


    @Override
    public boolean borrar(int indice, Apartados apartado) {

        String ident = String.valueOf(indice);

        final String  SQL_DELETE_PERSONAL = "DELETE `persona` WHERE idPersona = '" + ident + "';";
        //*a La tabla Personal le falta un id Delegación.
        final String  SQL_DELETE_DELEGACION = "DELETE `delegacion` WHERE id = '" + ident + "';";
        final String  SQL_DELETE_PROYECTO = "DELETE `proyecto WHERE id = '" + ident + "';";
        final String  SQL_DELETE_USUARIO = "DELETE `usuario WHERE id = '" + ident + "';";

        //Creación de variables dependiendo del apartado:
        String cadenaSql;

        switch (apartado) {
            case PERSONAL:      cadenaSql=SQL_DELETE_PERSONAL;break;
            case PROYECTOS:     cadenaSql=SQL_DELETE_DELEGACION;break;
            case DELEGACIONES:  cadenaSql=SQL_DELETE_PROYECTO;break;
            case USUARIOS:      cadenaSql=SQL_DELETE_USUARIO;break;
            default:
                return false;
        }

        // TODO

        PreparedStatement cadenaCreacion = controlerSql.getPrepare(cadenaSql);

 /*       try {
            st.setString(1,idPersona);
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
*/
        return false;
    }


    @Override
    public boolean Login(Usuario user) {
        return false;
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
        getPilaDatosGenerales();
        return pilaDatosGenerales;
    }

    private boolean recoger(Apartados apartados){
        String sql;

        switch(apartados){
            case PERSONAL:      sql="SELECT * FROM 4dbset.personal";    break;
            case DELEGACIONES:  sql="SELECT * FROM 4dbset.delegacion";    break;
            case PROYECTOS:     sql="SELECT * FROM 4dbset.proyectos;";    break;
            case USUARIOS:      sql="SELECT * FROM 4dbset.usuarios;";    break;
            default:return false;
        }


        try{
            PreparedStatement sqlConection = controlerSql.getConecction().prepareStatement(sql);

            if(sqlConection == null){
                mensajeError = controlerSql.getErrores();
                existeError = true;
                return false;
            }


            ResultSet rs = sqlConection.executeQuery();

            while(rs.next())
                if(create(rs,apartados) != null)
                    switch(apartados){
                        case PERSONAL:       pilaDatosGenerales.getPersonal().add((Personal)create(rs,apartados));         break;
                        case DELEGACIONES:   pilaDatosGenerales.getDelegaciones().add((Delegacion) create(rs,apartados));  break;
                        case PROYECTOS:      pilaDatosGenerales.getProyectos().add((Proyecto) create(rs,apartados));       break;
                        case USUARIOS:       pilaDatosGenerales.getUsuarios().add((Usuario) create(rs,apartados));         break;
                        default:return false;
                    }
            return true;

        }catch (Exception e){
            mensajeError =e;
            existeError = true;
            return false;
        }finally {
            controlerSql.close();
        }
    }

    //Crea un objeto partiendo de los datos recogidos de la BD.
    private Object create(ResultSet rs, Apartados apartado) throws SQLException {
        Object element;

        switch (apartado){
            case PERSONAL:
                    element = new Personal();
                    ((Personal)element).setEstado(rs.getBoolean("estado"));
                    ((Personal)element).setId(rs.getInt("idPersonal"));
                break;

            case DELEGACIONES:
                    element = new Delegacion();
                    ((Delegacion)element).setId(rs.getInt("idDelegacion"));
                    ((Delegacion)element).setNombre(rs.getString("Nombre"));
                    ((Delegacion)element).setDireccion(rs.getString("Dirección"));
                    ((Delegacion)element).setTelefono(rs.getString("Telefono"));
                break;

            case PROYECTOS:
                    element = new Proyecto();
                    ((Proyecto)element).setId(rs.getInt("idProyectos"));
                    ((Proyecto)element).setNombre(rs.getString("Nombre"));
                break;

            default:return null;
        }

        return element;
    }
}
