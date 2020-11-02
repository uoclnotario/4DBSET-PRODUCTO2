package com.app.console;

public enum Apartados {
    NINGUNO,INGRESOS,PROYECTOS,SOCIOS,PERSONAL,DELEGACIONES,USUARIOS;

    public static Apartados intToApartados(int entrada){
        switch (entrada) {
            case 0 :return Apartados.NINGUNO;
            case 1 :return Apartados.INGRESOS;
            case 2 :return Apartados.PROYECTOS;
            case 3 :return Apartados.SOCIOS;
            case 4 :return Apartados.PERSONAL;
            case 5 :return Apartados.DELEGACIONES;
            case 6 :return Apartados.USUARIOS;
            default :return Apartados.NINGUNO;
        }
    }



}
