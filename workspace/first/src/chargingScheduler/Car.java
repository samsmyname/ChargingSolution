package chargingScheduler;

public class Car {

	String AID;
	int chargeCurrent;
	int chargeMax;
	int prefStart;
	int prefEnd;

	//This constructor for testing only
	public Car(String _AID, int prefStart, int prefEnd) {
		AID = _AID;
		chargeCurrent = 40;
		chargeMax = 100;
		this.prefStart = prefStart;
		this.prefEnd = prefEnd;
		
	}
	
	public Car(String _AID, int chargeCurrent, int chargeMax, int freeStart, int freeEnd){
		
	}


	
	public int getStartTime() {
		return prefStart;
	}
	public int getEndTime() {
		return prefEnd;
	}

	
	public String getId() {
		return AID;
	}

	


	
}