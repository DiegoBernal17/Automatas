package analisis_sintactico;

import java.util.Scanner;

public class DescensoRecursivo {
    private String entrada;
    private String cadena, cadena_auxiliar;
    private char apuntador;

    /*
     * S -> cAd
     * A -> ab | a
     */
    public static void main(String[] args) {
        new DescensoRecursivo();
    }

    public DescensoRecursivo() {
        Scanner sc = new Scanner(System.in);
        entrada = sc.next();
        S();
    }

    public void S() {
        if (apuntador == 'c') {
            avanzar();
            int valor = A();
            if (valor != 2 && apuntador == 'd') {
                System.out.println("Cadena valida.");
                return;
            }
        }
        System.out.println("Caden NO valida.");
    }

    public int A() {
        if (apuntador == 'a') {
            avanzar();
            if(apuntador == 'b') {
                return 0;
            }
            retroceder();
            return 1;
        }
        return 2;
    }

    public void avanzar() {
        if(cadena.length() > 0) {
            cadena_auxiliar = cadena;
            apuntador = cadena.charAt(0);
            cadena = cadena.substring(1, cadena.length());
        }
    }

    public void retroceder() {
        apuntador = cadena_auxiliar.charAt(0);
        cadena = cadena_auxiliar;
    }
}
