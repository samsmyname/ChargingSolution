package chargingScheduler;

import java.util.List;
import isula.aco.exception.ConfigurationException;
import isula.aco.exception.InvalidInputException;
import chargingScheduler.Car;

public class ACO {
	
	int[][] matrix;
	List<Car> cars;
	int[][] bestPath;
	int bestPathFitness;
	
	Pheremones pheremonePath;
	
	public ACO(List<Car> cars)
	{
		this.cars = cars;
		matrix = new int[24][cars.size() + 1];
		bestPath = new int[24][cars.size() + 1];
		bestPathFitness = 0;
		
		pheremonePath = new Pheremones(24, cars.size());
		
		ACOLoop();
	}
	
	private void ACOLoop()
	{
		int numAnts = 30;
		
		for (int i = 0; i<5; i++)
		{
			for (int a = 0; a<numAnts; a++)
			{
				System.out.println("Iteration " + i + "Ant: " + a);
				ACOAnt ant = new ACOAnt(cars, pheremonePath);
				ant.FindPath();
				if (ant.pathCost() > bestPathFitness)
				{
					bestPath = ant.getPath();
					bestPathFitness = ant.pathCost();
				}
				
				//pheremonePath.mergePaths(ant.antPheremones);
			}
			
			pheremonePath.evaperate();
		}
		
		System.out.println("Best Path Fitness: " + bestPathFitness);
		
		
		System.out.println("Best Solution: ");
		for (int i = 0; i<24; i++)
		{
			
			for (int path = 0; path<cars.size() + 1; path++)
			{
				
				if (bestPath[i][path] == 1)
					System.out.print(path + " ") ;
			}
			
			
			UI.displaySchedules(path);
		}
		

		System.out.println("");
		System.out.print("Pheremones:");
		for (int i = 0; i<24; i++)
		{
			for (int j = 0; j<cars.size() + 1; j++)
			{
					System.out.print(" " + pheremonePath.getAmount(i, j) + " ");
			}
			System.out.println("");
		}
	}

}
