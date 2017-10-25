package expresionesregulares;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Simulador2 {
	
	public static void main (String []args){
		int opcion;
		String menu = "Menu de opciones\n";
		menu += "1) Ingresar una cadena\n";
		menu += "2) Salir"; 

		do{
			opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));
			switch(opcion)
			{
				case 1:
					Pattern.compile("[abcd]"); // Alfabeto
					String cadena = JOptionPane.showInputDialog("Ingrese una cadena de ab*c+d+ ");
					if (cadena.matches("ab*c+d+")) // Evaluar expresion regular 
					{
						JOptionPane.showMessageDialog(null, "Cadena Correcta");
					} else {
						JOptionPane.showMessageDialog(null, "Cadena incorrecta");
					}
				break;
				case 2:
					JOptionPane.showMessageDialog(null, "Fin del programa.");
				break;
				default:
					JOptionPane.showMessageDialog(null,"Opcion No valida\nElige otra.");
				break;
			}
		} while(opcion !=2);
	}
}