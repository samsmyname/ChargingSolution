package chargingScheduler;


import java.util.ArrayList;
import java.util.List;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

/*
Functions:

Collects requirements and preferences from other agents (i.e. from car
agents)

Produces a coordinated schedule for all the cars (can use e.g. KBR, GA,
ACO or other intelligent search/optimisation/reasoning techniques from the
unit)

Sends the individual schedules to car agents 
*/


public class MasterScheduler extends Agent {

	int numberCars;	
	
	List<Car> cars = new ArrayList<Car>();
	
	protected void setup() 
    { 
        System.out.println("-------------------- Starting MasterSchesuler --------------------");
        System.out.println("My name is "+ getLocalName());

        
        addBehaviour(new TickerBehaviour(this, 1000){
        	protected void onTick(){

				ACLMessage msg = receive();
				while (msg != null)
				{
					Car c = null;
					if (cars != null && !cars.isEmpty()) {
						c = cars.get(cars.size()-1);
					}
					String msgString = msg.getContent();
					cars.add(new Car(msgString) );
					msg = receive();
					if (c != null) {
						if (c.AID == "") {
							System.out.println("No message");
						} else {
							System.out.println("Message is " + c.AID);
						}
					}
				System.out.println("Car ID sent: " + msgString);

				System.out.println("Cars registered: " + String.valueOf(cars.size()));
				//System.out.println("test: " + msg.getSender());
				msg = null;
        		} 

        	}
        });
    }
	
	public void autoSetup(){
		setup();
	}
	
	private void geneticAlgorithm()
	{
		
	}
	
	private void evolutionAlgorithm()
	{
		
	}
	
	private class Car{
		String AID;
		int Charge;
		int prefStartTime;
		int prefEndTime;
		
		private Car(String _AID)
		{
			AID = _AID;
			//Charge = _Charge;
		}
	}
	
}
