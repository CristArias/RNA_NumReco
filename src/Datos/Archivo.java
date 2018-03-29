/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Fainy
 */
public class Archivo {
    
    private static String inputData = "xor.txt";
    private static String outputFile = "xorout.txt";
    
    private BufferedWriter archivoEscritura;
	private BufferedReader archivoLectura;
	
	
    
	public void abrirArchivo(String nombre, boolean escritura) throws IOException
        {
		if(escritura == true){
			this.archivoEscritura = new BufferedWriter(new FileWriter(nombre,true ));
		}
		else{
			this.archivoLectura = new BufferedReader(new FileReader(nombre));
		}	}
	
	public void escribirArchivo(String datos) throws IOException{
		archivoEscritura.write(datos);
		archivoEscritura.newLine();	
	}

	public String leerArchivo() throws IOException
        {
		return archivoLectura.readLine();
	}
	
	public void cerrarArchivo() throws IOException
        {
		if(archivoEscritura!= null)	archivoEscritura.close();
		if(archivoLectura!= null) archivoLectura.close();
	}
	
	public boolean puedeLeer() throws IOException
        {
		return archivoLectura.ready();
	}
	public String[] LeerPalabras(int cantidad)
        {
		String[] palabras= new String[cantidad];
		int i=0;
		try 
                {
			while(this.puedeLeer() && i < cantidad)
                        {
				palabras[i] =  this.leerArchivo();
				i ++;
			}
		}
                catch (IOException e) 
                {
			e.printStackTrace();
		}
		return palabras;
	}
	public void crearTxt(String Nombre)throws IOException
	{
		File f;
			f = new File(Nombre);
                        f.createNewFile();
	}
        public void EliminarArchivo(String Nombre)throws IOException{
               File f=new File(Nombre);
               f.delete();
        }
        
        public int ExistenciaArchivo(String NombreArchivo)
        {
            int bandera = 0;
            File f = new File(NombreArchivo);
            if(f.exists()==true)
            {
                bandera = 1;
            }
            return bandera;
        }
        public void nuevoArchivo(String nombreArchivo,String cadena)
        {
            File f = new File("Usuarios",nombreArchivo+".txt");
            try
            {
                FileWriter w= new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write(cadena);
                wr.close();
                bw.close();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Error al generar archivo");
            }
        }
        
    public ArrayList leerArchivosTxt(String ruta)
    {
        String path = ruta; 
        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles(); 
        ArrayList<String> archivos= new ArrayList();

        for (int i = 0; i < listOfFiles.length; i++)   
        {
        if (listOfFiles[i].isFile())             
            {
                files = listOfFiles[i].getName();
                archivos.add(files);
            }
        }
        return archivos;
    }
    
  
    
}
