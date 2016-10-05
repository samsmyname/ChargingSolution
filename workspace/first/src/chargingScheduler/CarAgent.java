package chargingScheduler;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.concurrent.ThreadLocalRandom;

  public class CarAgent extends Agent{ 
	  //FIXME Is CarAgent gets the requirements from the user?
	  private int carId = 0;
	  private String carRegNum;
	  private String startTime;
	  private String endTime;
	  private int carLoad = 20;
	  private TickerBehaviour counter;
	  
	  public CarAgent(){
		  System.out.println(getStartTime());
	  }
	  
	  public CarAgent(String carRegNum, String startTime, String endTime){
		  this.carId = carId;
		  this.setStartTime(startTime);
		  this.endTime = endTime;
	  }
	  
	 
      protected void setup(){ 
    	  carId = ThreadLocalRandom.current().nextInt(100, 999 + 1);
    	  
    	  ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
    	  msg.addReceiver(new AID("MasterScheduler", AID.ISLOCALNAME));
    	  msg.setLanguage("English");
    	  msg.setContent(String.valueOf(carId) );
    	  send(msg);
      }

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
  }