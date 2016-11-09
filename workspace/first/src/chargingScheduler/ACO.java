package chargingScheduler;

import java.util.List;
import isula.aco.exception.ConfigurationException;
import isula.aco.exception.InvalidInputException;
import chargingScheduler.Car;

public class ACO {
	
	private static final int Iterations = 2000;
	private static final int AntCount = 200;
	
	int[][] matrix;
	List<Car> cars;
	int[][] bestPath;
	int bestPathFitness;
	int timeSinceBetterPath = 0;
	
	double randomChance;
	
	Pheremones pheremonePath;
	
	public ACO(List<Car> cars)
	{
		this.cars = cars;
		matrix = new int[24][cars.size() + 1];
		bestPath = new int[24][cars.size() + 1];
		bestPathFitness = 0;
		
		pheremonePath = new Pheremones(24, cars.size());
		randomChance = 0.75;
		
	}
	
	public void adjustConstants(int ph, double pr, double ev)
	{
		pheremonePath.bonus = ph;
		randomChance = pr;
		pheremonePath.evap = ev;
		
	}
	
	public void ACOLoop()
	{
		System.out.println(" Pheremone Amounts: " + pheremonePath.pheromonesAsString());
		for (int i = 0; i<Iterations; i++)
		{
			ACOAnt[] ants = new ACOAnt[AntCount];
			
			for (int a = 0; a<AntCount; a++)
			{
				ants[a] = new ACOAnt(cars, pheremonePath, randomChance);
				ants[a].FindPath();
				if (ants[a].pathFitness() > bestPathFitness)
				{
					bestPath = ants[a].getPath();
					bestPathFitness = ants[a].pathFitness();
					System.out.print("Path cost: " + bestPathFitness);
					timeSinceBetterPath = 0;
				}
				else
				{
					timeSinceBetterPath++;			
					//if (timeSinceBetterPath > 10000)
						//i=Iterations;
				}
				
				
			}
			
			pheremonePath.updatePathPheromone(ants, bestPath);
			System.out.println(" Pheremone Amounts: " + pheremonePath.pheromonesAsString());
		}
		
		
		
		System.out.println("Best solution cost: " + bestPathFitness + " Solution: " + bestSolution());
		UI.displaySchedules(bestSolution());

	}
	
	String bestSolution()
	{
		return pathToString(bestPath);
	}
	
	String pathToString(int[][] convertPath)
	{
		String output ="";
		
		for (int i = 0; i<24; i++)
		{
			for (int path = 0; path<cars.size() + 1; path++)
			{
				
				if (convertPath[i][path] == 1)
				{
					output += path + " ";
				}
				
			}
		}
		
		return output;
	}

}
