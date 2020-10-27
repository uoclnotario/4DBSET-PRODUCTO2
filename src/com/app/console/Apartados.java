package com.app.console;

public enum Apartados {
    NINGUNO,INGRESOS,PROYECTOS,SOCIOS,PERSONAL,DELEGACIONES,USUARIOS;

    public static Apartados intToApartados(int entrada){
        return switch (entrada) {
            case 0 -> Apartados.NINGUNO;
            case 1 -> Apartados.INGRESOS;
            case 2 -> Apartados.PROYECTOS;
            case 3 -> Apartados.SOCIOS;
            case 4 -> Apartados.PERSONAL;
            case 5 -> Apartados.DELEGACIONES;
            case 6 -> Apartados.USUARIOS;
            default -> Apartados.NINGUNO;
        };
    }



}
