package ong.ConsoleApp;

import ong.LogicBunnises.Usuario;

public class Main {
    private static Usuario usuarioAutenticado = new Usuario();


    public static void main(String[] args) {

        //Carga el controlador de usuario para acer login.
        ong.ConsoleApp.Controlador.Usuario ctrlUsers = new ong.ConsoleApp.Controlador.Usuario(usuarioAutenticado);
        System.out.println("Bienvendio a la aplicación.");//Mostramos mensaje de bienvenida.

        //Lanzamos apartada de login.

        if(!ctrlUsers.Login())
            return;//Si el usuario no ha sido capaz de autenticarse, la aplicación se cierra.

        //Aqui mostrariamos el menu que probable sea un controlador hasta que se escriba cancel y se acabe.
        ong.ConsoleApp.Controlador.Menu ctrlMenu = new ong.ConsoleApp.Controlador.Menu();
        ctrlMenu.Inicio(usuarioAutenticado);
    }

}



