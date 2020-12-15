package dao;

import com.app.console.Apartados;
import logicaEmpresarial.*;

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

    @Override
    public boolean descargaDatos(Apartados apartados) {

        pilaDatosGenerales = new Ong();

        if (recoger(Apartados.DELEGACIONES) &&
                recoger(Apartados.PROYECTOS) &&
                recoger(Apartados.PERSONAL) &&
                recoger(Apartados.USUARIOS)) {
            return true;
        }else{
            return false;
        }
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

        final String SQL_INSERT_PERSONAL = "INSERT INTO personal (tipo,idPersona,fechaAlta,fechaBaja,estado,idDelegacion,idProyecto)VALUES(?,?,?,?,?,?,?);";
        final String SQL_INSERT_PERSONA = "INSERT INTO persona (TipoPersona,NIF_DNI,Nombre,FechaNacimiento,Domicilio)VALUES(?,?,?,?,?);";
        final String SQL_INSERT_DELEGACION = "INSERT INTO delegacion(nombre,direccion,telefono)VALUES(?,?,?);";
        final String SQL_INSERT_PROYECTO = "INSERT INTO proyectos(fechaAlta,fechaBaja,nombre,estado,tipo)VALUES(?,?,?,?,?);";
        final String SQL_INSERT_USUARIO = "INSERT INTO usuarios (tipoUsuario,nombre,hashing)VALUES(?,?,?);";

        //Subentidades de PERSONAL
        final String SQL_INSERT_COLABORADORES ="INSERT INTO colaboradores(idPersonal,tipoColaboracion)VALUES(?,?);";
        final String SQL_INSERT_CONTRATADOS = "INSERT INTO contratados(idPersonal,tipoContrato,salario)VALUES(?,?,?);";
        final String SQL_INSERT_VOLUNTARIOS=  "INSERT INTO voluntarios(idPersonal,areaVoluntariado)VALUES(?,?);";
        final String SQL_INSERT_VOLUNTARIOSINTERNACIONALES=  "INSERT INTO `voluntarios internacionales`(`volunariosId`,`pais`)VALUES(?,?);";

        //Seteamos los valores
        ArrayList<Object> valores = new ArrayList<>();
        Integer recogidaId;

            switch (apartado) {
                case PERSONAL:
                    //Inicia transaccion

                    // PERSONA
                    valores = new ArrayList<>();
                    valores.add(((Personal) item).getIntTipo());
                    valores.add(((Personal) item).getNif_dni());
                    valores.add(((Personal) item).getNombre());
                    valores.add(controlerSql.getValNull(convertSqlDate(((Personal) item).getFechaDeNacimiento())));
                    valores.add(((Personal) item).getDomicilio());



                    recogidaId = controlerSql.ejecutar(SQL_INSERT_PERSONA, valores, true, false, true);

                    if (recogidaId <= 0) {
                        controlerSql.realizarRoolback();
                        existeError = true;
                        mensajeError = controlerSql.getErrores();
                        return false;
                    }
                    ((Persona) item).setPersonaId(recogidaId);


                    //Crear datos Personal..
                    valores = new ArrayList<>();
                    valores.add(((Personal) item).getIntTIpo());
                    valores.add(((Personal) item).getPersonaId());
                    valores.add(controlerSql.getValNull(convertSqlDate(((Personal) item).getFechaAlta())));
                    valores.add(controlerSql.getValNull(convertSqlDate(((Personal) item).getFechaBaja())));
                    valores.add(((Personal) item).getEstado());

                    if (((Personal) item).getDelegacion() != null) {
                        valores.add(((Personal) item).getDelegacion().getId());
                    } else {
                        valores.add(null);
                    }

                    if (((Personal) item).getProyecto() != null) {
                        valores.add(((Personal) item).getProyecto().getId());
                    } else {
                        valores.add(null);
                    }


                    Boolean isSubtipo = false;
                    //Verificamos si es de un tipo especifico.
                    if (item.getClass().getName().equals("logicaEmpresarial.Contratados") ||
                            item.getClass().getName().equals("logicaEmpresarial.Contratados") ||
                            item.getClass().getName().equals("logicaEmpresarial.Voluntarios") ||
                            item.getClass().getName().equals("logicaEmpresarial.VoluntariosInternacionales"))
                        isSubtipo = true;


                    recogidaId = controlerSql.ejecutar(SQL_INSERT_PERSONAL, valores, true, !isSubtipo, true);
                    if (recogidaId <= 0) {
                        controlerSql.realizarRoolback();
                        existeError = true;
                        mensajeError = controlerSql.getErrores();
                        return false;
                    }
                    ((Personal) item).setId((recogidaId));

                    if (isSubtipo) {

                        //Creación de datos del tipo de personal en las tablas hijas.
                        switch (item.getClass().getName()) {
                            case "logicaEmpresarial.Contratados":
                                valores = new ArrayList<>();
                                valores.add(((Personal) item).getId());
                                valores.add(((Contratados) item).getTipoContrato());
                                valores.add(((Contratados) item).getSalario());
                                recogidaId = controlerSql.ejecutar(SQL_INSERT_CONTRATADOS, valores, true, true, false);
                                break;
                            case "logicaEmpresarial.Colaboradores":
                                valores = new ArrayList<>();
                                valores.add(((Personal) item).getId());
                                valores.add(((Colaboradores) item).getIntTipoColaboracion());
                                recogidaId = controlerSql.ejecutar(SQL_INSERT_COLABORADORES, valores, true, true, false);
                                break;
                            case "logicaEmpresarial.Voluntarios":
                                valores = new ArrayList<>();
                                valores.add(((Personal) item).getId());
                                valores.add(((Voluntarios) item).getAreaVoluntariado());
                                recogidaId = controlerSql.ejecutar(SQL_INSERT_VOLUNTARIOS, valores, true, true, false);
                                break;
                            case "logicaEmpresarial.VoluntariosInternacionales":
                                valores = new ArrayList<>();
                                valores.add(((Personal) item).getId());
                                valores.add(((Voluntarios) item).getAreaVoluntariado());
                                recogidaId = controlerSql.ejecutar(SQL_INSERT_VOLUNTARIOS, valores, true, false, true);
                                if (recogidaId <= 0) {
                                    controlerSql.realizarRoolback();
                                    existeError = true;
                                    mensajeError = controlerSql.getErrores();
                                    return false;
                                } else {
                                    valores = new ArrayList<>();
                                    valores.add(recogidaId);//id de voluntario
                                    valores.add(((VoluntariosInternacionales) item).getPais());
                                    recogidaId = controlerSql.ejecutar(SQL_INSERT_VOLUNTARIOSINTERNACIONALES, valores, true, true, false);
                                }
                                break;
                        }

                        if (recogidaId <= 0) {
                            controlerSql.realizarRoolback();
                            existeError = true;
                            mensajeError = controlerSql.getErrores();
                            return false;
                        }

                    }

                    //Si nada fallo Retorna ok.
                    return true;
                case USUARIOS:
                    valores.add(((Usuario) item).getIntRol());
                    valores.add(((Usuario) item).getNombre());
                    valores.add(((Usuario) item).getHasing());
                    recogidaId = controlerSql.ejecutar(SQL_INSERT_USUARIO, valores, false, false, true);
                    if (recogidaId <= 0) {
                        controlerSql.realizarRoolback();
                        existeError = true;
                        mensajeError = controlerSql.getErrores();
                        return false;
                    } else {
                        ((Usuario) item).setId(recogidaId);
                        return true;
                    }


                case DELEGACIONES:
                    valores.add(((Delegacion) item).getNombre());//Nombre
                    valores.add(((Delegacion) item).getDireccion());//Dirección
                    valores.add(((Delegacion) item).getTelefono());//Telefono
                    recogidaId = controlerSql.ejecutar(SQL_INSERT_DELEGACION, valores, false, false, true);
                    if (recogidaId <= 0) {
                        controlerSql.realizarRoolback();
                        existeError = true;
                        mensajeError = controlerSql.getErrores();
                        return false;
                    } else {
                        ((Delegacion) item).setId(recogidaId);
                        return true;
                    }

                case PROYECTOS:
                    valores.add(controlerSql.getValNull(convertSqlDate(((Proyecto) item).getFechaAlta())));
                    valores.add(controlerSql.getValNull(convertSqlDate(((Proyecto) item).getFechaBaja())));
                    valores.add(((Proyecto) item).getNombre());
                    valores.add(((Proyecto) item).getEstado());
                    valores.add(((Proyecto) item).getIntTipo());

                    recogidaId = controlerSql.ejecutar(SQL_INSERT_PROYECTO, valores, false, false, true);
                    if (recogidaId <= 0) {
                        controlerSql.realizarRoolback();
                        existeError = true;
                        mensajeError = controlerSql.getErrores();
                        return false;
                    } else {
                        ((Proyecto) item).setId(recogidaId);
                        return true;
                    }
                default:
                    return false;
            }
    }

    @Override
    public boolean modificar(Object item, int indice, Apartados apartado) {
        //TODO FINALIZAR APARTADO MODIFICACIÓN


        final String SQL_UPDATE_PERSONAL = "UPDATE persona INNER JOIN personal on personal.idPersona = persona.id SET persona.tipoPersona = ?,persona.nif_dni = ?,persona.nombre = ?,persona.fechaNacimiento = ?,persona.domicilio = ?, personal.tipo = ?, personal.fechaAlta =?, personal.fechaBaja=?, personal.estado = ?, personal.idDelegacion=?,personal.idProyecto=? WHERE personal.id = ? ;";


        final String SQL_UPDATE_DELEGACION = "UPDATE delegacion SET nombre = ?,direccion = ?,telefono = ? WHERE id = ?;";
        final String SQL_UPDATE_PROYECTO = "UPDATE proyectos SET fechaAlta = ?,fechaBaja = ?,nombre = ?,estado = ?,tipo = ? WHERE id = ?;";
        final String SQL_UPDATE_USUARIO = "UPDATE usuarios SET nombre = ?, tipoUsuario = ?, hashing = ? WHERE id = ?;";

        final String SQL_INSERT_COLABORADORES ="INSERT INTO colaboradores(idPersonal,tipoColaboracion)VALUES(?,?);";
        final String SQL_INSERT_CONTRATADOS = "INSERT INTO contratados(idPersonal,tipoContrato,salario)VALUES(?,?,?);";
        final String SQL_INSERT_VOLUNTARIOS=  "INSERT INTO voluntarios(idPersonal,areaVoluntariado)VALUES(?,?);";
        final String SQL_INSERT_VOLUNTARIOSINTERNACIONALES=  "INSERT INTO `voluntarios internacionales`(`volunariosId`,`pais`)VALUES(?,?);";

        final String SQL_DELETE_CONTRATADOS="DELETE FROM contratados WHERE idPersonal=?";
        final String SQL_DELETE_COLABORADORES="DELETE FROM voluntarios WHERE idPersonal=?";
        final String SQL_DELETE_VOLUNTARIOS="DELETE FROM colaboradores WHERE idPersonal=?";



        //Seteamos los valores
        ArrayList<Object> valores = new ArrayList<>();
        Integer recogidaId;
        String sqlDelete="";
        boolean isSubtipo=false;

        if(item == null)
            return false;

        switch (apartado) {
            case PERSONAL:
                //Recogida perosnal guardado.
                Persona personalAntiguo = pilaDatosGenerales.getPersonal().get(indice);

                valores = new ArrayList<>();
                valores.add(((Personal) item).getIntTipo());
                valores.add(((Personal) item).getNif_dni());
                valores.add(((Personal) item).getNombre());
                valores.add(controlerSql.getValNull(convertSqlDate(((Personal) item).getFechaDeNacimiento())));
                valores.add(((Personal) item).getDomicilio());
                valores.add(((Personal) item).getIntTIpo());
                valores.add(controlerSql.getValNull(convertSqlDate(((Personal) item).getFechaAlta())));
                valores.add(controlerSql.getValNull(convertSqlDate(((Personal) item).getFechaBaja())));
                valores.add(((Personal) item).getEstado());

                if (((Personal) item).getDelegacion() != null) {
                    valores.add(((Personal) item).getDelegacion().getId());
                } else {
                    valores.add(null);
                }

                if (((Personal) item).getProyecto() != null) {
                    valores.add(((Personal) item).getProyecto().getId());
                } else {
                    valores.add(null);
                }
                valores.add(((Personal) item).getId());

                recogidaId = controlerSql.ejecutar(SQL_UPDATE_PERSONAL, valores, true, false, false);
                if (recogidaId <= 0) {
                    controlerSql.realizarRoolback();
                    existeError = true;
                    mensajeError = controlerSql.getErrores();
                    System.out.println("Error al modificar="+mensajeError);
                    return false;
                }



                //Delete de datos antiguos.
                switch (item.getClass().getName()) {
                    case "logicaEmpresarial.Contratados":               isSubtipo = true;sqlDelete=SQL_DELETE_CONTRATADOS;break;
                    case "logicaEmpresarial.Colaboradores":             isSubtipo = true;sqlDelete=SQL_DELETE_COLABORADORES;break;
                    case "logicaEmpresarial.Voluntarios":               isSubtipo = true;sqlDelete=SQL_DELETE_VOLUNTARIOS;break;
                    case "logicaEmpresarial.VoluntariosInternacionales":isSubtipo = true;sqlDelete=SQL_DELETE_VOLUNTARIOS;break;
                }

                if(isSubtipo) {
                    valores = new ArrayList<>();
                    valores.add(((Personal) personalAntiguo).getId());
                    recogidaId = controlerSql.ejecutar(sqlDelete, valores, true, false, false);

                    if (recogidaId < 0) {
                        controlerSql.realizarRoolback();
                        existeError = true;
                        mensajeError = controlerSql.getErrores();
                        return false;
                    }
                }

                //Creación de datos del tipo de personal en las tablas hijas.
                switch (item.getClass().getName()) {
                    case "logicaEmpresarial.Contratados":
                        valores = new ArrayList<>();
                        valores.add(((Personal) item).getId());
                        valores.add(((Contratados) item).getTipoContrato());
                        valores.add(((Contratados) item).getSalario());
                        recogidaId = controlerSql.ejecutar(SQL_INSERT_CONTRATADOS, valores, true, true, false);
                        break;
                    case "logicaEmpresarial.Colaboradores":
                        valores = new ArrayList<>();
                        valores.add(((Personal) item).getId());
                        valores.add(((Colaboradores) item).getIntTipoColaboracion());
                        recogidaId = controlerSql.ejecutar(SQL_INSERT_COLABORADORES, valores, true, true, false);
                        break;
                    case "logicaEmpresarial.Voluntarios":
                        valores = new ArrayList<>();
                        valores.add(((Personal) item).getId());
                        valores.add(((Voluntarios) item).getAreaVoluntariado());
                        recogidaId = controlerSql.ejecutar(SQL_INSERT_VOLUNTARIOS, valores, true, true, false);
                        break;
                    case "logicaEmpresarial.VoluntariosInternacionales":
                        valores = new ArrayList<>();
                        valores.add(((Personal) item).getId());
                        valores.add(((Voluntarios) item).getAreaVoluntariado());
                        recogidaId = controlerSql.ejecutar(SQL_INSERT_VOLUNTARIOS, valores, true, false, true);
                        if (recogidaId <= 0) {
                            controlerSql.realizarRoolback();
                            existeError = true;
                            mensajeError = controlerSql.getErrores();
                            return false;
                        } else {
                            valores = new ArrayList<>();
                            valores.add(recogidaId);//id de voluntario
                            valores.add(((VoluntariosInternacionales) item).getPais());
                            recogidaId = controlerSql.ejecutar(SQL_INSERT_VOLUNTARIOSINTERNACIONALES, valores, true, true, false);
                        }
                        break;
                }

                if (recogidaId <= 0) {
                    controlerSql.realizarRoolback();
                    existeError = true;
                    mensajeError = controlerSql.getErrores();
                    return false;
                }else{
                    pilaDatosGenerales.getPersonal().set(indice,(Personal)item);
                    return true;
                }


            case USUARIOS:
                valores.add(((Usuario) item).getIntRol());
                valores.add(((Usuario) item).getNombre());
                valores.add(((Usuario) item).getHasing());
                valores.add(((Usuario) item).getId());

                recogidaId = controlerSql.ejecutar(SQL_UPDATE_USUARIO, valores, false, false, false);
                if (recogidaId <= 0) {
                    controlerSql.realizarRoolback();
                    existeError = true;
                    mensajeError = controlerSql.getErrores();
                    return false;
                } else {
                    pilaDatosGenerales.getUsuarios().set(indice,(Usuario) item);
                    return true;
                }

            case DELEGACIONES:
                valores.add(((Delegacion) item).getNombre());
                valores.add(((Delegacion) item).getDireccion());
                valores.add(((Delegacion) item).getTelefono());
                valores.add(((Delegacion) item).getId());

                recogidaId = controlerSql.ejecutar(SQL_UPDATE_DELEGACION, valores, false, false, false);
                if (recogidaId <= 0) {
                    controlerSql.realizarRoolback();
                    existeError = true;
                    mensajeError = controlerSql.getErrores();
                    return false;
                } else {
                    pilaDatosGenerales.getDelegaciones().set(indice,(Delegacion) item);
                    return true;
                }

            case PROYECTOS:
                valores.add(controlerSql.getValNull(convertSqlDate(((Proyecto) item).getFechaAlta())));
                valores.add(controlerSql.getValNull(convertSqlDate(((Proyecto) item).getFechaBaja())));
                valores.add(((Proyecto) item).getNombre());
                valores.add(((Proyecto) item).getEstado());
                valores.add(((Proyecto) item).getIntTipo());
                valores.add(((Proyecto)item).getId());

                recogidaId = controlerSql.ejecutar(SQL_UPDATE_PROYECTO, valores, false, false, false);
                if (recogidaId <= 0) {
                    controlerSql.realizarRoolback();
                    existeError = true;
                    mensajeError = controlerSql.getErrores();
                    return false;
                } else {
                    pilaDatosGenerales.getProyectos().set(indice,(Proyecto) item);
                    return true;
                }
            default:
                return false;
        }

    }


    @Override
    public boolean borrar(int indice, Apartados apartado) {



        final String SQL_DELETE_PERSONAL    = "DELETE personal,persona FROM persona LEFT JOIN personal ON persona.id = personal.idPersona where personal.id =";
        final String SQL_DELETE_DELEGACION  = "DELETE FROM delegacion WHERE id =";
        final String SQL_DELETE_PROYECTO    = "DELETE FROM proyecto WHERE id = ";
        final String SQL_DELETE_USUARIO     = "DELETE FROM usuario WHERE id = " ;




        //Creación de variables dependiendo del apartado:
        String cadenaSql;

        switch (apartado) {
            case PERSONAL:
                cadenaSql = SQL_DELETE_PERSONAL + getPilaDatosGenerales().getPersonal().get(indice).getId();
                break;
            case DELEGACIONES:
                cadenaSql = SQL_DELETE_DELEGACION  + getPilaDatosGenerales().getDelegaciones().get(indice).getId() ;
                break;
            case  PROYECTOS:
                cadenaSql = SQL_DELETE_PROYECTO +  getPilaDatosGenerales().getProyectos().get(indice).getId();
                break;
            case USUARIOS:
                cadenaSql = SQL_DELETE_USUARIO  + getPilaDatosGenerales().getUsuarios().get(indice).getId();
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
       String sql = "SELECT * FROM usuarios WHERE hashing = ?";

       try{
           ps = controlerSql.getConecction().prepareStatement(sql);
           ps.setString(1, user.getHasing());
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
                sql = "SELECT * FROM vistapersonal";
                break;
            case DELEGACIONES:
                sql = "SELECT * FROM delegacion";
                break;
            case PROYECTOS:
                sql = "SELECT * FROM proyectos;";
                break;
            case USUARIOS:
                sql = "SELECT * FROM usuarios;";
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
                switch (rs.getInt("tipo")){
                    case 1:
                        element=new Colaboradores();
                        ((Colaboradores) element).setIntTipoColaboracion(rs.getInt("tipoColaboracion"));
                    break;
                    case 2:element=new Contratados();
                        ((Contratados) element).setTipoContrato(rs.getString("tipoContrato"));
                        ((Contratados) element).setSalario(rs.getFloat("salario"));
                    break;
                    case 3:element=new Voluntarios();
                        ((Voluntarios) element).setAreaVoluntariado(rs.getString("areaVoluntariado"));
                    break;
                    case 4:element=new VoluntariosInternacionales();
                        ((Voluntarios) element).setAreaVoluntariado(rs.getString("areaVoluntariado"));
                        ((VoluntariosInternacionales) element).setPais(rs.getString("pais"));
                    break;
                    default:
                        element=new Personal();
                    break;
                }

                ((Personal) element).setId(rs.getInt("id"));
                ((Personal) element).setEstado(rs.getBoolean("estado"));


                //Recogida de datos de persona.
                ((Personal) element).setPersonaId(rs.getInt("idPersona"));
                ((Personal) element).setNif_dni(rs.getString("nif_dni"));
                ((Personal) element).setNombre(rs.getString("nombre"));
                ((Personal) element).setFechaDeNacimiento(rs.getDate("fechaNacimiento"));
                ((Personal) element).setDomicilio(rs.getString("domicilio"));
                ((Personal) element).setIntTipo(rs.getInt("tipo"));

                //Recogida datos de Personal.
                ((Personal) element).setFechaAlta(rs.getDate("fechaAlta"));
                ((Personal) element).setFechaBaja(rs.getDate("fechaBaja"));

                Integer idDelegacion = rs.getInt("idDelegacion");
                if(idDelegacion > 0 &&  getPilaDatosGenerales().getDelegaciones().size() > 0){
                    for(Delegacion del: getPilaDatosGenerales().getDelegaciones()){
                        if(idDelegacion == del.getId()){
                            ((Personal)element).setDelegacion(del);
                            //del.getPersonal().add((Personal) element);
                            break;
                        }
                    }

                }

                Integer idProyecto = rs.getInt("idProyecto");
                if(idProyecto > 0 &&  getPilaDatosGenerales().getProyectos().size() > 0){
                    for(Proyecto pro: getPilaDatosGenerales().getProyectos()){
                        if(idProyecto == pro.getId()){
                            ((Personal)element).setProyecto(pro);
                            break;
                        }
                    }

                }



                break;

            case DELEGACIONES:
                element = new Delegacion();
                ((Delegacion) element).setId(rs.getInt("id"));
                ((Delegacion) element).setNombre(rs.getString("nombre"));
                ((Delegacion) element).setDireccion(rs.getString("direccion"));
                ((Delegacion) element).setTelefono(rs.getString("telefono"));
                break;

            case PROYECTOS:

                switch (rs.getInt("tipo")){
                    case 0:element = new Nacional();break;
                    case 1:element= new Internacional();break;
                    default:element = new Proyecto();break;
                }


                ((Proyecto) element).setId(rs.getInt("id"));
                ((Proyecto) element).setNombre(rs.getString("Nombre"));
                ((Proyecto) element).setFechaAlta(rs.getDate("fechaAlta"));
                ((Proyecto) element).setFechaBaja(rs.getDate("fechaBaja"));

                break;

            case USUARIOS:
                element = new Usuario();
                ((Usuario) element).setId(rs.getInt("id"));
                ((Usuario) element).setIntRol(rs.getInt("tipoUsuario"));
                ((Usuario) element).setNombre(rs.getString("nombre"));
                ((Usuario) element).setHasing(rs.getString("hashing"));
                break;

            default:
                return null;
        }

        return element;
    }

    private static java.sql.Date convertSqlDate(java.util.Date uDate) {
        if(uDate == null){
            return null;
        }
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }


}
