package com.app.console;

import com.app.console.Vista.*;
import dao.DaoXML;
import logicaEmpresarial.*;

import java.awt.desktop.SystemEventListener;

public class AppConsole {

    private  final String PALABRAPARAVOLVER = "v";
    private  Usuario usuarioAutentificado;
    private Menu_vista vistaMenu;

    private DaoXML modelo;//Modelo seleccionado por el usuario en el que se encuentra actualmente la aplicacion.
    private Vista vista;//Vista seleccionada por el usuario en el que se encuenta actualmente al acpliacion.
    private Ong pilaDatos;


    public AppConsole(){
        pilaDatos=new Ong();
        vistaMenu = new Menu_vista();
        modelo = new DaoXML(pilaDatos);
        run();
    }

    private void run() {
        boolean userLogueado = false;

        //Inicio aplicacion.
        Usuario_vista VistaLogin=new Usuario_vista();    //Vista para loguear.


        do {
            do {
                usuarioAutentificado = (Usuario) VistaLogin.PedirCredenciales();
                if (modelo.Login(usuarioAutentificado)) {
                    //El usuario se ha logueado correctamente.
                    VistaLogin.MostrarBienvenida(usuarioAutentificado);
                    userLogueado = true;
                } else {
                    //Error el usuario o la clave no es correcto.
                    VistaLogin.MostrarError();
                }
            } while (!userLogueado);//Hasta que el usuario no se haya logueado no continuamos.


            //Mostrar menu
            while (MostrarMenu()) ;
            userLogueado = false;
            usuarioAutentificado = null;
        }while(true);//Bucle infinito, la aplicación si el usuario quiere salir, debera de cerrar la ventana.

    }

    private boolean  MostrarMenu(){

        String entradaUsuario = vistaMenu.MostrarMenu(usuarioAutentificado,PALABRAPARAVOLVER);
        int accesoApartado,minimo,maximo;

        minimo=1;
        if(usuarioAutentificado.getRol() == Usuario.tipoUsuarios.ADMINISTRADOR){
            maximo=1;
        }else{
            maximo=2;
        }

        switch (FuncionesConsola.comprobarEntrada(entradaUsuario,
                FuncionesConsola.MASCARANUMERO,
                PALABRAPARAVOLVER,
                FuncionesConsola.comprobaConversion.ENTERO)){
            case TRUE:
                accesoApartado = Integer.parseInt(entradaUsuario);
                //Comprobar que el usuario ha marcado un valor valido
                if(accesoApartado <=0 || accesoApartado>Apartados.values().length)
                {
                    vistaMenu.MostrarErrorEntrada(minimo,maximo,PALABRAPARAVOLVER);
                    return true;//Retorna true y se volverá a abrir el menu de nuevo
                }

                //Comprobar que si el usuario no tiene permiso para entrar en este apartado
                if(accesoApartado == 2)
                {
                    vistaMenu.MostrarErrorEntrada(minimo,maximo,PALABRAPARAVOLVER);
                    return true;//Retorna true y se volvera a repetir el menu.
                }

                while(AbrirApartado(Apartados.intToApartados(accesoApartado)));
                return true;//Cuando el usuario quiera volver al menu desde apartado, lanzamos true para volver a mostrar el menu.

            case FALSE:
                vistaMenu.MostrarErrorEntrada(minimo,maximo,PALABRAPARAVOLVER);
                return true;//Retorna true y se volvera a repetir el menu.
            case EXIT:
                return false;
            default:
                return true;//Esto es imposible pero por si acaso si se produce que vuelva a mostrar el menu.
        }

    }

    private boolean  AbrirApartado(Apartados apartados){


        //Cargar modelo factory y llamar a visualizar
         vista = getVista(apartados);

         modelo.descargaDatos();




        //Mostramos el listado y recibimos que desea hacer el usuario.
         String entradaUsuario = vista.MostrarLIstado(modelo.recogerLIstado(apartados),PALABRAPARAVOLVER,usuarioAutentificado);
         int indiceSeleccionado;

        //Mientras el usuario quiera permanecer dentro del item repetimos.
        switch (FuncionesConsola.comprobarEntrada(entradaUsuario,
                FuncionesConsola.MASCARANUMERO,
                PALABRAPARAVOLVER,
                FuncionesConsola.comprobaConversion.ENTERO))
        {
            case TRUE:
                indiceSeleccionado = Integer.parseInt(entradaUsuario);
                if(indiceSeleccionado < 0 || indiceSeleccionado > modelo.recogerLIstado(apartados).size())
                {
                    vistaMenu.MostrarErrorEntrada(0,modelo.recogerLIstado(apartados).size(),PALABRAPARAVOLVER);
                    return true;
                }

                if(indiceSeleccionado == 0){//Si es cero se Creara un nuevo elemento, por lo que llamamos a Crear.
                   //Aqui se podria añadir una restricción para que si el elemento ya existe de une error y no se cree.
                    vistaMenu.mensajeElementoCreado(modelo.crear(vista.Crear(pilaDatos,"CANCELAR"),apartados));

                }else{//De lo contrario llama a mostrar uno.
                    while(MostarUno(indiceSeleccionado-1,apartados));//Abirmos mostrar uno pasandole el apartado seleccionado -1.
                }
                break;
            case FALSE:
                vistaMenu.MostrarErrorEntrada(0,modelo.recogerLIstado(apartados).size(),PALABRAPARAVOLVER);
                return true;
            case EXIT:
                return false;
        }
        return true;
    }

    private  boolean MostarUno(int elemento,Apartados apartado){
        //Mostramos el elemento y recogemos que desea hacer el usuario.
        String entradaUsuario = vista.MostrarUno(modelo.recogerLIstado(apartado).get(elemento),PALABRAPARAVOLVER,usuarioAutentificado);
        int apartadoSeleccionado;

        switch (FuncionesConsola.comprobarEntrada(entradaUsuario,
                FuncionesConsola.MASCARANUMERO,
                PALABRAPARAVOLVER,
                FuncionesConsola.comprobaConversion.ENTERO))
        {
            case TRUE:
                    apartadoSeleccionado = Integer.parseInt(entradaUsuario);
                    if(apartadoSeleccionado < 0 || apartadoSeleccionado >1)
                    {
                        vistaMenu.MostrarErrorEntrada(0,modelo.recogerLIstado(apartado).size(),PALABRAPARAVOLVER);
                        return true;
                    }

                    if(apartadoSeleccionado == 0){//Si es cero se Modificará
                        //Carga la vista, se lo envia al modelo y muestra un mensaje si se ha realizado correctamente.
                        vistaMenu.mensajeElementoEditado(modelo.modificar(vista.Modificar(pilaDatos, elemento,"CANCELAR"), elemento));
                    }else{//De lo contrario llama a Borrar

                    }
                break;
            case FALSE:
                    vistaMenu.MostrarErrorEntrada(0,modelo.recogerLIstado(apartado).size(),PALABRAPARAVOLVER);
                return true;
            case EXIT:
                return false;
        }
        return true;
    }


    private  Vista getVista(Apartados apartado){
        switch (apartado){
            case PERSONAL: return new Personal_vista();
            default:return  null;
        }
    }


}