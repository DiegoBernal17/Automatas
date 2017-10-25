package traductor;

import java.io.*;

public class FileTranslate {
	private int row;
	private String text;
	private String wordsForRow;
	
	public FileTranslate() {
		this.row=0;
		this.text="";
		this.wordsForRow="";
	}
	
	public void saveToFile() throws IOException {
		String path = "/root/Escritorio/Automatas/traduccion.txt";
		File file = new File(path);
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(file));
		bw.write(this.text+"\n\n");
		bw.write("Cantidad de lineas leidas: "+this.row+"\n\n");
		bw.write("\nPalabras por linea: \n"+this.wordsForRow);
		bw.close();
	}
	
	public void translateContent(String file) throws FileNotFoundException, IOException {
	      String text, abcLine;
	      FileReader f = new FileReader(file);
	      BufferedReader b = new BufferedReader(f); 
	      while((text = b.readLine())!=null) {
	    	  this.row++;
	    	  FileReader alphabet = new FileReader("/root/Escritorio/Automatas/Alfabeto.txt");
	    	  BufferedReader abc = new BufferedReader(alphabet);
	    	  while((abcLine = abc.readLine())!=null) {
	    		  String[] separateLines = abcLine.split("=>");
		          if(text.equals(separateLines[0])) {
		        	  String[] words = separateLines[1].split(" ");
		        	  this.wordsForRow += "Linea "+row+": "+words.length+" palabras\n";
		        	  this.text += separateLines[1]+"\n";
		        	  System.out.println(separateLines[1]);
		        	  break;
		          }
	    	  }
		      abc.close();
	      }
	      b.close();
	}
	
	public void translate() {
		try {
			this.translateContent("/root/Escritorio/Automatas/archivoAtraducir.txt");
			this.saveToFile();
		} catch (Exception e) {
			
		}
	}
}