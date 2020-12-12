package dao;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;


public class SqlController {
    private String cadenaConexion = "";
    private final String driverMysql = "com.mysql.jdbc.Driver";
    private String dbName;
    private String user;
    private String pass;
    private Exception errores;
    private Connection conn = null;

    public SqlController(String host,String port,String dbName, String user, String pass){
        this.cadenaConexion=String.format("jdbc:mysql://%s:%s/%s?useSSL=false", host, port, dbName);
        this.user= user;
        this.pass= pass;
        this.dbName = dbName;
    }

    public Exception getErrores() {
        return errores;
    }

    /* PARA IMPEDIR EL INJECTION SQL HAY QUE SUSTITUIR LOS STRING DE CADENA POR PREPARESTATMENT.
    *
    * */

    public boolean update(String sqlString){
        Connection conn = null;
        try{
            Class.forName(driverMysql);
            conn = DriverManager.getConnection(cadenaConexion,user,pass);
            if(!conn.isClosed()){
                try (PreparedStatement stmt = conn.prepareStatement(sqlString)) {
                        stmt.execute();
                        return true;
                    }catch (Exception e) {
                    errores = e;
                    return false;
                }
            }

        }catch ( Exception e){
            //Error de conexión
            System.out.println(e);
            errores = e;
            return false;
        }finally{
            try {
                if (conn != null)
                    conn.close();

            }catch (SQLException e){
                //Fallo al cerrar la conexión.
                System.out.println(e.getMessage());
                errores = e;
                return false;
            }
        }
        return false;
    }

    public  PreparedStatement getPrepare(String sqlString){
        try{
            Class.forName(driverMysql);
            conn = DriverManager.getConnection(cadenaConexion,user,pass);

            if(!conn.isClosed()){
                try (PreparedStatement stmt = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS)) {
                  return stmt;

                }catch (Exception e){
                    errores = e;
                    return  null;
                }
            }
        }catch ( Exception e){
            //Error de conexión
            System.out.println(e);
            errores = e;
            return null;
        }finally{
            try {
                if (conn != null)
                    conn.close();

            }catch (SQLException e){
                //Fallo al cerrar la conexión.
                System.out.println(e.getMessage());
                errores = e;
                return null;
            }
        }
        return null;


    }


    //Retorna -1 en caso de que ocurra un error o no se genere la cadena.
    public int ejecutar(String sqlString, ArrayList<Object> valores){
        try{
            Class.forName(driverMysql);
            conn = DriverManager.getConnection(cadenaConexion,user,pass);
            Class.forName(driverMysql);
            conn = DriverManager.getConnection(cadenaConexion,user,pass);


            if(!conn.isClosed()){
                    try (PreparedStatement stmt = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS)) {

                        //Se recorre el bucle y se inserta el tipo de valor.
                        int index = 1;
                        for(Object e:valores){
                            if(e instanceof Integer){
                                stmt.setInt(index,(Integer)e);
                            }else if(e instanceof String){
                                stmt.setString(index,(String)e);
                            }else if(e instanceof Boolean){
                                stmt.setBoolean(index,(Boolean)e);
                            }else if(e instanceof Float) {
                                stmt.setFloat(index, (Float) e);
                            }else if(e instanceof Date) {
                                stmt.setDate(index,(Date) e);
                            }else{
                                //Deberiamos de tirar errro por que algo falla;
                                stmt.setNull(index,0);
                            }
                            index++;
                        }


                        if(stmt.executeUpdate() > 0 ){
                            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    System.out.println(generatedKeys.getInt(1));
                                    return generatedKeys.getInt(1);
                                }
                                else {
                                    throw new SQLException("Creating user failed, no ID obtained.");
                                }
                            }

                        }else{
                            errores = new Exception("No se creo ningun elemento");
                            return -1;
                        }

                    }catch (Exception e){
                        errores = e;
                        return -1;
                    }
            }
        }catch ( Exception e){
            //Error de conexión
            System.out.println(e);
            errores = e;
            return -1;
        }finally{
            try {
                if (conn != null)
                    conn.close();

            }catch (SQLException e){
                //Fallo al cerrar la conexión.
                System.out.println(e.getMessage());
                errores = e;
                return -1;
            }
        }
        return -1;
    }

    public Connection getConecction(){
        try{
            Class.forName(driverMysql);
            conn = DriverManager.getConnection(cadenaConexion,user,pass);
            return conn;
        }catch ( Exception e){
            //Error de conexión
            System.out.println(e);
            errores = e;
            return null;
        }
    }

    public void close(){
        try {
            if (conn != null)
                conn.close();

        }catch (Exception e){
            //Fallo al cerrar la conexión.
            System.out.println(e.getMessage());
            errores = e;
        }
    }



}
