package chargingScheduler;


import chargingScheduler.CarAgent;
import java.util.Comparator;

public class SortByStartTime implements Comparator<CarAgent> {

	@Override
	public int compare(CarAgent arg0, CarAgent arg1) {		
		if (arg0 == null) {
			return -1;
		}
		
		if (arg1 == null) {
			return 1;
		}
		
		return arg1.getStartTime().compareTo(arg0.getStartTime());
	}
}