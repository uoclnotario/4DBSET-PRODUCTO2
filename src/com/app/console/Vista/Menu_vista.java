package com.app.console.Vista;

import dao.IDao;
import logicaEmpresarial.Usuario;



public class Menu_vista {

    public String MostrarMenu(Usuario user, String salir) {

        int valorRecogido = 0;
        System.out.println("---SELECCIONE UN APARTADO ---");
        System.out.println("\t-1\t PROYECTOS ");
        System.out.println("\t-2\t PERSONAL ");
        System.out.println("\t-3\t DELEGACIONES ");

        if(user.getRol() == Usuario.tipoUsuarios.ADMINISTRADOR)
            System.out.println("\t-4\t USUARIOS ");

        System.out.println("Escriba el indice del apartado o "+ salir + " para cerrar sesión:");
        return FuncionesConsola.leerConsola();
    }
    public void MostrarErrorEntrada(int minimo, int maximo, String salir){
        System.out.println("El valor introducido no es correcto, debe de introducir de "+minimo+" A "+ maximo);
        System.out.println("o escriba" + salir +" para cerrar la sesión.");
    }



    public void DespedirUsuario(Usuario user){
        System.out.println("Adios "+user.getNombre());
    }

    public void mensajeElementoCreado(boolean elementoCreado, IDao dao){

        if(elementoCreado){
            System.out.println("Se ha creado correctamente");
        }else{
            if(dao.existeUnError())
                if(dao.getMensajeError() != null)
                    System.out.println("Se ha producido un error y no se ha podido guardar información:"+dao.getMensajeError());
                else
                    System.out.println("Se ha producido un error y no se ha podido guardar información:Razones desconocidas...");
            else
                System.out.println("No se ha creado, debido a que se ha cancelado.");
        }

    }
    public void mensajeElementoEditado(boolean elementoCreado){

        if(elementoCreado){
            System.out.println("Se ha Modificado correctamente");
        }else{
            System.out.println("No se ha podido modificar.");
        }

    }
    public void mensajeError(String error){
        System.out.println("Se ha producido un error: "+error);
        System.out.println("La aplicación no pudec continuar.");
    }
    public boolean preguntarBorrar(){
        System.out.println("¿Seguro que desea borrar?:escriba Si/s No/n");
        return  FuncionesConsola.leerConsolaSiNo();
    }
    public void mensajeBorrar(boolean borrado){
        if(borrado){
            System.out.println("Se ha borrado correctamente");
        }else{
            System.out.println("No se ha borrado");
        }
    }

    //Pide por consola usuario y password.
    public Object PedirCredenciales(){
        String entradaUser,entradaPass;
        System.out.println("Introduce el nombre de usuario:");
        entradaUser = FuncionesConsola.leerConsola();

        System.out.println("Introduzca la contraseña:");
        entradaPass= FuncionesConsola.leerConsola();


        return new Usuario(entradaUser,entradaPass, Usuario.tipoUsuarios.USUARIO);
    }

    public void MostrarBienvenida(Usuario user){
        System.out.println("Bienvenido "+user.getNombre());
        if(user.getNombre().equals("DefaultAdmin")){
            System.out.println("Este usuario es el de por defecto, tiene privilegios de administrador");
            System.out.println("Cree un usuario con privilegios de administrador para gestionar la apliación");
        }
    }
    public void MostrarErrorLoggin(){
        System.out.println("El usuario o la clave introducidos no son correctos");
    }

    public void MostrarError(String Error){
            System.out.println(Error);
    }
}
