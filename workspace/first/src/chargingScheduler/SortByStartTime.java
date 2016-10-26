package chargingScheduler;


import chargingScheduler.CarAgent;
import java.util.Comparator;

public class SortByStartTime implements Comparator<Car> {

	@Override
	public int compare(Car arg0, Car arg1) {		
		if (arg0 == null) {
			return -1;
		}
		
		if (arg1 == null) {
			return 1;
		}
		
	//	return arg1.getStartTime().compareTo(arg0.getStartTime());
		return Integer.compare(arg1.getStartTime(), arg0.getStartTime());
	}
}