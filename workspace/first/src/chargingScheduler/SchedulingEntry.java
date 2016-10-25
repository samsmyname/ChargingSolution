package chargingScheduler;

public class SchedulingEntry {

	private int _carId;
	private boolean _isCharging;
	
	
	public SchedulingEntry(){}
	
	public SchedulingEntry(int id, boolean isCharging) {
		this.setCarId(id);
		this.setIsCharging(isCharging);
	}
	
	public int getCarId() {
		return _carId;
	}
	public void setCarId(int _carId) {
		this._carId = _carId;
	}
	public boolean isCharging() {
		return _isCharging;
	}
	public void setIsCharging(boolean _isCharging) {
		this._isCharging = _isCharging;
	}
}