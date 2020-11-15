package com.app.console.Vista;

import logicaEmpresarial.Delegacion;
import logicaEmpresarial.Ong;
import logicaEmpresarial.Personal;
import logicaEmpresarial.Usuario;
import javax.print.DocFlavor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;


public class Delegaciones_vista implements Vista {
    @Override
    public String mostrarLIstado(List listado, String salir, Usuario user) {
        if(listado == null){

    }else{
        List<Delegacion> delegacion = (List<Delegacion>)listado;
        System.out.println("Listado de las delegaciones:");
        System.out.println("\tINDICE\tNOMBRE\tDIRECCION");

        for(int i = 0; i < delegacion.size();i++)
            System.out.println("\t"+(i+1)+"\t"+delegacion.get(i).getNombre()+ "\t"+delegacion.get(i).getDireccion());


        System.out.println("Indique que desea realizar:");
        System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
        System.out.println("\t- 0 Crear un nuevo.");
        System.out.println("\t- Escriba "+salir+" para volver al menu");

        }


        return FuncionesConsola.leerConsola();
}

    @Override
    public String mostrarUnElemento(Object elemento, String salir, Usuario user) {

        FuncionesConsola.mostrarEncabezado("LISTADO DE DELEGACIONES");
        MostrarDato((Delegacion)elemento);

        System.out.println("Indique que desea realizar:");;

        //Solo los administradore pueden modificar delegaciones.
        if(user.getRol() == Usuario.tipoUsuarios.ADMINISTRADOR) {
            System.out.println("\t- 0 Modificar la delegacion.");
            System.out.println("\t- 1 Borrar la delegacion.");
        }
        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }
    public void MostrarDato(Delegacion delegacion){
        System.out.println("");
        System.out.println("Nombre:\t"+ delegacion.getNombre());
        System.out.println("Direccion:\t"+ delegacion.getDireccion());
        System.out.println("Telefono:\t"+ delegacion.getTelefono());


        System.out.println("Mostrando Personal Asignado");
        if(delegacion.getPersonal() != null){
            System.out.printf("%-10s %-10s %-10s %-10s\n", "INDICE", "NOMBRE", "DNI","TIPO");
            for(int i = 0; i < delegacion.getPersonal() .size();i++)
                System.out.printf("%-10s %-10s %-10s %-10s\n", +(i+1),delegacion.getPersonal().get(i).getNombre(),
                                                                            delegacion.getPersonal().get(i).getNif_dni(),
                                                                            delegacion.getPersonal().get(i).getTipoString());

        }else{
            System.out.println("No Hay ningún personal asignado");
        }




    }

    @Override
    public Object crearElemento(Ong datos, String PALABRACANCEALR) {
        return solicitarNuevo(datos,-1, PALABRACANCEALR);
    }

    @Override
    public Object modificarElemento(Ong datos, int indice, String PALABRACANCEALR) {
        return solicitarNuevo(datos,indice,PALABRACANCEALR);
    }
    private Object solicitarNuevo(Ong datos, int indice, String PALABRACANCELAR) {
        Delegacion nuevaDelegacion ;
        String entradaTexto;
        int entradaNumero;
        boolean esMOdificacion = indice != -1;

        if(esMOdificacion){

            System.out.println("Antes fallo=" + datos.getDelegaciones().size());
            nuevaDelegacion =datos.getDelegaciones().get(indice);

        }else{
            nuevaDelegacion = new Delegacion();
        }

        System.out.println("Creación de una nueva Delegación:");

        //Nombre
        if(esMOdificacion)
            System.out.println("Inserte Nombre:["+nuevaDelegacion.getNombre()+"]");
        else
            System.out.println("Inserte Nombre:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);

        //Si entradaTexto es null quiere decir que el usuario ha escrito la palaba de cancelar(no quiere seguir con la creacion/modificación)
        if(entradaTexto != null) {
            //Si la entradaTexto Vale (default) quiere decir que el usuario ha presionado enter y quiere dejarlo como esta.
            if(!entradaTexto.equals("(default)")){
                nuevaDelegacion.setNombre(entradaTexto);
            }
        }else{
            return null;
        }


        //Dirección
        if(esMOdificacion)
            System.out.println("Inserte Dirección:["+nuevaDelegacion.getDireccion()+"]");
        else
            System.out.println("Inserte Dirección:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);

        //Si entradaTexto es null quiere decir que el usuario ha escrito la palaba de cancelar(no quiere seguir con la creacion/modificación)
        if(entradaTexto != null) {
            //Si la entradaTexto Vale (default) quiere decir que el usuario ha presionado enter y quiere dejarlo como esta.
            if(!entradaTexto.equals("(default)")){
                nuevaDelegacion.setDireccion(entradaTexto);
            }
        }else{
            return null;
        }

        //Telefono
        if(esMOdificacion)
            System.out.println("Inserte Telefono:["+nuevaDelegacion.getTelefono()+"]");
        else
            System.out.println("Inserte el numeró de telefono:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARANUMERO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);

        //Si entradaTexto es null quiere decir que el usuario ha escrito la palaba de cancelar(no quiere seguir con la creacion/modificación)
        if(entradaTexto != null) {
            //Si la entradaTexto Vale (default) quiere decir que el usuario ha presionado enter y quiere dejarlo como esta.
            if(!entradaTexto.equals("(default)")){
                nuevaDelegacion.setTelefono(entradaTexto);
            }
        }else{
            return null;
        }


        return  nuevaDelegacion;
    }

}
