package com.app.console.Vista;

import logicaEmpresarial.Ingresos;
import logicaEmpresarial.Ong;
import logicaEmpresarial.Socios;
import logicaEmpresarial.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Socios_vista{

    public String MostrarLIstado(List listado, String salir, Usuario user)
    {
        if(listado == null)
        {

        }
        else
        {
            List<Socios> socios = (List<Socios>)listado;
            System.out.println("Socios:");
            System.out.println("\tIdentificacion\t");

            for(int i = 0; i < socios.size(); i++)
                System.out.println("\t"+(i+1)+"\t"+socios.get(i).getIdentificacion());

            System.out.println("Indique que desea realizar:");
            System.out.println("\t- Indique el indice del usuario a visualizar o modificar ");
            System.out.println("\t- 0 Crear un nuevo.");
            System.out.println("\t- Escriba "+salir+" para volver al menu");
        }


        return FuncionesConsola.leerConsola();

    }


    public String MostrarUno(Object elemento, String salir, Usuario user) {
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
        System.out.println("Identificacion:\t"+ socios.getIdentificacion());

    }


    public Object Crear(Socios datos, String PALABRACANCEALR) {
        return solicitarNuevo(datos,-1, PALABRACANCEALR);
    }


    public Object Modificar(Socios datos, int indice, String PALABRACANCEALR) {
        return solicitarNuevo(datos,-1, PALABRACANCEALR);
    }

    private Object solicitarNuevo(Socios datos, int indice, String PALABRACANCELAR){
        Socios nuevoIngreso = new Socios();
        String entradaTexto;
        int entradaNumero;
        boolean esMOdificacion = indice != -1;


        System.out.println("Creación de nuevo socios:");

        //CIForNIF

        if(esMOdificacion)
            System.out.println("Inserte el identificacion:"+datos.getIdentificacion()+"]");
        else
            System.out.println("Inserte el identificacion:");

        entradaTexto= FuncionesConsola.forzarEntradaTexto(FuncionesConsola.MASCARATEXTO,
                FuncionesConsola.comprobaConversion.TEXTO,
                PALABRACANCELAR,
                esMOdificacion);
        if(entradaTexto != null) {

            nuevoIngreso.setIdentificacion(entradaTexto);
        }else{
            return null;
        }

        return nuevoIngreso;
    }
}
