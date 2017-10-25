package afd;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.geom.*;

public class SimuladorAFD extends Applet {
    private boolean aceptacion;
    private String cadena;
    private String cadena_auxiliar;
    private char caracter_siguiente;
    private Graphics2D g2;

    public SimuladorAFD() {
        this.cadena = "ini";
        this.cadena_auxiliar = "";
    }

    public void paint(Graphics g) {
        setSize(500,300);
        g2 = (Graphics2D) g;

        // Estados de aceptacion
        g2.setColor(Color.gray);
        g2.fill(new Ellipse2D.Double(176,86,38,38));
        g2.fill(new Ellipse2D.Double(336,26,38,38));
        g2.fill(new Ellipse2D.Double(316,186,38,38));
        g2.fill(new Ellipse2D.Double(236,176,38,38));

        // Estados
        g2.setColor(Color.lightGray);
        g2.fill(new Ellipse2D.Double(100,100,30,30));
        g2.fill(new Ellipse2D.Double(180,90,30,30));
        g2.fill(new Ellipse2D.Double(160,144,30,30));
        g2.fill(new Ellipse2D.Double(96,180,30,30));
        g2.fill(new Ellipse2D.Double(250,50,30,30));
        g2.fill(new Ellipse2D.Double(280,100,30,30));
        g2.fill(new Ellipse2D.Double(340,30,30,30));
        g2.fill(new Ellipse2D.Double(360,120,30,30));
        g2.fill(new Ellipse2D.Double(240,180,30,30));
        g2.fill(new Ellipse2D.Double(320,190,30,30));

        // Nombre de los estados (q)
        g2.setColor(Color.black);
        g2.drawString("q0", 108, 120);
        g2.drawString("q1", 188, 110);
        g2.drawString("q5", 168, 164);
        g2.drawString("q6", 104, 200);
        g2.drawString("q2", 258, 70);
        g2.drawString("q4", 288, 120);
        g2.drawString("q3", 348, 50);
        g2.drawString("q9", 368, 140);
        g2.drawString("q7", 248, 200);
        g2.drawString("q8", 328, 210);

        // Lineas
        g2.draw(new QuadCurve2D.Double(120,100,210,10,338,34)); // q0:q3
        g2.drawLine(132,114,174,107); // q0:q1
        g2.drawLine(209,90,249,70); // q1:q2
        g2.drawLine(282,62,335,48); // q2:q3
        g2.drawArc(256,42,18,20,0,180); // q2
        g2.drawArc(357,22,18,20,322 ,180); // q5
        g2.drawLine(268,80,282,105); // q2:q4
        g2.draw(new QuadCurve2D.Double(215,100,290,90,339,58)); // q1:q3
        g2.drawLine(215,112,278,114); // q1:q4
        g2.drawLine(312,119,359,132); // q4:q9
        g2.drawArc(287, 93,18,20,350,180); // q4
        g2.drawLine(308,108,348,64); // q4:q3
        g2.drawLine(360,65,372,119); // q3:q9
        g2.drawLine(348,189,372,151); // q8:q9
        g2.drawLine(129,124,162,148); // q0:q5
        g2.drawLine(191,164,236,188); // q5:q7
        g2.draw(new QuadCurve2D.Double(235,200,206,194,183,173)); // q7:q5
        g2.draw(new QuadCurve2D.Double(192,158,280,170,360,144)); // q5:q9
        g2.drawLine(275,200,315,206); // q7:q8
        g2.draw(new QuadCurve2D.Double(370,58,450,140,360,220)); // parte1> q3> q7:q3
        g2.draw(new QuadCurve2D.Double(360,220,310,250,266,212)); // parte2> q7> q7:q3
        g2.drawLine(160,167,127,190); // q5:q6
        g2.drawArc(102,198,18,20,180,180); // q6
        g2.drawArc(379,126,20,18,270,180); // q9
        g2.draw(new QuadCurve2D.Double(118,180,140,110,359,138)); // q6:q9
        g2.draw(new QuadCurve2D.Double(120,70,200,0,344,28)); // parte1> q3> q6:q3
        g2.draw(new QuadCurve2D.Double(102,182,70,120,120,70)); // parte2> q6> q6:q3

        if(cadena.equals("ini"))
            this.run();
    }

    public void run() {
        this.cadena = "";
        this.cadena = JOptionPane.showInputDialog("Inserta la cadena");
        if(cadena==null || cadena.equals("")) {
            if(JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "¿Seguro que quieres salir?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                System.exit(0);
            else
                this.cadena = JOptionPane.showInputDialog("Inserta la cadena");
        }

        this.cadena_auxiliar = cadena;
        this.q0();
    }

    public void q0() {
        aceptacion = false;
        System.out.println("q0");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(100,100,30,30));
        g2.setColor(Color.black);
        g2.drawString("q0", 108, 120);
        try{ Thread.sleep(100); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a') {
                this.q1();
            } else if(caracter_siguiente == 'b') {
                this.q5();
            } else if(caracter_siguiente == 'c') {
                this.q3();
            }
        }
    }

    public  void q1() {
        aceptacion = true;
        System.out.println("q1");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(180,90,30,30));
        g2.setColor(Color.black);
        g2.drawString("q1", 188, 110);
        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a') {
                this.q2();
            } else if(caracter_siguiente == 'b') {
                this.q4();
            } else if(caracter_siguiente == 'c') {
                this.q3();
            }
        }
    }

    public void q2() {
        aceptacion = false;
        System.out.println("q2");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(250,50,30,30));
        g2.setColor(Color.black);
        g2.drawString("q2", 258, 70);
        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a') {
                this.q2();
            } else if(caracter_siguiente == 'b') {
                this.q4();
            } else if(caracter_siguiente == 'c') {
                this.q3();
            }
        }
    }

    public void q3() {
        aceptacion = true;
        System.out.println("q3");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(340,30,30,30));
        g2.setColor(Color.black);
        g2.drawString("q3", 348, 50);
        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a' || caracter_siguiente == 'b') {
                this.q9();
            } else if(caracter_siguiente == 'c') {
                this.q3();
            }
        }
    }

    public void q4() {
        aceptacion = false;
        System.out.println("q4");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(280,100,30,30));
        g2.setColor(Color.black);
        g2.drawString("q4", 288, 120);
        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a') {
                this.q9();
            } else if(caracter_siguiente == 'b') {
                this.q4();
            } else if(caracter_siguiente == 'c') {
                this.q3();
            }
        }
    }

    public void q5() {
        aceptacion = false;
        System.out.println("q5");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(160,144,30,30));
        g2.setColor(Color.black);
        g2.drawString("q5", 168, 164);
        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a') {
                this.q9();
            } else if(caracter_siguiente == 'b') {
                this.q6();
            } else if(caracter_siguiente == 'c') {
                this.q7();
            }
        }
    }

    public void q6() {
        aceptacion = false;
        System.out.println("q6");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(96,180,30,30));
        g2.setColor(Color.black);
        g2.drawString("q6", 104, 200);
        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a') {
                this.q9();
            } else if(caracter_siguiente == 'b') {
                this.q6();
            } else if(caracter_siguiente == 'c') {
                this.q3();
            }
        }
    }

    public void q7() {
        aceptacion = true;
        System.out.println("q7");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(240,180,30,30));
        g2.setColor(Color.black);
        g2.drawString("q7", 248, 200);
        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a') {
                this.q8();
            } else if(caracter_siguiente == 'b') {
                this.q5();
            } else if(caracter_siguiente == 'c') {
                this.q3();
            }
        }
    }

    public void q8() {
        aceptacion = true;
        System.out.println("q8");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(320,190,30,30));
        g2.setColor(Color.black);
        g2.drawString("q7", 248, 200);
        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }

        if(!this.isFinal()) {
            this.avanzar();
            if(caracter_siguiente == 'a' || caracter_siguiente == 'b' || caracter_siguiente == 'c') {
                this.q9();
            }
        }
    }

    public void q9() {
        aceptacion = false;
        System.out.println("q9");

        g2.setColor(Color.yellow);
        g2.fill(new Ellipse2D.Double(360,120,30,30));
        g2.setColor(Color.black);
        g2.drawString("q9", 368, 140);

        g2.drawString("Incorrecto.", 20, 20);
        System.out.println("Incorrecto");
        this.cadena = "ini";

        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }
    }

    public void avanzar() {
        String cadena_auxiliar2 = "";
        if(cadena_auxiliar.length() > 0)
            caracter_siguiente = cadena_auxiliar.charAt(0);

        for(int i=1; i<cadena_auxiliar.length(); i++) {
            cadena_auxiliar2 += cadena_auxiliar.charAt(i);
        }
        cadena_auxiliar = cadena_auxiliar2;
        try{ Thread.sleep(1000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }
        this.refresh();
    }

    public boolean isFinal() {
        if(cadena_auxiliar.length() == 0) {
            g2.setFont(new Font("Arial",Font.BOLD,26));
            if(aceptacion) {
                g2.setColor(Color.green);
                g2.drawString("Correcto.", 20, 40);
                System.out.println("Correcto");
            } else {
                g2.setColor(Color.red);
                g2.drawString("Incorrecto.", 20, 40);
                System.out.println("Incorrecto.");
            }
            this.cadena = "ini";

            try{ Thread.sleep(2000); } catch(InterruptedException e ) { System.out.println("Thread Interrupted"); }
            return true;
        } else {
            repaint();
            return false;
        }
    }

    public void refresh() {
        g2.setColor(Color.gray);
        g2.fill(new Ellipse2D.Double(176,86,38,38));
        g2.fill(new Ellipse2D.Double(336,26,38,38));
        g2.fill(new Ellipse2D.Double(316,186,38,38));
        g2.fill(new Ellipse2D.Double(236,176,38,38));

        g2.setColor(Color.lightGray);
        g2.fill(new Ellipse2D.Double(100,100,30,30));
        g2.fill(new Ellipse2D.Double(180,90,30,30));
        g2.fill(new Ellipse2D.Double(160,144,30,30));
        g2.fill(new Ellipse2D.Double(96,180,30,30));
        g2.fill(new Ellipse2D.Double(250,50,30,30));
        g2.fill(new Ellipse2D.Double(280,100,30,30));
        g2.fill(new Ellipse2D.Double(340,30,30,30));
        g2.fill(new Ellipse2D.Double(360,120,30,30));
        g2.fill(new Ellipse2D.Double(240,180,30,30));
        g2.fill(new Ellipse2D.Double(320,190,30,30));

        g2.setColor(Color.black);
        g2.drawString("q0", 108, 120);
        g2.drawString("q1", 188, 110);
        g2.drawString("q5", 168, 164);
        g2.drawString("q6", 104, 200);
        g2.drawString("q2", 258, 70);
        g2.drawString("q4", 288, 120);
        g2.drawString("q3", 348, 50);
        g2.drawString("q9", 368, 140);
        g2.drawString("q7", 248, 200);
        g2.drawString("q8", 328, 210);
    }
}