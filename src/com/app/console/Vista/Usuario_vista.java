package com.app.console.Vista;
import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Ong;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Usuario;
import java.util.List;
import java.util.Optional;
public class Usuario_vista implements Vista {
    @Override
    public String MostrarLIstado(List listado, String salir, Usuario user) {
        if (listado == null) {
        } else {
            List<Usuario> usuarios = (List<Usuario>) listado;
            System.out.println("Listado de los usuarios:");
            System.out.println("\tINDICE\tNOMBRE\tHASING\tROL");
            for (int i = 0; i < usuarios.size(); i++)
                System.out.println("\t" + (i + 1) + "\t" + usuarios.get(i).getNombre() + "\t" + usuarios.get(i).getHasing());
            System.out.println("Indique que desea realizar:");
            System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
            System.out.println("\t- 0 Crear un nuevo.");
            System.out.println("\t- Escriba " + salir + " para volver al menu");
        }
        return FuncionesConsola.leerConsola();
    }
    @Override
    public String MostrarUno(Object elemento, String salir, Usuario user) {
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
        System.out.println("Direccion:\t" + usuario.getHasing());
        System.out.println("Telefono:\t" + usuario.getRol());
    }
    @Override
    public Object Crear(Ong datos, String PALABRACANCELAR) {
        return solicitarNuevo(datos, -1, PALABRACANCELAR);
    }
    @Override
    public Object Modificar(Ong datos, int indice, String PALABRACANCELAR) {
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
        } else {
            nuevoUsuario = new Usuario();
        }
        System.out.println("Creación de un nuevo usuario:");
        return nuevoUsuario;
    }
}
