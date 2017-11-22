package afd;

import javax.swing.JOptionPane;

public class SimuladorAFD2 {
    private boolean aceptacion;
    private String cadena;
    private String cadena_auxiliar;
    private char caracter_siguiente;

    public static void main(String[] args) {
        new SimuladorAFD2().run();
    }

    public SimuladorAFD2() {
        this.cadena = "";
        this.cadena_auxiliar = "";
    }

    public void run() {
        this.cadena = "";
        do {
            this.cadena = JOptionPane.showInputDialog("Inserta la cadena");
            if (cadena == null || cadena.equals("")) {
                if (JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "¿Seguro que quieres salir?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                    System.exit(0);
            } else {
                this.cadena_auxiliar = cadena;
                this.q0();
            }
        } while(true);
    }

    public void q0() {
        aceptacion = false;
        System.out.println("q0");

        try{ Thread.sleep(400); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a')
                this.q1();
            else if(caracter_siguiente == 'b')
                this.q5();
            else if(caracter_siguiente == 'c')
                this.q3();
            else
                this.q11();
        }
    }

    public  void q1() {
        aceptacion = true;
        System.out.println("q1");

        try{ Thread.sleep(400); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a')
                this.q2();
            else if(caracter_siguiente == 'b')
                this.q4();
            else if(caracter_siguiente == 'c')
                this.q3();
            else
                this.q11();
        }
    }

    public void q2() {
        aceptacion = false;
        System.out.println("q2");

        try{ Thread.sleep(400); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a')
                this.q2();
            else if(caracter_siguiente == 'b')
                this.q4();
            else if(caracter_siguiente == 'c')
                this.q3();
            else
                this.q11();
        }
    }

    public void q3() {
        aceptacion = true;
        System.out.println("q3");

        try{ Thread.sleep(400); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a' || caracter_siguiente == 'b')
                this.q8();
            else if(caracter_siguiente == 'c')
                this.q3();
            else
                this.q11();
        }
    }

    public void q4() {
        aceptacion = false;
        System.out.println("q4");

        try{ Thread.sleep(400); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a')
                this.q8();
            else if(caracter_siguiente == 'b')
                this.q4();
            else if(caracter_siguiente == 'c')
                this.q3();
            else
                this.q11();
        }
    }

    public void q5() {
        aceptacion = false;
        System.out.println("q5");

        try{ Thread.sleep(400); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a')
                this.q8();
            else if(caracter_siguiente == 'b')
                this.q4();
            else if(caracter_siguiente == 'c')
                this.q6();
            else
                this.q11();
        }
    }

    public void q6() {
        aceptacion = true;
        System.out.println("q6");

        try{ Thread.sleep(400); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a')
                this.q7();
            else if(caracter_siguiente == 'b')
                this.q9();
            else if(caracter_siguiente == 'c')
                this.q3();
            else
                this.q11();
        }
    }

    public void q7() {
        aceptacion = true;
        System.out.println("q7");

        try{ Thread.sleep(400); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a' || caracter_siguiente == 'b' || caracter_siguiente == 'c')
                this.q8();
            else
                this.q11();
        }
    }

    public void q8() {
        aceptacion = false;
        System.out.println("q8");

        try{ Thread.sleep(400); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        /*if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a' || caracter_siguiente == 'b' || caracter_siguiente == 'c') {
                this.q8();
            }
        }*/
        System.out.println("Incorrecto\n");
    }

    public void q9() {
        aceptacion = false;
        System.out.println("q9");

        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a' || caracter_siguiente == 'b')
                this.q8();
            else if(caracter_siguiente == 'c')
                this.q10();
            else
                this.q11();
        }
    }

    public void q10() {
        aceptacion = false;
        System.out.println("q10");

        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a')
                this.q7();
            else if(caracter_siguiente == 'b')
                this.q9();
            else if(caracter_siguiente == 'c')
                this.q10();
            else
                this.q11();
        }
    }

    public void q11() {
        aceptacion = false;
        System.out.println("q10");

        System.out.println("El caracter introducido no se encuentra en el alfabeto.\n");
    }

    public void avanzar() {
        String cadena_auxiliar2 = "";
        if(cadena_auxiliar.length() > 0)
            caracter_siguiente = cadena_auxiliar.charAt(0);

        for(int i=1; i<cadena_auxiliar.length(); i++) {
            cadena_auxiliar2 += cadena_auxiliar.charAt(i);
        }
        cadena_auxiliar = cadena_auxiliar2;
    }

    public boolean isFinal() {
        if(cadena_auxiliar.length() == 0) {
            if(aceptacion) {
                System.out.println("Correcto.\n");
            } else {
                System.out.println("Incorrecto.\n");
            }

            try{ Thread.sleep(2000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }
            return true;
        } else {
            return false;
        }
    }
}