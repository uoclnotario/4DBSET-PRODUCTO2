package dao;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


public class SqlController {
    private String cadenaConexion = "";
    private final String driverMysql = "com.mysql.jdbc.Driver";
    private String dbName;
    private String user;
    private String pass;
    private Exception errores;
    private Connection conn = null;

    public SqlController(String host,String port,String dbName, String user, String pass){
        this.user= user;
        this.pass= pass;
        this.dbName = dbName;

        this.cadenaConexion=String.format("jdbc:mysql://%s:%s/%s?useSSL=false", host, port, dbName);
        compobarDBname(host,port);


    }

    public Object getValNull(Date val){
        if(val == null) return "'DATENULL'";
        return val;
    }



    public Exception getErrores() {
        return errores;
    }
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
                        System.out.println(e);
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


    public int ejecutar(String sqlString, ArrayList<Object> valores, boolean manualCommit, boolean commitFinall, boolean obtenerId){
        try{

            if(!manualCommit){

                Class.forName(driverMysql);
                conn = DriverManager.getConnection(cadenaConexion,user,pass);
            }else{
                if(conn == null) {
                    Class.forName(driverMysql);
                    conn = DriverManager.getConnection(cadenaConexion, user, pass);
                }

                if(conn.isClosed()){
                    Class.forName(driverMysql);
                    conn = DriverManager.getConnection(cadenaConexion,user,pass);
                }

                if(conn.getAutoCommit()){
                    conn.setAutoCommit(false);
                }
            }


            if(!conn.isClosed()){
                    try (PreparedStatement stmt = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS)) {

                        //Se recorre el bucle y se inserta el tipo de valor.
                        int index = 1;

                        for(Object e:valores){
                            if(e instanceof Integer){
                                stmt.setInt(index,(Integer)e);
                            }else if(e instanceof String){
                                //Si es DATE NULL
                                if(e.equals("'DATENULL'"))
                                    stmt.setNull(index, Types.DATE);
                                else
                                    stmt.setString(index,(String)e);

                            }else if(e instanceof Boolean){
                                stmt.setBoolean(index,(Boolean)e);
                            }else if(e instanceof Float) {
                                stmt.setFloat(index, (Float)e);
                            } else if(e instanceof java.sql.Date) {
                                stmt.setDate(index,(Date)e);
                            }else{
                                stmt.setNull(index,0);
                            }
                            index++;
                        }



                        if(obtenerId){
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
                        }else{
                            return stmt.executeUpdate();
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
                    if (conn != null){

                        if(commitFinall)
                            conn.commit();

                        if(!manualCommit ||(manualCommit && commitFinall))
                            conn.close();

                    }

            }catch (SQLException e){
                //Fallo al cerrar la conexión.
                System.out.println(e.getMessage());
                errores = e;
                return -1;
            }
        }
        return -1;
    }



    public int ejecutar(PreparedStatement stmt){
        try{
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
    }

    public  void realizarRoolback(){
        try{
            conn.rollback();
        }catch (Exception ex){

        }
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

    public boolean compobarDBname(String host,String port){
        try{
            Class.forName(driverMysql);
            conn = DriverManager.getConnection(cadenaConexion,user,pass);
            if(!conn.isClosed()){
                return  true;
            }else {
                return false;
            }
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (SQLException throwables)
                {
                       System.out.println(throwables.getErrorCode() + " mensaje:" + throwables.getMessage());
                       if(throwables.getErrorCode() == 1049)
                           return createDBName(host,port);

                        return false;
                }
    }


    private boolean createDBName(String host,String port){

        this.cadenaConexion=String.format("jdbc:mysql://%s:%s/?useSSL=false", host, port);
        this.user= user;
        this.pass= pass;

        update("CREATE DATABASE 4DBSET;");
        this.cadenaConexion=String.format("jdbc:mysql://%s:%s/%s?useSSL=false", host, port, dbName);
        this.user= user;
        this.pass= pass;
        this.dbName = dbName;

        update("CREATE TABLE IF NOT EXISTS `DELEGACION` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `nombre` VARCHAR(45) NOT NULL,\n" +
                        "  `direccion` VARCHAR(150) NULL,\n" +
                        "  `telefono` VARCHAR(9) NULL,\n" +
                        "  PRIMARY KEY (`id`))\n" +
                        "ENGINE = InnoDB;" );

        update("CREATE TABLE IF NOT EXISTS `PERSONA` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `tipoPersona` VARCHAR(45) NOT NULL,\n" +
                        "  `nif_dni` VARCHAR(45) NOT NULL,\n" +
                        "  `nombre` VARCHAR(45) NOT NULL,\n" +
                        "  `fechaNacimiento` DATE NULL,\n" +
                        "  `domicilio` VARCHAR(45) NULL,\n" +
                        "  PRIMARY KEY (`id`))\n" +
                        "ENGINE = InnoDB;" );

        update("CREATE TABLE IF NOT EXISTS `PROYECTOS` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `fechaAlta` DATE NOT NULL,\n" +
                "  `fechaBaja` DATE NOT NULL,\n" +
                "  `nombre` VARCHAR(45) NOT NULL,\n" +
                "  `fechaDeInicio` DATE NOT NULL,\n" +
                "  `estado` BIT(1) NOT NULL,\n" +
                "  `idDelegacion` INT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `fk_PROYECTOS_DELEGACION1_idx` (`idDelegacion` ASC) VISIBLE,\n" +
                "  CONSTRAINT `fk_PROYECTOS_DELEGACION1`\n" +
                "    FOREIGN KEY (`idDelegacion`)\n" +
                "    REFERENCES `DELEGACION` (`id`)\n" +
                "    ON DELETE NO ACTION\n" +
                "    ON UPDATE NO ACTION)\n" +
                "ENGINE = InnoDB;");

        update( "CREATE TABLE IF NOT EXISTS `PERSONAL` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `fechaAlta` DATE NOT NULL,\n" +
                "  `fechaBaja` DATE NULL,\n" +
                "  `estado` TINYINT NOT NULL,\n" +
                "  `delegacion` INT NULL,\n" +
                "  `proyecto` INT NULL,\n" +
                "  `idDelegacion` INT NULL,\n" +
                "  `idPersona` INT NULL,\n" +
                "  `idProyecto` INT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `fk_PERSONAL_DELEGACION1_idx` (`idDelegacion` ASC) VISIBLE,\n" +
                "  INDEX `fk_PERSONAL_PERSONA1_idx` (`idPersona` ASC) VISIBLE,\n" +
                "  INDEX `fk_PERSONAL_PROYECTOS1_idx` (`idProyecto` ASC) VISIBLE,\n" +
                "  CONSTRAINT `fk_PERSONAL_DELEGACION1`\n" +
                "    FOREIGN KEY (`idDelegacion`)\n" +
                "    REFERENCES `DELEGACION` (`id`)\n" +
                "    ON DELETE SET NULL\n" +
                "    ON UPDATE SET NULL,\n" +
                "  CONSTRAINT `fk_PERSONAL_PERSONA1`\n" +
                "    FOREIGN KEY (`idPersona`)\n" +
                "    REFERENCES `PERSONA` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE,\n" +
                "  CONSTRAINT `fk_PERSONAL_PROYECTOS1`\n" +
                "    FOREIGN KEY (`idProyecto`)\n" +
                "    REFERENCES `PROYECTOS` (`id`)\n" +
                "    ON DELETE SET NULL\n" +
                "    ON UPDATE SET NULL)\n" +
                "ENGINE = InnoDB;");

        update("CREATE TABLE IF NOT EXISTS `CONTRATADOS` (\n" +
                "  `idPersonal` INT NOT NULL,\n" +
                "  `tipoContrato` VARCHAR(45) NULL,\n" +
                "  `salario` FLOAT NULL,\n" +
                "  PRIMARY KEY (`idPersonal`),\n" +
                "  INDEX `fk_CONTRATADOS_PERSONAL1_idx` (`idPersonal` ASC) VISIBLE,\n" +
                "  CONSTRAINT `fk_CONTRATADOS_PERSONAL1`\n" +
                "    FOREIGN KEY (`idPersonal`)\n" +
                "    REFERENCES `PERSONAL` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB;\n");
        update("CREATE TABLE IF NOT EXISTS `COLABORADORES` (\n" +
                "  `idPersonal` INT NOT NULL,\n" +
                "  `tipoColaboracion` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`idPersonal`),\n" +
                "  CONSTRAINT `fk_COLABORADORES_PERSONAL1`\n" +
                "    FOREIGN KEY (`idPersonal`)\n" +
                "    REFERENCES `PERSONAL` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB;\n");

        update("CREATE TABLE IF NOT EXISTS `VOLUNTARIOS` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `idPersonal` INT NOT NULL,\n" +
                "  `areaVoluntariado` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`id`, `idPersonal`),\n" +
                "  INDEX `fk_VOLUNTARIOS_PERSONAL1_idx` (`idPersonal` ASC) VISIBLE,\n" +
                "  CONSTRAINT `fk_VOLUNTARIOS_PERSONAL1`\n" +
                "    FOREIGN KEY (`idPersonal`)\n" +
                "    REFERENCES `PERSONAL` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB;");

        update( "CREATE TABLE IF NOT EXISTS `VOLUNTARIOS INTERNACIONALES` (\n" +
                "  `volunariosId` INT NOT NULL,\n" +
                "  `pais` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`volunariosId`),\n" +
                "  CONSTRAINT `fk_VOLUNTARIOS INTERNACIONALES_VOLUNTARIOS1`\n" +
                "    FOREIGN KEY (`volunariosId`)\n" +
                "    REFERENCES `VOLUNTARIOS` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE)\n" +
                "ENGINE = InnoDB;");

        update("CREATE TABLE IF NOT EXISTS `USUARIOS` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `nombre` VARCHAR(45) NOT NULL,\n" +
                "  `tipoUsuario` INT NOT NULL,\n" +
                "  `hashing` VARCHAR(20) NOT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB;");


        return true;
    }
}
