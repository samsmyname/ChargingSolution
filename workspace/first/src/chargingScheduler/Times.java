package chargingScheduler;

public class Times {
		
		Boolean[] freeTimes = new Boolean[24];
		
	

		public Times(int...arguments)
		{
			for (int a : arguments)
			{
				freeTimes[a] = true;
			}
		}
		
		public Boolean IsFree(int t)
		{
			if (freeTimes[t])
			{
				return true;
			}
			
			return false;
		}
}

