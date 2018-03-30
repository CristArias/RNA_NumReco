/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.io.File;
import java.io.IOException;
import org.joone.engine.Layer;
import org.joone.engine.NeuralNetListener;
import org.joone.io.FileInputSynapse;
import org.joone.io.FileOutputSynapse;
import org.joone.net.NeuralNet;

/**
 *
 * @author Fainy
 */
public class Producir {
    
    Entrenamiento ent = new Entrenamiento();
    
    
    public void ejecutanto (NeuralNet nnet, String archivoResultado, String archivoEntrada) throws IOException
    {
//        nnet = ent.recuperar(archivoResultado);
        boolean comparacion = ent.recuperar2(archivoResultado);
        
        if(comparacion != true) //red entrenada
        {
            //Se elimina el archivo de entrada y el teacher, ya que el archivo de las salidas deseadas
            //está conectado al teacher
            nnet.getInputLayer().removeAllInputs();
            nnet.getOutputLayer().removeAllOutputs();
            
            FileInputSynapse entrada = new FileInputSynapse();//se crea y se agrega a la capa de entrada el archivo con los patrones
            entrada.setAdvancedColumnSelector("1-14");         //que van a ser producidos.
            entrada.setInputFile(new File(archivoEntrada));
 
            Layer capaEntrada = nnet.getInputLayer();
            capaEntrada.addInputSynapse(entrada);
            
            FileOutputSynapse salida = new FileOutputSynapse();//se crea el archivo y se agrega a la capa de salida 
            salida.setFileName("resultado.txt");              //donde van a quedar guardados los resultados.
            
            Layer capaSalida = nnet.getOutputLayer();
            capaSalida.addOutputSynapse(salida);
            
            
            nnet.getMonitor().setTotCicles(1);// se modifica el panel de control
            nnet.getMonitor().setLearning(false);
             
            nnet.go(true, true);
            
            
            
        }else
        {
            System.out.println("La red no se entrenó");
        }
    }
    
    
}
