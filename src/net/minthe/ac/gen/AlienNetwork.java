package net.minthe.ac;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Michael Kelley on 4/30/2017.
 */
public class AlienNetwork {
    //Random number generator seed, for reproducability
    public static final int seed = 13245;
    //Number of iterations per minibatch
    public static final int iterations = 1;
    //Number of epochs (full passes of the data)
    public static final int nEpochs = 2000;
    //How frequently should we plot the network output?
    public static final int plotFrequency = 500;
    //Number of data points
    public static final int nSamples = 1000;
    //Batch size: i.e., each epoch has nSamples/batchSize parameter updates
    public static final int batchSize = 100;
    //Network learning rate
    public static final double learningRate = 0.01;
    public static final int numInputs = 75;
    public static final int numOutputs = 1;

    public static void main(String[] args) throws IOException, InterruptedException {
        RecordReader reader = new CSVRecordReader(0, ",");
//        reader.initialize(new FileSplit(new ClassPathResource("data.csv").getFile()));
        reader.initialize(new FileSplit(new File("data.csv")));
        DataSetIterator iterator = new RecordReaderDataSetIterator(reader, 100, 0, 0, true);
//        int numInputs = 75;
//        while (iterator.hasNext()) {
//            DataSet ds = iterator.next();
//            System.out.println(ds);
//        }
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .iterations(iterations)
                .learningRate(learningRate)
//                .rmsDecay(0.95)
                .seed(seed)
//                .regularization(true)
//                .l2(0.001)
                .weightInit(WeightInit.XAVIER)
                .updater(Updater.NESTEROVS).momentum(0.9)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(3).build())
                .layer(1, new DenseLayer.Builder().nIn(3).nOut(3).build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(3)
                        .nOut(1)
                        .build())
                .backprop(true)
                .pretrain(false)
                .build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        net.setListeners(new ScoreIterationListener(1));

        INDArray[] networkPredictions = new INDArray[nEpochs / plotFrequency];
        for (int i = 0; i < nEpochs; i++) {
            iterator.reset();
            net.fit(iterator);
            if ((i + 1) % plotFrequency == 0) {
                iterator.reset();
                networkPredictions[i / plotFrequency] = net.output(iterator, false);
                System.out.println(networkPredictions[i/plotFrequency]);
            }
        }

    }
}
