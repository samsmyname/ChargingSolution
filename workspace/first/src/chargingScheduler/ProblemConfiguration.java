package chargingScheduler;

import isula.aco.*;
import isula.aco.algorithms.*;
import isula.aco.algorithms.acs.*;
import isula.aco.algorithms.antsystem.*;
import isula.aco.algorithms.maxmin.*;
import isula.aco.exception.*;
import isula.image.*;
import isula.image.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parameter settings for the algorithm.
 *
 */
public class ProblemConfiguration implements MaxMinConfigurationProvider,
        AcsConfigurationProvider {

    //  private static final String FILE_NAME = "cars.data";
    private static final String FILE_NAME = "????.data";

    private static final String FILE_FOLDER =
            "C:/Users/????????????????????????????";

    public static final String FILE_DATASET = FILE_FOLDER + FILE_NAME;
    public static final int Q = 1;


    private static final int NUMBER_OF_ANTS = 1;
    private static final int VERY_IMPORTANT = 1;
    private static final int NOT_IMPORTANT = 0;

    private static final double EVAPORATION = 0.75;
    private static final int MAX_ITERATIONS = 20000;

    private Environment environment;
    private double initialPheromone = 1.0;

    public ProblemConfiguration(double[][] problemRepresentation) {
        List<Integer> randomSolution = new ArrayList<>();
        int numberOftimes = problemRepresentation.length;
        for (int timesIndex = 0; timesIndex < numberOftimes; timesIndex += 1) {
            randomSolution.add(timesIndex);
        }

        Collections.shuffle(randomSolution);
        double randomQuality = ACO.getScheduleMakespan(
                randomSolution.toArray(new Integer[randomSolution.size()]),
                problemRepresentation);

        
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public int getNumberOfAnts() {
        return NUMBER_OF_ANTS;
    }


    public double getEvaporationRatio() {
        return EVAPORATION;
    }

    public double getMaximumPheromoneValue() {
        throw new ConfigurationException(
                "We don't use this parameter in this version of the Algorithm");
    }

    public double getMinimumPheromoneValue() {
        throw new ConfigurationException(
                "We don't use this parameter in this version of the Algorithm");
    }

    public double getQValue() {
        return Q;
    }

    public int getBestChoiceConstant() {
        return 4;
    }

    public int getNumberOfIterations() {
        return MAX_ITERATIONS;
    }

    public double getInitialPheromoneValue() {
        return this.initialPheromone;
    }

    /**
     * This method of calculation is included in the paper.
     */
    public double getBestChoiceProbability() {
        double[][] problemGraph = this.environment.getProblemGraph();
        double bestChoiceProbability = (problemGraph.length - this
                .getBestChoiceConstant()) / problemGraph.length;
        return bestChoiceProbability;
    }

    public double getHeuristicImportance() {
        return NOT_IMPORTANT;
    }

    public double getPheromoneImportance() {
        return VERY_IMPORTANT;
    }
}
