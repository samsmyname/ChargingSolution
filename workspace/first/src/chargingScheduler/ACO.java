package chargingScheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;

import chargingScheduler.Car;
import chargingScheduler.Algorithm;
import chargingScheduler.CarAgent;
import chargingScheduler.SortByStartTime;
import chargingScheduler.SortById;
import chargingScheduler.SchedulingEntry;


public class ACO implements Algorithm
{	
	private Queue<Car> _scheduledCars;
	private ArrayList<Car> _source;
	
	public ACO() {
		this._source = new ArrayList<Car>();
	}
	
	public ACO(ArrayList<Car> cars) {
		this._source = cars;
	}	
	
	@Override
	public void execute() {
		this._scheduledCars = new LinkedList<Car>();
		this._source.sort(new SortByStartTime());
		
		for (Car car : this._source) {
			this._scheduledCars.add(car);	
		}
	}
	
	public Car getNextCar() 
	{	
		return this._scheduledCars.poll();	
	}
	
	public void addCar(Car car)
	{
		this._source.add(car);
		this.execute();
	}

	@Override
	public int getPositionInQueue(String name) {		
	 ArrayList<Car> tmp = new ArrayList<Car>(this._scheduledCars);
	int position = 	tmp.size();
	 
		for (int i = 0; i < tmp.size(); i++) {
			position -= 1;
			if (tmp.get(i).getId() == name) {
				return position;
			}	
		}
		
		return -1;
	}	
}