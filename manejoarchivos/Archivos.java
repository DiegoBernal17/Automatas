package archivos;

import java.io.*;

public class manejo_archivos {

    public static String leerArchivo(String nombreArchivo) {
        String file = "/home/bernal/Escritorio/Automatas/"+nombreArchivo+".txt";
        String text, cadena="";
        FileReader f;
        try {
            f = new FileReader(file);
            BufferedReader b = new BufferedReader(f);
            while((text = b.readLine())!=null) {
                cadena += text+"\n";
            }

            b.close();
        } catch (Exception e) {
            System.out.println("Error al leer un archivo");
        }
        return cadena;
    }

    public static void guardarArchivo(String nombreArchivo, String textoGuardar) {
        String path = "/home/bernal/Escritorio/Automatas/saves/"+nombreArchivo+".txt";
        File file = new File(path);
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            //bw.flush();
            bw.write(textoGuardar);
            bw.close();
        } catch(Exception e) {
            System.out.println("Error al guardar datos en un archivo.");
        }
    }
}
