package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GestionFichero {

    public static void buscaXML() {
        String ruta = "D:\\4DBSET-Producto2\\BaseDatosXML.xml";
        File archivo = new File(ruta);
        BufferedWriter bw;
        if(archivo.exists()) {
            try {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write("El fichero de texto ya estaba creado.");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write("El fichero ha sido creado.");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
