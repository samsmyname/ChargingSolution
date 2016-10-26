package chargingScheduler;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import isula.aco.AcoProblemSolver;
import isula.aco.Ant;
import isula.aco.AntColony;
import isula.aco.ConfigurationProvider;
import isula.aco.DaemonAction;
import isula.aco.algorithms.antsystem.OfflinePheromoneUpdate;
import isula.aco.algorithms.antsystem.PerformEvaporation;
import isula.aco.algorithms.antsystem.StartPheromoneMatrix;
import isula.aco.exception.ConfigurationException;
import isula.aco.exception.InvalidInputException;

public class ACOProblem {
	
	double[][] problemRepresentation; 

	public ACOProblem(List<Car> cars) throws IOException, InvalidInputException, ConfigurationException, javax.naming.ConfigurationException 
	{
		problemRepresentation = new double[10][2];
		
		for (int i = 0; i<10; i++)
		{
			for (int j = 0; j<2; j++)
			{
				problemRepresentation[i][j] = 10;
			}
		}
		
		ACOConfig configurationProvider = new ACOConfig(problemRepresentation);
		AntColony<Integer, ACOEnvironment> colony = getAntColony(configurationProvider);
		ACOEnvironment environment = new ACOEnvironment(problemRepresentation);
		
		AcoProblemSolver<Integer, ACOEnvironment> solver = new AcoProblemSolver<>();
		solver.initialize(environment, colony, configurationProvider);
		solver.addDaemonActions(new StartPheromoneMatrix<Integer, ACOEnvironment>(), new PerformEvaporation<Integer, ACOEnvironment>());
		
		solver.addDaemonActions(getPheromoneUpdatePolicy());
		solver.solveProblem();
		
		System.out.println( solver.getBestSolutionAsString());
		
	}
	
	public static AntColony<Integer, ACOEnvironment> getAntColony(final ConfigurationProvider configurationProvider) {
		return new AntColony<Integer, ACOEnvironment>(configurationProvider.getNumberOfAnts()) {
			@Override
			protected Ant<Integer, ACOEnvironment> createAnt(ACOEnvironment environment) {
				int initialReference = new Random().nextInt(environment.getNumberOfCities());
				return new ACOAnt(environment.getNumberOfCities());
			}
		};
	}
	
	private static DaemonAction<Integer, ACOEnvironment> getPheromoneUpdatePolicy() {
		return new OfflinePheromoneUpdate<Integer, ACOEnvironment>() {
			@Override
			protected double getNewPheromoneValue(Ant<Integer, ACOEnvironment> ant, Integer positionInSolution, Integer solutionComponent,
					ACOEnvironment environment, ConfigurationProvider configurationProvider) 
			{
				Double contribution = 1 / ant.getSolutionCost(environment);
				return ant.getPheromoneTrailValue(solutionComponent, positionInSolution, environment) + contribution;
			}
		};
	}
}
