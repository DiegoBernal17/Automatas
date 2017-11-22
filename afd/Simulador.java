package afd;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.net.SocketImpl;

public class Simulador {
    private String cadena;
    private int camino;
    private char caracter_actual;

    public static void main(String[] args) {
        int option;
        String cadena;
        do {
            option = Integer.parseInt(JOptionPane.showInputDialog("1) Validar Cadena \n2) Salir"));
            if(option == 1) {
                cadena = JOptionPane.showInputDialog("Ingresa la cadena:");
                Simulador afd = new Simulador(cadena);
                afd.run();
                System.out.println("\n\n");
            }
        } while (option != 2);
    }

    public Simulador(String cadena) {
        this.cadena = cadena;
    }

    public void run() {
        // a * b * c + U ( b c ) * a
        if(cadena.equals("") || cadena.isEmpty() || cadena == null)
            JOptionPane.showMessageDialog(null, "Cadena vacia.");

        boolean finish;
        int estado_actual = 0;
        this.buscarCamino();
        System.out.println("Cadena: "+cadena+" | camino: "+camino);
        for(int i=0; i<cadena.length(); i++) {
            if(camino == 0 || camino == 1 || camino == 2) {
                if (caracter_actual == 'a' && cadena.charAt(i) != 'a') {
                    caracter_actual = cadena.charAt(i);
                    estado_actual++;
                } else if (caracter_actual == 'b' && cadena.charAt(i) != 'b') {
                    caracter_actual = cadena.charAt(i);
                    estado_actual++;
                } else if (caracter_actual == 'c' && cadena.charAt(i) != 'c') {
                    JOptionPane.showMessageDialog(null, "Incorrecta.");
                    return;
                }
            }
            System.out.println("Estado actual: q"+estado_actual+" | caracter_actual: "+caracter_actual);

            if (camino == 0) {
                if(cadena.charAt(i) != caracter_actual) {
                    JOptionPane.showMessageDialog(null, "Incorrecta.");
                    return;
                }
            } else if (camino == 1) {
            } else if (camino == 2) {
            } else if (camino == 3) {
            } else if (camino == 4) {
                JOptionPane.showMessageDialog(null, "Correcta.");
                break;
            } else if (camino == 5) {
                JOptionPane.showMessageDialog(null, "Incorrecta.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Correcta.");
    }

    public void buscarCamino() {
        if(cadena.charAt(0) == 'a' && cadena.length() > 1)
            camino = 0;
        else if(cadena.charAt(0) == 'b') {
            if(cadena.length() > 1) {
                if(cadena.charAt(1) == 'b')
                    camino = 1;
                else if(cadena.charAt(1) == 'c')
                    camino = 3;
            }
        } else if(cadena.charAt(0) == 'c')
            camino = 2;
        else if(cadena.charAt(0) == 'a' && cadena.length() == 1)
            camino = 4;
        else
            camino = 5;

        caracter_actual = cadena.charAt(0);
    }
}
