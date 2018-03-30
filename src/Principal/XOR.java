/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * XOR.java
 * Sample class to demostrate the use of the Joone's core engine
 * see the Developer Guide for more details
 */

/*
 * JOONE - Java Object Oriented Neural Engine
 * http://joone.sourceforge.net
 */
package Principal;

import Logica.Entrenamiento;
import Logica.OpenCV;
import Logica.Producir;
import Logica.Red;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.joone.engine.*;
import org.joone.engine.learning.*;
import org.joone.io.*;
import org.joone.net.NeuralNet;

public class XOR 
{
    private static String inputData = "dataset ordenado aleatoriamente.txt";
    private static String outputFile = "xorout.txt";
    
    private static String archivoPrueba = "archivoProducción.txt";
    
    
    /** Creates new XOR */
    public XOR() {
    }
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) throws IOException {
        //XOR xor = new XOR();
        Red mired= new Red();
        
        OpenCV oc = new OpenCV();
        oc.digitalizar();
        
        Entrenamiento mientreno = new Entrenamiento();
        Producir miproduccion = new Producir();
        
        
        NeuralNet minet = mired.creacionRed();
        NeuralNet minetsita;
        minetsita = mientreno.creacionArchivoEntradaYTeacher(minet, inputData, outputFile);
        
        minetsita.join();

        miproduccion.ejecutanto(minetsita, outputFile, archivoPrueba);        
    }
    
    
    
}

