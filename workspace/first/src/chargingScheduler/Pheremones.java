package chargingScheduler;

public class Pheremones {
	int[][] path;
	int numberCars;
	int numberHours;
	
	public Pheremones(int numberHours, int numberCars)
	{
		this.numberCars = numberCars;
		this.numberHours = numberHours;
		
		path = getBlankSlate(5);
		
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
	
	public void evaperate()
	{
		for (int i = 0; i< numberHours; i++)
		{
			for (int j = 0; j < numberCars; j++)
			{
				if (path[i][j] > 5)
				{
					path[i][j] -= 1;
				}
			}
		}
	}
	
	public int getAmount(int time, int car)
	{
		return path[time][car];
	}
	
	public int[][] getBlankSlate(int p)
	{
		int[][] slate = new int[numberHours][numberCars + 1];
		
		for (int i = 0; i< numberHours; i++)
		{
			for (int j = 0; j < numberCars; j++)
			{
				slate[i][j] = p;
			}
		}
		System.out.println("Slate set");
		return slate;
	}
}
