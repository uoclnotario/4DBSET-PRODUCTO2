package com.app.console.Vista;

import logicaEmpresarial.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Ingresos_vista implements Vista {

    @Override
    public String mostrarLIstado(List listado, String salir, Usuario user) {
        FuncionesConsola.mostrarEncabezado("LISTADO DE INGRESOS");

        if(listado == null || listado.size() == 0){
            System.out.println("No hay ningún ingreso almacenado.");

            System.out.println("Indique que desea realizar:");
        }else{
            List<Ingresos> ingresos = (List<Ingresos>)listado;

            System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", "INDICE", "CIF/NIF", "NOMBRE","DOMICILIO","FECHA");


            for(int i = 0; i < ingresos.size();i++){
                String del="No asignado";

                System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", +(i+1),
                                                                    ingresos.get(i).getNif_dni(),
                                                                    ingresos.get(i).getNombre(),
                                                                    ingresos.get(i).getDomicilio(),
                                                                    ingresos.get(i).getImporte(),
                                                                    FuncionesConsola.formatoFecha.format(ingresos.get(i).getFecha()));
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
        System.out.println("---MOSTRANDO DATOS DE INGRESO---");
        mostrarDato((Ingresos)elemento);

        System.out.println("Indique que desea realizar:");;
        System.out.println("\t- 0 Modificar este ingreso.");
        System.out.println("\t- 1 Borrar este ingreso.");

        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }
    private  void mostrarDato(Ingresos ingreso){
        System.out.println("");
        System.out.println("CIF/NIF:\t"+ ingreso.getNif_dni());
        System.out.println("Nombre:\t"+ ingreso.getNombre());
        System.out.println("Domicilio:\t"+ ingreso.getDomicilio());
        System.out.println("Importe:\t"+ ingreso.getImporte());
        System.out.println("Fecha:\t"+ ingreso.getFecha());
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
        Ingresos nuevoIngreso = new Ingresos();
        String entradaTexto;
        int entradaNumero;
        boolean esMOdificacion = indice != -1;

/*
        System.out.println("Creación de nuevo ingreso:");

        //CIForNIF

        if(esMOdificacion)
            System.out.println("Inserte el CIF/NIF:"+datos.getIngresos().get(indice).getNif_dni()+"]");
        else
            System.out.println("Inserte el CIF/NIF:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARANIFORCIF,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            nuevoIngreso.setNif_dni(entradaTexto);
        }else{
            return null;
        }

        //Nombre
        if(esMOdificacion)
            System.out.println("Inserte el nombre:"+datos.getIngresos().get(indice).getNombre()+"]");
        else
            System.out.println("Inserte el nombre:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
           // nuevoIngreso.setNombreIngreso(entradaTexto);
        }else{
            return null;
        }

        //Domicilio
        if(esMOdificacion)
            System.out.println("Inserte el domicilio:"+datos.getIngresos().get(indice).getDomicilo()+"]");
        else
            System.out.println("Inserte el domicilio:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            nuevoIngreso.setDomicilioIngreso(entradaTexto);
        }else{
            return null;
        }

        //Importe

        if(esMOdificacion)
            System.out.println("Inserte el importe:"+datos.getIngresos().get(indice).getImporte()+"]");
        else
            System.out.println("Inserte el importe:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARADECIMAL,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            float entradaFloat = Float.parseFloat(entradaTexto);
            nuevoIngreso.setImporte(entradaFloat);
        }else{
            return null;
        }

        //Fecha

        if(esMOdificacion)
            System.out.println("Inserte el fecha:"+datos.getIngresos().get(indice).getFecha()+"]");
        else
            System.out.println("Inserte el fecha:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARAFECHA,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            Date entradaDate = null;
            try {
                entradaDate = formatter1.parse(entradaTexto);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            nuevoIngreso.setFecha(entradaDate);
        }else{
            return null;
        }
*/
        return nuevoIngreso;
    }
}
