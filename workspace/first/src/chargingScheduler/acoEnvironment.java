package chargingScheduler;

import java.util.logging.Logger;

import isula.aco.*;
import isula.aco.algorithms.*;
import isula.aco.algorithms.acs.*;
import isula.aco.algorithms.antsystem.*;
import isula.aco.algorithms.maxmin.*;
import isula.aco.exception.*;
import isula.image.*;
import isula.image.util.*;

public class acoEnvironment extends Environment {

    private static Logger logger = Logger.getLogger(acoEnvironment.class
            .getName());

    private int numberOftimes;

    /**
     * Environment for the Scheduling Problem.
     *
     */
    public acoEnvironment(double[][] problemGraph)
            throws InvalidInputException {
        super(problemGraph);
        this.numberOftimes = problemGraph.length;

        logger.info("Number of times: " + numberOftimes);
    }

    public int getNumberOftimes() {
        return getProblemGraph().length;
    }

    @Override
    protected boolean isProblemGraphValid() {
        int numberOfcars = getProblemGraph()[0].length;
        int times = getNumberOftimes();

        for (int i = 1; i < times; i++) {
            if (getProblemGraph()[i].length != numberOfcars) {
                return false;
            }
        }

        logger.info("Number of cars: " + numberOfcars);
        return true;
    }

	@Override
	protected double[][] createPheromoneMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

    
}
