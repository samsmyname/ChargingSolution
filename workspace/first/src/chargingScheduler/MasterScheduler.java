package chargingScheduler;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

/**
 * @MasterScheduler
 *  - Collects requirements and preferences from other agents (i.e. from car agents)
 *  - Produces a coordinated schedule for all the cars (can use e.g. KBR, GA, ACO or other intelligent search/optimisation/reasoning techniques from the unit)
 *  - Sends the individual schedules to car agents 
 */

public class MasterScheduler extends Agent {

	int numberCars;
	List<Car> cars = new ArrayList<Car>();

	/* 
	 * @see jade.core.Agent#setup()
	 * @setup
	 *  	- Recieve messages from CarAgent
	 */
	protected void setup() {
		System.out.println("-------------------- Starting MasterSchesuler --------------------");
	
		/**
		 * @addBehaviour
		 *  - CyclicBehaviour receives x message from car agent
		 *  - Sends reply to x message to Car Agent
		 */
		// Recieve from Car Agent
		addBehaviour(new CyclicBehaviour() {
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					 System.out.println("Received msg from MasterSchedularAgent: " + msg.getContent());
					 ACLMessage reply = msg.createReply();
					 reply.setPerformative( ACLMessage.INFORM );
					 reply.setContent(" Reply from MSA" ); 
					 send(reply);
					 
					 geneticAlgorithm();
				} else {
					block();
				}
			}
		});
	}

	public void autoSetup() {
		setup();
	}

	private void geneticAlgorithm() {

	}

	private void evolutionAlgorithm() {

	}

}