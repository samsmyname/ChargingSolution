package chargingScheduler;

import java.util.List;
import java.util.Random;

import chargingScheduler.Car;

public class ACOAnt {
	Pheremones antPheremones;
	int[][] myPath;
	List<Car> cars;
	
	double chance;
	
	public ACOAnt(List<Car> cars, Pheremones oldPath, double chance)
	{
		this.chance = chance;
		
		this.cars = cars;
		antPheremones = oldPath;
		myPath = new int[24][cars.size()+1];
		
		for (int t = 0; t<24; t++)
		{			
			for (int c = 0; c<cars.size() + 1; c++)
			{
				myPath[t][c] = 0;
			}
		}
	}
	
	public void FindPath()
	{
		
		for (int t = 0; t<24; t++)
		{
			int carSelected = weightedCarChoice(t);
			myPath[t][carSelected] = 1;	//Set the path of this ant
		}
		
	}
	
	public int pathFitness()
	{
		int[] hoursGiven = new int[24];
		for (int j = 0; j < 24; j++)
			{	
				hoursGiven[j] = getCarAtTime(j);
			}
			
		return (int)FitnessACO.GetFitness(hoursGiven, cars);
	}
	
	//Returns the number of hours a car is scheduled
	private int numberOfHoursScheduled(int carID)
	{
		int hours = 0;
		for(int i = 0; i < 24; i++)
		{
			if (myPath[i][carID] == 1)
			hours += 1;
		}
		return hours;
		
	}
	
	//Returns the ID of the car scheduled at a time
	public int getCarAtTime (int time)
	{		
		for (int c = 0; c<cars.size(); c++)
		{
			if (myPath[time][c] == 1)
				return c;
		}
		
		return 100;
	}
	
	private int weightedCarChoice(int t)
	{

		// Compute the total weight of all items together
		double totalWeight = 0.0d;
		for (int c = 0; c<cars.size()+1; c++)
		{
			totalWeight += antPheremones.getAmount(t,c);
		}
		// Now choose a random item
		int randomIndex = -1;
		double random = Math.random() * totalWeight;
		for (int i = 0; i < cars.size() + 1; i++)
		{
		    random -= antPheremones.checkPheromoneLevel(t,i);
		    if (random <= 0.0d)
		    {
		        randomIndex = i;
		        break;
		    }
		}
		
		if (Math.random()>= chance)
		{
			int r = (int)(Math.random()*(cars.size()+1));
			return r;
			
		}
		
		return randomIndex;
	}
	
	public int[][] getPath()
	{
		return myPath;
	}
	
}
