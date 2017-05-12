package net.minthe.ac.wm;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.DoubleArrayCrossover;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.Stagnation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael Kelley on 5/10/2017.
 */
public class ACWM {
    public static void main(String[] args) {
        CandidateFactory<double[]> factory = new RandomDoubleArrayFactory(840);
        List<EvolutionaryOperator<double[]>> operators = new LinkedList<>();
        operators.add(new DoubleArrayCrossover());
//        operators.add(new DoubleArrayMutation(-10, 10, new Probability(.1)));
        EvolutionaryOperator<double[]> pipeline = new EvolutionPipeline<>(operators);
        DoubleArrayEvaluator evaluator = new DoubleArrayEvaluator();
        SelectionStrategy<Object> selector = new RouletteWheelSelection();
        Random rng = new MersenneTwisterRNG();

        EvolutionEngine<double[]> engine =
                new GenerationalEvolutionEngine<>(factory,
                        pipeline,
                        evaluator,
                        selector,
                        rng);

        engine.addEvolutionObserver(data -> System.out.println(data.getBestCandidateFitness()));
        engine.evolve(100, 1, new Stagnation(10, true));
    }
}
