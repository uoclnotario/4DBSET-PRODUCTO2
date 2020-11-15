package com.app.console;

import com.app.console.Vista.*;
import com.thoughtworks.xstream.core.util.Pool;
import dao.FactoryDAO;
import dao.IDao;
import dao.DaoXML;
import logicaEmpresarial.*;

public class AppConsole {

    private final String PALABRAPARAVOLVER = "v";
    private Usuario usuarioAutentificado;
    private Menu_vista vistaMenu;

    private IDao modelo;//Modelo seleccionado por el usuario en el que se encuentra actualmente la aplicacion.
    private Vista vista;//Vista seleccionada por el usuario en el que se encuenta actualmente al acpliacion.
    private Ong pilaDatos;


    public AppConsole(){

        while (true) {
            vistaMenu = new Menu_vista();
            modelo = FactoryDAO.lodaModel(FactoryDAO.typeDao.XML);
            if(modelo !=null){
                try {
                    run();
                } catch (Exception ex) {
                    vistaMenu.mensajeError("Se produjo un grave fallo, ningún dato fue guardado: " + ex.getMessage() + " in=" + ex.getLocalizedMessage());
                }
            }else{
                vistaMenu.mensajeError("El modelo seleccionado no es correcto.");
                break;
            }
        }
    }
    private void run() {
        boolean userLogueado = false;


        do {
            do {
                usuarioAutentificado = (Usuario) vistaMenu.PedirCredenciales();
                if (modelo.Login(usuarioAutentificado)) {
                    //El usuario se ha logueado correctamente.
                    vistaMenu.MostrarBienvenida(usuarioAutentificado);
                    userLogueado = true;
                } else {
                    //Error el usuario o la clave no es correcto.
                    vistaMenu.MostrarErrorLoggin();
                }
            } while (!userLogueado);//Hasta que el usuario no se haya logueado no continuamos.


            //Mostrar menu
            while (mostrarMenu()) ;
            userLogueado = false;
            usuarioAutentificado = null;
        }while(true);//Bucle infinito, la aplicación si el usuario quiere salir, debera de cerrar la ventana.

    }
    private boolean mostrarMenu(){

        String entradaUsuario = vistaMenu.MostrarMenu(usuarioAutentificado,PALABRAPARAVOLVER);
        int accesoApartado,minimo,maximo;

                    minimo=1;
                    maximo=Apartados.values().length;
                    if(usuarioAutentificado.getRol() == Usuario.tipoUsuarios.ADMINISTRADOR){
                        maximo-=1;
                    }else{

                        maximo -=2;
                    }

                    switch (FuncionesConsola.comprobarEntrada(entradaUsuario,
                            FuncionesConsola.MASCARANUMERO,
                            PALABRAPARAVOLVER,
                            FuncionesConsola.comprobaConversion.ENTERO)){
                        case TRUE:
                            accesoApartado = Integer.parseInt(entradaUsuario);
                            //Comprobar que el usuario ha marcado un valor valido
                            if(accesoApartado <=0 || accesoApartado>maximo)
                            {
                                vistaMenu.MostrarErrorEntrada(minimo,maximo,PALABRAPARAVOLVER);
                                return true;//Retorna true y se volverá a abrir el menu de nuevo
                }


                while(abrirApartado(Apartados.intToApartados(accesoApartado)));
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
    private boolean abrirApartado(Apartados apartados){

        //Cargar modelo factory y llamar a visualizar
         vista = getVista(apartados);
         if(vista == null){
             vistaMenu.mensajeError("Este apartado aun está en desarrollo.");
             return false;
         }

         //Descargamos los datos del apartado y verificamos errores.
         if(!modelo.descargaDatos(apartados)){

             //Si no se descargan los datos

             if(modelo.existeUnError())
                vistaMenu.mensajeError(modelo.getMensajeError());
             else
                 vistaMenu.mensajeError("Error desconocido");

             //Si se produce un error continua la aplicacion.
         }


        //Mostramos el listado y recibimos que desea hacer el usuario.
         String entradaUsuario = vista.mostrarLIstado(modelo.recogerLIstado(apartados),PALABRAPARAVOLVER,usuarioAutentificado);
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
                    vistaMenu.mensajeElementoCreado(modelo.crear(vista.crearElemento(modelo.getPilaDatosGenerales(),"CANCELAR"),apartados));

                }else{//De lo contrario llama a mostrar uno.
                    while(mostarUno(indiceSeleccionado-1,apartados));//Abirmos mostrar uno pasandole el apartado seleccionado -1.
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
    private boolean mostarUno(int elemento, Apartados apartado){
        //Mostramos el elemento y recogemos que desea hacer el usuario.
        String entradaUsuario = vista.mostrarUnElemento(modelo.recogerLIstado(apartado).get(elemento),PALABRAPARAVOLVER,usuarioAutentificado);
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


                        Object nuevoValor = vista.modificarElemento(modelo.getPilaDatosGenerales(), elemento,"CANCELAR");

                        if(nuevoValor == null){
                            vistaMenu.mensajeElementoEditado(false);
                        }else{
                            if(!modelo.modificar(nuevoValor,elemento,apartado)){
                                if(modelo.existeUnError()){
                                    vistaMenu.mensajeError(modelo.getMensajeError());
                                }
                                    vistaMenu.mensajeElementoEditado(false);
                            }else{
                                vistaMenu.mensajeElementoEditado(true);
                            }

                        }

                    }else{//De lo contrario llama a Borrar

                        if(vistaMenu.preguntarBorrar()){
                            if(!modelo.borrar(elemento,apartado)){
                                if(modelo.existeUnError()){
                                    vistaMenu.mensajeError(modelo.getMensajeError());
                                }
                                vistaMenu.mensajeBorrar(false);

                            }else{
                                vistaMenu.mensajeBorrar(true);
                                return false; //Sale para que no vuelva a intentar mostrar el elemento que acabamos de borrar
                            }
                        }else{
                            vistaMenu.mensajeBorrar(false);
                        }
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
    private Vista getVista(Apartados apartado){
        switch (apartado){
            case PERSONAL: return new Personal_vista();
            case PROYECTOS: return new Proyectos_vista();
            case DELEGACIONES: return new Delegaciones_vista();
            case USUARIOS: return new Usuario_vista();
            default:return  null;
        }
    }

}