package com.app.console.Vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuncionesConsola {
    public static final String MASCARATEXTO ="^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$";
    public  static final String MASCARANUMERO="^([0-9])*$";
    public static final String MASCARADECIMAL="^([0-9.,])*$";
    public static final String MASCARAFECHA = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";
    public  enum comprobaConversion{TEXTO,ENTERO,REAL,FECHA};
    public enum retornoConversion{FALSE,TRUE,EXIT}

    //Funciones estaticas publicas que son genericas para todas las clases que son parte de hotel.
    public static String leerConsola(){
        InputStreamReader isr=  new InputStreamReader(System.in);
        BufferedReader br  =  new BufferedReader (isr);

        String entrada;

        try{
            entrada= br.readLine();
            return entrada;
        }
        catch(IOException e){
            System.out.println("Se ha producido un error al introudcir los datos, vuelve a intentarlo");
            System.out.println(e.toString());
            return null;
        }
    }

    //Permite al usuario introducir si o no y lo devuelve como booleano
    public static boolean leerConsolaSiNo(){
        InputStreamReader isr=  new InputStreamReader(System.in);
        BufferedReader br  =  new BufferedReader (isr);

        String entrada;
        while(true){
            try{
                entrada= br.readLine();
                if(entrada.toUpperCase().matches("S|I|SI"))
                    return true;


                if(entrada.toUpperCase().matches("N|O|NO"))
                    return false;

                System.out.print("Los valores escritos no son correctos, solo puede introducir S / SI / N / NO vuelva a intentarlo.");
            }
            catch(IOException e){
                System.out.println("se ha producido un error, vuelv a intentarlo.");
            }
        }
    }

    //Verifica la entrada del usuario, en el caso de que el usuairo quiera cancelar se devolvera EXIT enum
    public static retornoConversion comprobarEntrada(String entrada, String filtro, String cadenaCancelar, comprobaConversion destinoTipo) {
        Pattern p = Pattern.compile(filtro);
        Matcher matcher = p.matcher(entrada);

        if (matcher.matches()) {
            //Controlamos que la converisón de tipos no vaya a producir error.
            try {
                switch (destinoTipo) {
                    case ENTERO:
                        int textConver = Integer.parseInt(entrada);
                        break;
                    case REAL:
                        float textConve2 = Float.parseFloat(entrada);
                        break;
                }
            } catch (Exception e) {
                System.out.println("El valor introducido no es correcto, vuelva a intentarlo o escriba " + cadenaCancelar);
                return retornoConversion.FALSE;
            }


            return retornoConversion.TRUE;
        } else {
            //Compruebo si lo que el usuario ha puesto ha sido la palabra para cancelar.
            if (entrada.equals(cadenaCancelar))
                return retornoConversion.EXIT;

            System.out.println("El valor introducido no es correcto, vuelva a intentarlo o escriba " + cadenaCancelar);
            return retornoConversion.FALSE;
        }
    }
}
