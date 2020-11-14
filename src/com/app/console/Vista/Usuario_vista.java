package com.app.console.Vista;

import logicaEmpresarial.*;

import java.util.List;
import java.util.Optional;


public class Usuario_vista implements Vista {

    @Override
    public String mostrarLIstado(List listado, String salir, Usuario user) {
        if(user.getRol() != Usuario.tipoUsuarios.ADMINISTRADOR)
        {
            System.out.println("No tiene acceso a este apartado");
            return null;
        }

        FuncionesConsola.mostrarEncabezado("LISTADO DE USUARIO");

        if(listado == null || listado.size() == 0){
            System.out.println("No hay ningún Usuario almacenado.");

            System.out.println("Indique que desea realizar:");
        }else{
            List<Usuario> usuarios = (List<Usuario>)listado;

            System.out.printf("%-10s %-10s %-10s\n", "INDICE", "NOMBRE", "PERMISOS");


            for(int i = 0; i < usuarios.size();i++){
                System.out.printf("%-10s %-10s %-10s \n", +(i+1),
                        usuarios.get(i).getNombre(),
                        usuarios.get(i).getRolString());
            }


            System.out.println("Indique que desea realizar:");
            System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
        }

        System.out.println("\t- 0 Crear un nuevo.");
        System.out.println("\t- Escriba "+salir+" para volver al menu");


        return FuncionesConsola.leerConsola();
    }

    @Override
    public String mostrarUnElemento(Object elemento, String salir, Usuario user) {
        System.out.println("---MOSTRANDO DATOS DEL USUARIO---");
        MostrarDato((Usuario) elemento);
        System.out.println("Indique que desea realizar:");
        ;
        System.out.println("\t- 0 Modificar el usuario.");
        System.out.println("\t- 1 Borrar el usuario");
        System.out.println("\t- Escriba " + salir + " para volver atras.");
        return FuncionesConsola.leerConsola();
    }
    public void MostrarDato(Usuario usuario) {
        System.out.println("");
        System.out.println("Nombre:\t" + usuario.getNombre());
        System.out.println("Hasing:\t" + usuario.getHasing());
        System.out.println("Rol:\t" + usuario.getRol());
    }
    @Override
    public Object crearElemento(Ong datos, String PALABRACANCELAR) {
        return solicitarNuevo(datos, -1, PALABRACANCELAR);
    }
    @Override
    public Object modificarElemento(Ong datos, int indice, String PALABRACANCELAR) {
        return solicitarNuevo(datos, indice, PALABRACANCELAR);
    }
    private Object solicitarNuevo(Ong datos, int indice, String PALABRACANCELAR) {
        Usuario nuevoUsuario;
        String entradaTexto;
        int entradaNumero;
        boolean esMOdificacion = indice != -1;
        if (esMOdificacion) {
            System.out.println("Antes fallo=" + datos.getUsuarios().size());
            nuevoUsuario = datos.getUsuarios().get(indice);
        } else nuevoUsuario = new Usuario();
        System.out.println("Creación de un nuevo usuario:");
        return nuevoUsuario;
    }
}