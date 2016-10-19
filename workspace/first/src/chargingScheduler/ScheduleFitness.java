package chargingScheduler;

import java.util.List;


import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class ScheduleFitness extends FitnessFunction{
	
	private static List<Car> cars;
	
	public ScheduleFitness( int a_targetAmount, List<Car> cars )
    {
		this.cars = cars;
    }


	//PLEASE NOTE: I had no idea how to spell scheduled so there may be multiple spellings of the word in this class. Have fun!
	
	@Override
	protected double evaluate(IChromosome a_subject) {
		// For best possible schedule, fitness should be 100 with current weightings and settings.
		
		int maxFitness = 100;
		int fitness = maxFitness; //Arbitrary starting value
		
		int i = 1; // The car value that appears in the gene.
		for (Car c : cars)
		{
			
			// Setup variables
			int hoursRequired = 0;
			int totalHours = 24;
			int hoursScheduledCount = numberOfHoursScheduled(a_subject, i);
			int[] hoursGiven = new int[24];
			int hoursReqCount = 0;
			int hoursDist = 0;
			int carFitness = maxFitness;
			String hoursGivenString = "";
			String solutionString = "";
			
			if (c.prefEnd < c.prefStart)
			{
				hoursRequired = (24 - c.prefStart) + c.prefEnd;
				for(int j = 0; j<24; j++)
				{
					if(j<=c.prefEnd || j>c.prefStart)
					{
						hoursGiven[j] = i;
					}
				}
			} else
			{
				hoursRequired = c.prefEnd - c.prefStart;
				for(int j = 0; j<24; j++)
				{
					if(j<=c.prefEnd && j>c.prefStart)
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
				hoursGivenString += hoursGiven[j];
				solutionString += (int)getCarAtGene(a_subject,j);
				if ((int)getCarAtGene(a_subject,j) == hoursGiven[j] )
				{
					
				}
				
					if ((int)getCarAtGene(a_subject,j) != hoursGiven[j] )
					{
						if ((int)getCarAtGene(a_subject,j) == i || hoursGiven[j] == i)
						{
							carFitness -= Math.abs((c.prefStart-j)/3);
						}
					}				
			}
			
			// Subtract carfitness from totalfitness
			fitness -= (100-carFitness);
			
			// test output
			System.out.println(solutionString + " " + " " + carFitness);
			
			// Increment which car
			i++;
		}
		
		//Fitness value cannot go lower than 1 or an exception will be thrown.
		if (fitness<1)		
			fitness = 1;
		return fitness;
	}
	
	
	// Return hours scheduled
	private String hoursScheduled(IChromosome a_subject, int carID)
	{
		String hours = "";
		for(int i = 0; i < 24; i++)
		{
			hours += getCarAtGene(a_subject, i);
		}
		return hours;
	}
	
	//Returns the number of hours a car is scheduled
	private static int numberOfHoursScheduled(IChromosome a_subject, int carID)
	{
		int hours = 0;
		
		for (int i = 0; i<24; i++)
		{
			if (getCarAtGene(a_subject, i) == carID)
				hours++;
		}
		
		return hours;
	}
	
	//Returns the ID of the car scheduled at the time a_position
	public static int getCarAtGene (IChromosome a_potentialSolution, int a_position)
	{
		Integer carAtTime = (Integer)a_potentialSolution.getGene(a_position).getAllele();
		
		return carAtTime.intValue();
	}

}
