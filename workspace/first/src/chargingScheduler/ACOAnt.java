package chargingScheduler;

import java.util.List;
import java.util.Random;

import chargingScheduler.Car;

public class ACOAnt {
	Pheremones antPheremones;
	int[][] myPath;
	List<Car> cars;
	
	public ACOAnt(List<Car> cars, Pheremones oldPath)
	{
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
			System.out.println("time: " + t + "car: " + carSelected);
			myPath[t][carSelected] = 1;	//Set the path of this ant
		}
		
		for (int t = 0; t<24; t++)
		{
			for (int c = 0; c<cars.size() ; c++)
			{
				if (myPath[t][c] == 1)
					antPheremones.addPheremone(t, c, pathCost());	//Add pheremone to this path
			}
			
			
		}
		
	}
	
	public int pathCost()
	{
			// For best possible schedule, fitness should be 100 with current weightings and settings.
			
			int maxFitness = 100;
			int fitness = maxFitness; //Arbitrary starting value
			
			int i = 1; // The car value that appears in the gene.
			for (Car c : cars)
			{
				
				// Setup variables
				int hoursRequired = 0;
				int totalHours = 24;
				int hoursScheduledCount = numberOfHoursScheduled(i);
				int[] hoursGiven = new int[24];
				int hoursReqCount = 0;
				int hoursDist = 0;
				int carFitness = maxFitness;
				String test = "";
				String test2 = "";
				
				int startTime = c.getStartTime();
				int endTime = c.getEndTime();
				
				
				
				if (endTime < startTime)
				{
					hoursRequired = (24 - startTime) + endTime;
					for(int j = 0; j<24; j++)
					{
						if(j<=endTime || j>startTime)
						{
							hoursGiven[j] = i;
						}
					}
				} else
				{
					hoursRequired = endTime - startTime;
					for(int j = 0; j<24; j++)
					{
						if(j<=endTime && j>startTime)
						{
							hoursGiven[j] = i;
						}
					}
				}
				
				//TEMP: Assume car needs half as many hours to charge as hours given
				hoursRequired /= 2;
				
				// Compare number of hours to hours required
				int diff = Math.abs(hoursScheduledCount - hoursRequired);
				if (diff != 0)
				{
					double perc = (double)diff / (totalHours - hoursRequired) * 100;
					carFitness -= perc;
				}	
				
				// Compare hours to given times
				for (int j = 0; j < 24; j++)
				{	
					test += hoursGiven[j];
					test2 += (int)getCarAtTime(j);
					if ((int)getCarAtTime(j) == hoursGiven[j] )
					{
						
					}
					
						if ((int)getCarAtTime(j) != hoursGiven[j] )
						{
							if ((int)getCarAtTime(j) == i || hoursGiven[j] == i)
							{
								carFitness -= Math.abs((startTime-j)/3);
							}
						}				
				}
				
				// Subtract carfitness from totalfitness
				fitness -= (100-carFitness);
				
				// test output
				
				// Increment which car
				i++;
			}
			System.out.println("Fitness " + fitness);
			return fitness;

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
			totalWeight += antPheremones.getAmount(t,c) + 5;
		}
		// Now choose a random item
		int randomIndex = -1;
		double random = Math.random() * totalWeight;
		for (int i = 0; i < cars.size() + 1; ++i)
		{
		    random -= antPheremones.path[t][i] + 5;
		    if (random <= 0.0d)
		    {
		        randomIndex = i;
		        break;
		    }
		}
		return randomIndex;
	}
	
	public int[][] getPath()
	{
		return myPath;
	}
	
}
