package chargingScheduler;

import isula.aco.*;
import isula.aco.algorithms.*;
import isula.aco.algorithms.acs.*;
import isula.aco.algorithms.antsystem.*;
import isula.aco.algorithms.maxmin.*;
import isula.aco.exception.*;
import isula.aco.test.*;


public class ACOEnvironment extends Environment {
	
	public ACOEnvironment(double[][] problemGraph) throws InvalidInputException
	{
		super(problemGraph);
	}
	
	public int getNumberOfCities()
	{
		return getProblemGraph().length;
	}

	@Override
	protected double[][] createPheromoneMatrix() {
		
		int numberOfCities = getNumberOfCities();
		
		
		return new double[numberOfCities][numberOfCities];
	}

}
