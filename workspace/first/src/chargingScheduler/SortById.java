package chargingScheduler;

import java.util.Comparator;

import chargingScheduler.CarAgent;

public class SortById implements Comparator<CarAgent> {

	@Override
	public int compare(CarAgent arg0, CarAgent arg1) {		
		if (arg0 == null) {
			return -1;
		}
		
		if (arg1 == null) {
			return 1;
		}
				
		return arg1.getId() - arg0.getId();
	}
}