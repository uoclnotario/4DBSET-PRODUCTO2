package com.app.console.Vista;

import logicaEmpresarial.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Ingresos_vista {

    public String MostrarLIstado(List listado, String salir, Usuario user)
    {
        if(listado == null)
        {

        }
        else
        {
            List<Ingresos> ingresos = (List<Ingresos>)listado;
            System.out.println("Ingresos:");
            System.out.println("\tINDICE\tCIF/NIF\tNOMBRE\tDOMICILIO\tIMPORTE\tFECHA\t");

            for(int i = 0; i < ingresos.size(); i++)
                System.out.println("\t"+(i+1)+"\t"+ingresos.get(i).getIndice()+ "\t"+ingresos.get(i).getCifOnif()+ "\t"+ingresos.get(i).getNombre()+
                        "\t"+ingresos.get(i).getDomicilo()+ "\t"+ingresos.get(i).getImporte()+ "\t"+ingresos.get(i).getFecha());

            System.out.println("Indique que desea realizar:");
            System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
            System.out.println("\t- 0 Crear un nuevo.");
            System.out.println("\t- Escriba "+salir+" para volver al menu");
        }


        return FuncionesConsola.leerConsola();

    }


    public String MostrarUno(Object elemento, String salir, Usuario user) {
        System.out.println("---MOSTRANDO DATOS DE INGRESO---");
        MostrarDato((Ingresos)elemento);

        System.out.println("Indique que desea realizar:");;
        System.out.println("\t- 0 Modificar este ingreso.");
        System.out.println("\t- 1 Borrar este ingreso.");

        System.out.println("\t- Escriba "+salir+" para volver atras.");

        return FuncionesConsola.leerConsola();
    }

    public void MostrarDato(Ingresos ingreso){
        System.out.println("");
        System.out.println("CIF/NIF:\t"+ ingreso.getCifOnif());
        System.out.println("Nombre:\t"+ ingreso.getNombre());
        System.out.println("Domicilio:\t"+ ingreso.getDomicilo());
        System.out.println("Importe:\t"+ ingreso.getImporte());
        System.out.println("Fecha:\t"+ ingreso.getFecha());
    }


    public Object Crear(Ingresos datos, String PALABRACANCEALR) {
        return solicitarNuevo(datos,-1, PALABRACANCEALR);
    }


    public Object Modificar(Ingresos datos, int indice, String PALABRACANCEALR) {
        return solicitarNuevo(datos,-1, PALABRACANCEALR);
    }

    private Object solicitarNuevo(Ingresos datos, int indice, String PALABRACANCELAR){
        Ingresos nuevoIngreso = new Ingresos();
        String entradaTexto;
        int entradaNumero;
        boolean esMOdificacion = indice != -1;


        System.out.println("Creación de nuevo ingreso:");

        //CIForNIF

        if(esMOdificacion)
            System.out.println("Inserte el CIF/NIF:"+datos.getCifOnif()+"]");
        else
            System.out.println("Inserte el CIF/NIF:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARANIFORCIF,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
                nuevoIngreso.setCifOnif(entradaTexto);
        }else{
            return null;
        }

        //Nombre
        if(esMOdificacion)
            System.out.println("Inserte el nombre:"+datos.getNombre()+"]");
        else
            System.out.println("Inserte el nombre:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {
            nuevoIngreso.setNombreIngreso(entradaTexto);
        }else{
            return null;
        }

        //Domicilio
        if(esMOdificacion)
            System.out.println("Inserte el domicilio:"+datos.getDomicilo()+"]");
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
            System.out.println("Inserte el importe:"+datos.getImporte()+"]");
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
            System.out.println("Inserte el fecha:"+datos.getFecha()+"]");
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

        return nuevoIngreso;
    }


}
