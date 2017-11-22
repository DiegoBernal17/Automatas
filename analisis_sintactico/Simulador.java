package analisis_sintactico;

import javax.swing.*;
import manejoarchivos.Archivos;
import reconocimiento_tokens.AnalisisLexico;

public class Simulador {
    private String cadena_original;
    private String cadena_auxiliar;
    private char caracter_actual;
    private char caracter_siguiente;

    public static void main(String[] args) {
        new Simulador();
    }

    public Simulador() {
        int option;
        String nombre="";
        do {
            option = Integer.parseInt(JOptionPane.showInputDialog(null,"1) Leer archivo\n2) Salir"));

            if(option==1){
                nombre = JOptionPane.showInputDialog(null, "Nombre del archivo");
                if(nombre.equals(""))
                    nombre = "default";

                cadena_original = Archivos.leerArchivo(nombre);
                if(!cadena_original.isEmpty()) {
                    cadena_auxiliar = cadena_original;
                    inicio();

                } else
                    JOptionPane.showMessageDialog(null,"El archivo seleccionado está vacio.");
            }
        } while(option != 2);
    }

    public void inicio() {

    }

    public void variables() {

    }

    public void ciclos() {

    }

    public void condiciones() {

    }

    public void avanzar() {
        if(!esFinal()) {
            caracter_actual = cadena_auxiliar.charAt(0);
            if(cadena_auxiliar.length() > 0)
                caracter_siguiente  = cadena_auxiliar.charAt(1);
            cadena_auxiliar = cadena_auxiliar.substring(1, cadena_auxiliar.length());
        }
    }

    public boolean esFinal() {
        if(cadena_auxiliar.length() > 0)
            return false;
        return true;
    }
}