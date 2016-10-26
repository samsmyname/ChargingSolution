package chargingScheduler;

import java.util.ArrayList;
import java.util.List;

import isula.aco.Ant;
import isula.aco.AntPolicy;
import isula.aco.AntPolicyType;
import isula.aco.algorithms.antsystem.*;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

public class ACOAnt extends Ant<Integer, ACOEnvironment>{

	
	private static final double DELTA = Float.MIN_VALUE;
	private final int numberOfCities;
	private int initialReference;
	
	public ACOAnt(int numberOfCities)
	{
		super();
		this.numberOfCities = numberOfCities;
		this.setSolution(new Integer[numberOfCities]);
		
		
		AntPolicy pol = new AntPolicy(AntPolicyType.NODE_SELECTION);
		this.addPolicy(pol);
	}
	
	
	@Override
	public Double getHeuristicValue(Integer solutionComponent, Integer positionInSolution, ACOEnvironment environment) {
		Integer lastComponent = this.initialReference;
		if (getCurrentIndex() > 0) {
			lastComponent = this.getSolution()[getCurrentIndex() - 1];
		}
		double distance = getDistance(lastComponent, solutionComponent, environment.getProblemGraph()) + DELTA;
		return 1 / distance;
	}

	@Override
	public List<Integer> getNeighbourhood(ACOEnvironment environment) {
		List<Integer> neighbourhood = new ArrayList<>();
		for (int cityIndex = 0; cityIndex < environment.getNumberOfCities(); cityIndex += 1) {
			neighbourhood.add(cityIndex);
		}
		
		return neighbourhood;
	}

	@Override
	public Double getPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution, ACOEnvironment environment) {
		Integer previousComponent = this.initialReference;
		if (positionInSolution > 0) {
			previousComponent = getSolution()[positionInSolution - 1];
		}
		double[][] pheromoneMatrix = environment.getPheromoneMatrix();
		return pheromoneMatrix[solutionComponent][previousComponent];
	}

	@Override
	public double getSolutionCost(ACOEnvironment environment) 
	{
		 return getTotalDistance(getSolution(), environment.getProblemGraph());
	}

	@Override
	public boolean isSolutionReady(ACOEnvironment environment) {
		return getCurrentIndex() == environment.getNumberOfCities();
	}

	@Override
	public void setPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution, ACOEnvironment environment, Double value) {
		Integer previousComponent = this.initialReference;
		if (positionInSolution > 0) {
			previousComponent = getSolution()[positionInSolution - 1];
		}
		
		double[][] pheromoneMatrix = environment.getPheromoneMatrix();
		pheromoneMatrix[solutionComponent][previousComponent] = value;
		pheromoneMatrix[previousComponent][solutionComponent] = value;
		
	}
	
	public static double getTotalDistance(Integer[] route, double[][] problemRepresentation) {
		double totalDistance = 0.0;
		for (int solutionIndex = 1; solutionIndex < route.length; solutionIndex += 1) {
			int previousSolutionIndex = solutionIndex - 1;
			totalDistance += getDistance(route[previousSolutionIndex], route[solutionIndex], problemRepresentation);
		}
		
		totalDistance += getDistance(route[route.length - 1], route[0], problemRepresentation);
		return totalDistance;
	}
	
	public static double getDistance(int anIndex, int anotherIndex, double[][] problemRepresentation) {
		double[] aCoordinate = getCityCoordinates(anIndex, problemRepresentation);
		double[] anotherCoordinate = getCityCoordinates(anotherIndex, problemRepresentation);
		
		return Math.round(new EuclideanDistance().compute(aCoordinate, anotherCoordinate));
	}
	
	private static double[] getCityCoordinates(int index, double[][] problemRepresentation) {
		return new double[]{problemRepresentation[index][0],problemRepresentation[index][1]};
	}

}
