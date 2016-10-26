package chargingScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import isula.aco.*;
import isula.aco.algorithms.*;
import isula.aco.algorithms.acs.*;
import isula.aco.algorithms.antsystem.*;
import isula.aco.algorithms.maxmin.*;
import isula.aco.exception.*;
import isula.aco.test.*;

public class ACOConfig implements ConfigurationProvider {
	
	private double initialPheromoneValue;
	
	public ACOConfig(double[][] problemRepresentation)
	{
		List<Integer> randomSolution = new ArrayList<>();
		
		int numberOfCities = problemRepresentation.length;
		for (int cityIndex = 0; cityIndex < numberOfCities; cityIndex += 1) 
		{
			randomSolution.add(cityIndex);
			
		}

		Collections.shuffle(randomSolution);
		
		double randomQuality = ACOAnt.getTotalDistance(randomSolution.toArray(new Integer[randomSolution.size()]), problemRepresentation);
		this.initialPheromoneValue = numberOfCities / randomQuality;
	
		
	}

	@Override
	public double getEvaporationRatio() {
		return 1 - 0.6;
	}

	@Override
	public double getHeuristicImportance() {
		return 2.5;
	}

	@Override
	public double getInitialPheromoneValue() {
		return this.initialPheromoneValue;
	}

	@Override
	public int getNumberOfAnts() {
		return 30;
	}

	@Override
	public int getNumberOfIterations() {
		return 50;
	}

	@Override
	public double getPheromoneImportance() {
		return 1.0;
	}

}
