package expresionesregulares;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JOptionPane;

public class Simulador {
	
	private final char[] alfabeto = {'b','c','d'}; // Se omite la a porque no se repetirá más que la primera vez
	// Guarda las veces que encuentre una letra del alfabeto {a,b,c}
	private int[] cuenta = {0,0,0};
	
	public Simulador() {
		this.iniciar();
	}
	
	public void iniciar() {
		int opcion;
		String menu = "Menu de opciones\n";
		menu += "1) Revisar archivo\n";
		menu += "2) Salir"; 

		do{
			try
			{
				opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));
				switch(opcion)
				{
					case 1:
						String cadena = JOptionPane.showInputDialog("Ingrese el nombre del archivo");
						if (this.validar(cadena))
							JOptionPane.showMessageDialog(null, "Cadena Correcta");
						else
							JOptionPane.showMessageDialog(null, "Cadena incorrecta");
					break;
					case 2:
						JOptionPane.showMessageDialog(null, "Fin del programa.");
					break;
					default:
						JOptionPane.showMessageDialog(null,"Opcion No valida\nElige otra.");
					break;
				}
			} catch(Exception e) {
				opcion = 0;
			}
		} while(opcion !=2);
	}
	
	public boolean validar(String archivo) {
		String cadena = "";
		try {
			// Lee el archivo
			FileReader f = new FileReader("/root/Escritorio/Automatas/"+archivo);
		    BufferedReader b = new BufferedReader(f); 
		    cadena = b.readLine();
		    f.close();
		} catch (Exception e) { }
		
		// Cambiamos a 0 las cuentas
		for(int i=0;i<cuenta.length;i++)
			cuenta[i] = 0;
		
		// Expresion Regular: ab*c+d+
		// Validamos primeramente que la cadena empiece a fuerza con la letra 'a'
		if(cadena.charAt(0) != 'a') {
			System.out.println("No contiene una 'a' al principio");
			return false;
		}
		
		// Busca en la cadena una letra o caracter fuera del alfabeto especificado
		// No incluimos la letra 'a' en el alfabeto para que marque como incorrecto si encuentra una extra
		// Y contamos el total de letras 'a','b','c'
		for(int i=1;i<cadena.length();i++) {
			for(int j=0;j<alfabeto.length;j++) {
				if(cadena.charAt(i) == alfabeto[j]) {
					// Sumamos +1 en el contador a la letra correspondiente
					cuenta[j]++;
					break;
				}
				if(j==alfabeto.length-1) {
					System.out.print("No se encuentra el caracter en el alfabeto");
					return false;
				}
			}
		}
		
		// Si 'c' o 'd' tiene en su cuenta 0 es incorrecto
		if(cuenta[1] == 0 || cuenta[2] == 0) {
			System.out.println("La letra 'a' y/o 'b' debe ir una o más veces");
			return false;
		}
		
		int inicio=1;
		int fin=1+cuenta[0];
		// Comprobamos el orden de las letras
		// Daremos 3 veces la vuelta para comprobar las letras, quitaremos con cada vuelta una letra a revisar
		for(int l=0; l<3; l++)
		{
			if(l==0 && cuenta[0]==0)
				l++;
			
			for(int i=inicio;i<fin;i++) {
				if(l==0) {
					if(cadena.charAt(i) != 'b') {
						System.out.println("Alguna letra fuera de orden.");
						return false;
					}
				} 
				if(l==1) {
					if(cadena.charAt(i) != 'c') {
						System.out.println("Alguna letra fuera de orden.");
						return false;
					}
				}
				if(l==3) {
					if(cadena.charAt(i) != 'd') {
						System.out.println("Alguna letra fuera de orden.");
						return false;
					}
				}
			}
			inicio = fin;
			if(l<2)
				fin = inicio+cuenta[l+1];
		}
		return true;
	}
}
