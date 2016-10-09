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
		// For best possible schedule, fitness should be 150 with current weightings and settings.
		
		int fitness = 50; //Arbitrary starting value
		
		int i = 1;	// The car value that appears in the gene.
		for (Car c : cars)
		{
			//Assume car needs 8 hours to charge - will be changed when code is written to get data from the car agents.
			int hoursRequired = 8;		//TODO
			int hoursSchedueled = numberOfHoursScheduled(a_subject, i);
			
			if (hoursSchedueled == hoursRequired)	// If we have scheduled the right number of hours.
			{
				int idealBlocks = 1;	//Assume we want to charge the car in one continuous period
				int scheduledBlocks = howManySeperateBlocks(a_subject, i);
				
				if (idealBlocks != scheduledBlocks)		//If our schedule has split up charging periods
				{
					fitness -= (Math.pow((idealBlocks-scheduledBlocks), 2))*2;
					
					
					if (scheduledBlocks == 2)
					{
						int distance = seperateBlockDistance(a_subject, i);
						fitness -= Math.pow(distance, 2);
					}
					
				}
				else
				{
					fitness+=(100/cars.size());	//Massive boost if the solution meets all goals, weighted so that it is the same boost no matter how many cars.

				}
				
			}
			else if (hoursSchedueled < hoursRequired | hoursSchedueled > hoursRequired)
			{
				fitness -= (int) Math.abs( Math.pow((hoursRequired-hoursSchedueled), 2))*3;
			}
				
			i++;
		}

		if (fitness<1)		//Fitness value cannot go lower than 1 or an exception will be thrown.
			fitness = 1;
		
		return fitness;
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
	
	//Works out how many non continuous periods of time a car is scheduled to charge for.
	private static int howManySeperateBlocks (IChromosome a_subject, int carID)
	{
		int blocks = 0;
		boolean sameBlock = false;
		
		for (int i = 0; i<24; i++)
		{
			if (getCarAtGene(a_subject, i) == carID)
			{
				if (sameBlock == false)
				{
					blocks++;
					sameBlock = true;
				}
			}
			else
			{
				sameBlock = false;
			}

		}
		
		return blocks;
	}
	
	//Works out how close two separate car charging blocks are too each other 
	//FIXME This function does not work as intended.
		private static int seperateBlockDistance (IChromosome a_subject, int carID)
		{
			boolean sameBlock = false;
			int distance = 0;
			
			for (int i = 0; i<24; i++)
			{
				if (getCarAtGene(a_subject, i) == carID)
				{
					if (sameBlock == false)
					{
						sameBlock = true;
					}
				}
				else
				{
					if (sameBlock == false)
						distance+=1;
					
					sameBlock = false;
				}

			}
			
			return distance;
		}
	
	//Returns the ID of the car scheduled at the time a_position
	public static int getCarAtGene (IChromosome a_potentialSolution, int a_position)
	{
		Integer carAtTime = (Integer)a_potentialSolution.getGene(a_position).getAllele();
		
		return carAtTime.intValue();
	}

}
