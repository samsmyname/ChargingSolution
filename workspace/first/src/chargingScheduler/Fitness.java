package chargingScheduler;

import java.util.ArrayList;
import java.util.List;


import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class Fitness{
	
	public static double GetFitness(int[] solution, List<Car> cars) {
		// For best possible schedule, fitness should be 300 with current weightings and settings.
		
		int maxFitness = 300;
		int fitness = maxFitness; //Arbitrary starting value
		
		int i = 1; // The car value that appears in the gene.
		for (Car c : cars)
		{
			
			// Setup variables
			int chargeSpeed = 100; //Amount of charge per hour
			int hoursRequired = 0;
			int totalHours = 24;
			int hoursScheduledCount = numberOfHoursScheduled(solution, i);
			int hoursReqCount = 0;
			int[] hoursGiven = new int[24];
			int hoursDist = 0;
			int carFitness = maxFitness;
			String hoursGivenString = "";
			String solutionString = "";
			
			if (c.prefEnd < c.prefStart)
			{
				for(int j = 0; j<24; j++)
				{
					if(j<=c.prefEnd || j>c.prefStart)
					{
						hoursGiven[j] = i;
					}
				}
			} else
			{
				for(int j = 0; j<24; j++)
				{
					if(j<=c.prefEnd && j>c.prefStart)
					{
						hoursGiven[j] = i;
					}
				}
			}

            double hoursNeeded =( c.chargeMax - c.chargeCurrent );
            hoursNeeded = Math.ceil(hoursNeeded / chargeSpeed);
            hoursRequired = (int)hoursNeeded;
			
			// Compare number of hours to hours required
			int diff = Math.abs(hoursScheduledCount - hoursRequired);
			if (diff != 0)
			{
				double perc = (double)diff / (totalHours - hoursRequired) * maxFitness;
				carFitness -= perc*5;
			}	
			
			// Compare hours to given times
			for (int j = 0; j < 24; j++)
			{	
				hoursGivenString += hoursGiven[j];
				solutionString += solution[j];
				solutionString += " ";
				
				if(solution[j] == i)
				{
					if (hoursGiven[j] == i)
					{
						carFitness -= Math.abs((c.prefStart-j));
					}
					else
					{
						carFitness -= Math.abs((c.prefStart-j))*24;
					}
				}
			}
			
			// Subtract carfitness from totalfitness
			fitness -= (100-carFitness);
			
			// test output
			//System.out.print("Test Output: " + hoursScheduledCount + " - " + solutionString + " - " + " - " + carFitness + " - ");
			
			// Increment which car
			i++;
		}
		
		//Fitness value cannot go lower than 1 or an exception will be thrown.
		if (fitness<1)		
			fitness = 1;
		return fitness;
	}
	
	
	// Return hours scheduled
	private String hoursScheduled(int[] solution, int carID)
	{
		String hours = "";
		for(int i = 0; i < 24; i++)
		{
			hours += solution[i];
		}
		return hours;
	}
	
	//Returns the number of hours a car is scheduled
	private static int numberOfHoursScheduled(int[] solution, int carID)
	{
		int hours = 0;
		
		for (int i = 0; i<24; i++)
		{
			if (solution[i] == carID)
				hours++;
		}
		
		return hours;
	}


	public static void test() {
		
		List<Car> testCars = new ArrayList<Car>();
		testCars.add( new Car("testCar", 5, 10, 0, 500));
		
		int[] testSolution = new int[]{0,0,0,0,0,1,1,1,1,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		
		double score = GetFitness(testSolution, testCars);
		
		System.out.println("Test Score: " + score);
		
	}
}
