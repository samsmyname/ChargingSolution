package chargingScheduler;

import java.util.ArrayList;
import java.util.List;

import org.jgap.InvalidConfigurationException;
import chargingScheduler.ACO;
import isula.aco.exception.ConfigurationException;
import isula.aco.exception.InvalidInputException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

/**
 * @MasterScheduler - Collects requirements and preferences from other agents
 *                  (i.e. from car agents) - Produces a coordinated schedule for
 *                  all the cars (can use e.g. KBR, GA, ACO or other intelligent
 *                  search/optimisation/reasoning techniques from the unit) -
 *                  Sends the individual schedules to car agents
 */

public class MasterScheduler extends Agent {
	
	

	int numberCars;
	List<Car> cars = new ArrayList<Car>();
	List<AID> AIDList = new ArrayList<AID>();

	/*
	 * @see jade.core.Agent#setup()
	 * 
	 * @setup - Recieve messages from CarAgent
	 */
	protected void setup() {
		SystemStart.MS = this;
		
		System.out
				.println("-------------------- Starting MasterSchesuler --------------------");

		/**
		 * @addBehaviour - CyclicBehaviour receives x message from car agent -
		 *               Sends reply to x message to Car Agent
		 */
		// Recieve from Car Agent
		addBehaviour(new CyclicBehaviour() {
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					AIDList.add(msg.getSender());
					System.out
							.println("Received msg from MasterSchedularAgent: "
									+ msg.getContent());
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent(" Reply from MSA");
					send(reply);

					String[] split = msg.getContent().split(",");
					String[] prefStart = split[1].split(":");
					String[] prefEnd = split[2].split(":");
					String curr = split[3];
					String max = split[4];

					Car newCar = new Car(split[0],
							Integer.parseInt(prefStart[0]),
							Integer.parseInt(prefEnd[0]),
							Integer.parseInt(curr), Integer.parseInt(max));
					cars.add(newCar);

				} else {
					block();
				}
			}
		});
	}

	public void autoSetup() {
		setup();
	}

	public void geneticAlgorithm() {
		try {
			JGAP GA =  new JGAP(cars);
			sendSchedules(GA.finalSolution);
		} catch (InvalidConfigurationException e) {
			System.out
					.println("***** Exception occured on geneticAlgorithm() *****");
			e.printStackTrace();
		}
	}

	public void antColonyAlgorithm(String ph, String pr, String ev) {
		try {
			ACO ACO = new ACO(cars);
			ACO.adjustConstants(Integer.valueOf(ph), Double.valueOf(pr), Double.valueOf(ev));
			ACO.ACOLoop();
			sendSchedules(ACO.bestSolution());
		} catch (ConfigurationException e) {
			System.out
					.println("***** Exception occured on ACOAlgorithm() *****");
			e.printStackTrace();
		}
	}
	
	public void sendSchedules(String schedule)
	{
		for (AID a : AIDList)
		{
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			message.addReceiver(a);
			message.setContent("schedule " + schedule);
			send(message);
			
		}
	}
}
