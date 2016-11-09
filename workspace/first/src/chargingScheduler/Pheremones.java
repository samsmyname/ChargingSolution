package chargingScheduler;

public class Pheremones {
	private double[][] path;
	int numberCars;
	int numberHours;
	
	double evap;
	int bonus;
	
	public Pheremones(int numberHours, int numberCars)
	{
		this.numberCars = numberCars;
		this.numberHours = numberHours;
		
		evap = 0.75;
		
		path = getBlankSlate(1);
		
	}
	
	public void mergePaths(Pheremones p)
	{
		
		for (int i = 0; i< 24; i++)
		{
			for (int j = 0; j < numberCars + 1; j++)
			{
				path[i][j] = path[i][j] + p.path[i][j];
			}
		}

	}
	
	public void addPheremone(int t, int c, int amount)
	{
		path[t][c] += amount;
	}
	
	public void updatePathPheromone(ACOAnt[] ants, int[][] bestPath)
	{
		for (int i = 0; i< numberHours; i++)
		{
			for (int j = 0; j < numberCars + 1; j++)
			{
				path[i][j] *= evap;
			}
		}
		
		for (ACOAnt a : ants)
		{
			for (int i = 0; i< numberHours; i++)
			{
				for (int j = 0; j < numberCars + 1; j++)
				{
					if (a.myPath[i][j] == 1)
					{
						addPheremone(i, j, a.pathFitness());
						
						if (a.myPath[i][j] == bestPath[i][j])
						{
							addPheremone(i, j, bonus);
						}
					}
					
				}
			}
		}
	}
	
	public double getAmount(int time, int car)
	{
		return path[time][car];
	}
	
	public double[][] getBlankSlate(double p)
	{
		double[][] slate = new double[numberHours][numberCars + 1];
		
		for (int i = 0; i< numberHours; i++)
		{
			for (int j = 0; j < numberCars + 1; j++)
			{
				slate[i][j] = p;
			}
		}
		System.out.println("Slate set");
		return slate;
	}
	
	public String pheromonesAsString()
	{
		String newline = System.getProperty("line.separator");
		String output = newline;
		
		for (int i = 0; i<numberHours; i++)
		{
			output += " " + i + " ";
			for (int j = 0; j<path[i].length; j++)
			{
				 output += path[i][j] + " ";
			}
			output += newline;
		}
		
		return output;
	}
	
	public double checkPheromoneLevel(int i, int j)
	{
		return path[i][j];
	}
}
