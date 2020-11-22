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
            recoger(Apartados.PERSONAL)){


            //TODO RECORRER PERSONAL Y ANIDAR RELACIONES.

            return true;
        }

        return false;
    }

    @Override
    public List recogerLIstado(Apartados apartado) {
        return null;
    }

    @Override
    public boolean crear(Object item, Apartados apartado) {
        return false;
    }

    @Override
    public boolean modificar(Object item, int indice, Apartados apartado) {
        return false;
    }

    @Override
    public boolean borrar(int indice, Apartados apartado) {
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
        return null;
    }

    private boolean recoger(Apartados apartados){
        String sql;

        switch(apartados){
            case PERSONAL:      sql="SELECT * FROM 4dbset.personal";    break;
            case DELEGACIONES:  sql="SELECT * FROM 4dbset.delegacion";    break;
            case PROYECTOS:     sql="SELECT * FROM 4dbset.proyectos;";    break;
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
