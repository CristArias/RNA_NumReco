/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import org.joone.engine.FullSynapse;
import org.joone.engine.LinearLayer;
import org.joone.engine.SigmoidLayer;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.net.NeuralNet;

/**
 *
 * @author Fainy
 */
public class Red {
    
    public NeuralNet creacionRed() {
        /*
         * Firts, creates the three Layers
         */
        LinearLayer entrada = new LinearLayer();
        SigmoidLayer oculta = new SigmoidLayer();
        SigmoidLayer salida = new SigmoidLayer();
        entrada.setLayerName("entrada");
        oculta.setLayerName("oculta");
        salida.setLayerName("salida");
        /* sets their dimensions */
        entrada.setRows(2);
        oculta.setRows(3);
        salida.setRows(1);
        
        /*
         * Now create the two Synapses
         */
        FullSynapse synapse_EO = new FullSynapse(); /* input -> hidden conn. */
        FullSynapse synapse_OS = new FullSynapse(); /* hidden -> output conn. */
        
        synapse_EO.setName("EO");
        synapse_OS.setName("OS");
        /*
         * Connect the input layer whit the hidden layer
         */
        entrada.addOutputSynapse(synapse_EO);
        oculta.addInputSynapse(synapse_EO);
        /*
         * Connect the hidden layer whit the output layer
         */
        oculta.addOutputSynapse(synapse_OS);
        salida.addInputSynapse(synapse_OS);
        
        NeuralNet nnet = new NeuralNet();
        nnet.addLayer(entrada, NeuralNet.INPUT_LAYER);
        nnet.addLayer(oculta, NeuralNet.HIDDEN_LAYER);
        nnet.addLayer(salida, NeuralNet.OUTPUT_LAYER);
        
        return nnet;
    }
        
}

