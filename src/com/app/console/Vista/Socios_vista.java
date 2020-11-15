package com.app.console.Vista;

import logicaEmpresarial.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Socios_vista implements Vista{

    @Override
    public String mostrarLIstado(List listado, String salir, Usuario user) {

        FuncionesConsola.mostrarEncabezado("LISTADO DE SOCIOS");

        if(listado == null || listado.size() == 0){
            System.out.println("No hay ningún socio almacenado.");

            System.out.println("Indique que desea realizar:");
        }else{
            List<Socios> socios = (List<Socios>)listado;

            System.out.printf("%-10s %-10s %-10s\n", "INDICE", "NOMBRE", "DNI");


            for(int i = 0; i < socios.size();i++){
                System.out.printf("%-10s %-10s %-10s\n", +(i+1),socios.get(i).getNombre(),socios.get(i).getNif_dni());
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
        System.out.println("---MOSTRANDO DATOS DE SOCIO---");
        MostrarDato((Socios)elemento);

        System.out.println("Indique que desea realizar:");;
        System.out.println("\t- 0 Modificar este socio.");
        System.out.println("\t- 1 Borrar este socio.");

        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }
    public void MostrarDato(Socios socios){
        System.out.println("");

    }
    @Override
    public Object crearElemento(Ong datos, String PALABRACANCEALR) {
        return solicitarNuevo(datos,-1, PALABRACANCEALR);
    }

    @Override
    public Object modificarElemento(Ong datos, int indice, String PALABRACANCEALR) {
       return solicitarNuevo(datos,-1, PALABRACANCEALR);
    }

    private Object solicitarNuevo(Ong datos, int indice, String PALABRACANCELAR){
        Socios nuevoIngreso = new Socios();
        String entradaTexto;
        int entradaNumero;
        boolean esMOdificacion = indice != -1;


        System.out.println("Creación de nuevo socios:");
        return nuevoIngreso;
    }
}
