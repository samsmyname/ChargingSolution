package chargingScheduler;

import chargingScheduler.CarAgent;
import java.util.ArrayList;
import chargingScheduler.SchedulingEntry;

public interface Algorithm {

	void execute();
	void addCar(Car car);
	Car getNextCar();
	int getPositionInQueue(String name);
}