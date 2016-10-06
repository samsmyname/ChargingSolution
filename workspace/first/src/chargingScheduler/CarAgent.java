package chargingScheduler;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.concurrent.ThreadLocalRandom;

import week4.CounterManager2;

public class CarAgent extends Agent{
	// FIXME Is CarAgent gets the requirements from the user?
	private int carId = 0;
	private String carRegNum;
	private String startTime;
	private String endTime;
	private int carLoad = 20;
	private TickerBehaviour counter;
	
	public CarAgent() {
		System.out.println("Am i here? ---->  CarAgent()");
		System.out.println(getStartTime());
	}

	public CarAgent(String carRegNum, String startTime, String endTime) {
		System.out.println("Am i here?  ---->  CarAgent(String carRegNum, String startTime, String endTime)");
		this.carRegNum = carRegNum;
		this.setStartTime(startTime);
		this.endTime = endTime;
	}

	//FIXME ---Moved from SystemStart---
	public void StartCarAgent(String carReg, String startTime, String endTime) {
		AgentController agentCtrlc = null;
		try {
			// Create and start an agent of class CarAgent
			System.out.println(">>>>>>>>>>>>>>> Starting up a CarAgent...");
			agentCtrlc = SystemStart.mainCtrl.createNewAgent("CarAgent with registration " + carReg, CarAgent.class.getName(), new Object[0]);
			System.out.println("111");
			agentCtrlc.start();
		} catch (StaleProxyException e) {
			System.out.println("******* Error occured while starting up the agent ******* " + e);
		}
	}
	
	protected void setup() {
		//Send a msg to another agent
				addBehaviour(new CyclicBehaviour(){
					public void action(){
						ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
						msg.setContent("Send");
						msg.addReceiver(new AID("MasterScheduler",AID.ISLOCALNAME));
						send(msg);
					}
					
				});
		
		carId = 0123;

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID("MasterScheduler", AID.ISLOCALNAME));
		msg.setLanguage("ACL");
		msg.setContent(String.valueOf(carId));
		send(msg);
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public void deactivateCounter(){
		
	}
	
	public void activateCounter() {
		counter = new TickerBehaviour(this, 5000) {
			private static final long serialVersionUID = 1L;

			public void onStart() {
				super.onStart();
				System.out.println("Agent "+getLocalName()+" - Start counting");
			}
			
			protected void onTick() {
				System.out.println("Agent "+getLocalName()+" - Counter: " + getTickCount());
			}
			
			public int onEnd() {
				System.out.println("Agent "+getLocalName()+" - Stop counting");
				return super.onEnd();
			}
		};
		addBehaviour(counter);
	}
}