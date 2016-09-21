package chargingScheduler;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.concurrent.ThreadLocalRandom;

  public class CarAgent extends Agent 
  { 
	  int carId;
	  int carLoad = 20;
	  
      protected void setup() 
      { 
    	  carId = ThreadLocalRandom.current().nextInt(100, 999 + 1);
    	  
    	  ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
    	  msg.addReceiver(new AID("MasterScheduler", AID.ISLOCALNAME));
    	  msg.setLanguage("English");
    	  msg.setContent(String.valueOf(carId) );
    	  send(msg);

      }
  }