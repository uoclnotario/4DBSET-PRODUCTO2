package com.app.console;

public enum Apartados {
    //TODOS LOS APARTADOS
   // NINGUNO,INGRESOS,PROYECTOS,SOCIOS,PERSONAL,DELEGACIONES,USUARIOS;

    //APARTADOS CON DESARROLLO COMPLETADO
    NINGUNO,PROYECTOS,PERSONAL,DELEGACIONES,USUARIOS;

    public static Apartados intToApartados(int entrada){
        switch (entrada) {
            case 0 :return Apartados.NINGUNO;
            case 1 :return Apartados.PROYECTOS;
            case 2 :return Apartados.PERSONAL;
            case 3 :return Apartados.DELEGACIONES;
            case 4 :return Apartados.USUARIOS;
            default :return Apartados.NINGUNO;
        }
    }



}
