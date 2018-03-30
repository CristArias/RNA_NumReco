/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;


import java.io.BufferedReader;
import org.joone.engine.Monitor;
import org.joone.engine.NeuralNetEvent;
import org.joone.engine.NeuralNetListener;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.FileOutputSynapse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.joone.engine.*;
import org.joone.net.NeuralNet;


/**
 *
 * @author Fainy
 */
public class Entrenamiento implements NeuralNetListener{
 
    public synchronized NeuralNet creacionArchivoEntradaYTeacher(NeuralNet nnet, String patrones, String outputfile)
    {
        FileInputSynapse inputStream = new FileInputSynapse();//se ingresa el archivo que contiene los patrones
        inputStream.setAdvancedColumnSelector("1-14");
        inputStream.setInputFile(new File(patrones));
        
        Layer capaentrada = nnet.getInputLayer();//el archivo de los patrones apunta a la capa de entrada de la red
        capaentrada.addInputSynapse(inputStream);
        
        
        TeachingSynapse teacher = new TeachingSynapse(); //se crea un objeto del entrenador (teacher)
        
        FileInputSynapse samples = new FileInputSynapse(); //se ingresa el archivo que contiene las salidas deseadas
        samples.setInputFile(new File(patrones));
        samples.setAdvancedColumnSelector("15-18");
        
        teacher.setDesired(samples);
       
        Layer capasalida = nnet.getOutputLayer();
        capasalida.addOutputSynapse(teacher);
        NeuralNet n;
        n = panelControl(nnet);
        guardar(teacher, n, outputfile);
        
        return n;
    }
    
    public NeuralNet panelControl(NeuralNet nnet)
    {
        Monitor monitor = nnet.getMonitor();//Se crea el panel de control
        
        monitor.setLearningRate(0.3);
        monitor.setMomentum(0.22);
        monitor.addNeuralNetListener(this);
        monitor.setTrainingPatterns(10000); /* # of rows (patterns) contained in the input file */
        monitor.setTotCicles(1000); /* How many times the net must be trained on the input patterns */
        monitor.setLearning(true); /* The net must be trained */
        nnet.setMonitor(monitor);
        nnet.go(); /* The net starts the training job */
        
        System.out.println("Entrenando...."); 
        
        return nnet;
        
    }
    
    
    //Metodo para guardar en un archivo lo que la red aprendió
    public void guardar(TeachingSynapse teacher, NeuralNet nnet, String archivo) {
        
        /* Creates the error output file */
        FileOutputSynapse resultado = new FileOutputSynapse();
        resultado.setFileName(archivo);
        //error.setBuffered(false);
        teacher.addResultSynapse(resultado);
    }
 
    //Metodo para recuperar lo qe la red ha aprendido
//    public NeuralNet recuperar(String archivo) {
//        try {
//            FileInputStream stream = new FileInputStream(archivo);
//            ObjectInputStream entrada = new ObjectInputStream(stream);
// 
//            return (NeuralNet) entrada.readObject();
//        } catch (Exception excp) {
//            excp.printStackTrace();
// 
//            return null;
//        }
//    }
    
    public boolean recuperar2(String archivo) throws FileNotFoundException, IOException
    {
        String linea;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        boolean bandera= false;
                
        if((linea = b.readLine())==null)
        {
            bandera=true;
        }
        
        return bandera;
    }
    
    
    public void netStopped(NeuralNetEvent e) {
        System.out.println("Training finished");
    }
    
    public void cicleTerminated(NeuralNetEvent e) {
    }
    
    public void netStarted(NeuralNetEvent e) {
        System.out.println("Training...");
        
//        this.recuperar("RNA-Se_Entreno");
    }
    
    public void errorChanged(NeuralNetEvent e) {
        Monitor mon = (Monitor)e.getSource();
        /* We want print the results every 200 cycles */
        if (mon.getCurrentCicle() % 200 == 0)
            System.out.println(mon.getCurrentCicle() + " epochs remaining - RMSE = " + mon.getGlobalError());
    }
    
    public void netStoppedError(NeuralNetEvent e,String error) {
    }
    
    
    
   
    
}
