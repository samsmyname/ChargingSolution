package chargingScheduler;

public class Car {

	String AID;
	int chargeCurrent;
	int chargeMax;
	int prefStart;
	int prefEnd;

	//This constructor for testing only
	public Car(String _AID, int prefStart, int prefEnd, int chargeCur, int chargeM) {
		AID = _AID;
		chargeCurrent = chargeCur;
		chargeMax = chargeM;
		this.prefStart = prefStart;
		this.prefEnd = prefEnd;
		
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