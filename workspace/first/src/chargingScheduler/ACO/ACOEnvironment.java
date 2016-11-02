package chargingScheduler.ACO;

import java.util.logging.Logger;

import isula.aco.*;
import isula.aco.algorithms.*;
import isula.aco.algorithms.acs.*;
import isula.aco.algorithms.antsystem.*;
import isula.aco.algorithms.maxmin.*;
import isula.aco.exception.*;
import isula.aco.test.*;


public class ACOEnvironment extends Environment {
	
	private static Logger logger = Logger.getLogger(ACOEnvironment.class
            .getName());

    private int numberOfJobs;

    /**
     * Environment for the Flow Shop Scheduling Problem.
     *
     * @param problemGraph Graph representation of the problem.
     * @throws InvalidInputException When the graph is incorrectly formed.
     */
    public ACOEnvironment(double[][] problemGraph)
            throws InvalidInputException {
        super(problemGraph);
        this.numberOfJobs = problemGraph.length;

        logger.info("Number of Jobs: " + numberOfJobs);
    }

    public int getNumberOfJobs() {
        return getProblemGraph().length;
    }

    @Override
    protected boolean isProblemGraphValid() {
        int numberOfMachines = getProblemGraph()[0].length;
        int jobs = getNumberOfJobs();

        for (int i = 1; i < jobs; i++) {
            if (getProblemGraph()[i].length != numberOfMachines) {
                return false;
            }
        }

        logger.info("Number of Machines: " + numberOfMachines);
        return true;
    }

    @Override
    protected double[][] createPheromoneMatrix()
            throws MethodNotImplementedException {
        int jobs = getNumberOfJobs();

        return new double[jobs][jobs];
    }

}
